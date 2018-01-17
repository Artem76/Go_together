package app.service.tasks;

import app.entity.*;
import app.entity.enums.VendorTypes;
import app.repository.VendorRatesCurrentRepository;
import app.service.logger.LoggerService;
import app.service.changesRegistery.ChangesRegisteryService;
import app.service.clientRatesCurrent.ClientRatesCurrentService;
import app.service.routingSmsRule.RoutingSmsRuleService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.smppVendorIps.SmppVendorIpsService;
import app.service.softswitch.SoftswitchService;
import app.service.softswitchTriggers.SoftswitchTriggersService;
import app.service.vendorDialpeer.VendorDialpeerService;
import app.service.vendorRatesCurrent.VendorRatesCurrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.telnet.TelnetClient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Олег on 31.07.2017.
 */

@Service
public class SoftswitchUpdateTask {

    @Autowired
    SmppVendorIpsService smppVendorIpsService;

    @Autowired
    SoftswitchService softswitchService;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Autowired
    SmppClientAccountService smppClientAccountService;

    @Autowired
    SmppVendorAccountService smppVendorAccountService;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    SoftswitchTriggersService softswitchTriggersService;

    @Autowired
    RoutingSmsRuleService routingSmsRuleService;

    @Autowired
    VendorDialpeerService vendorDialpeerService;

    @Autowired
    ClientRatesCurrentService clientRatesCurrentService;

    @Autowired
    VendorRatesCurrentService vendorRatesCurrentService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    VendorRatesCurrentRepository vendorRatesCurrentRepository;

    @Autowired
    LoggerService loggerService;

    @PersistenceContext
    private EntityManager em;

    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;

    public void runParallelStream(){

        loggerService.writeInfo("Softswitcch update task started");

        List<Softswitch> softswitches = softswitchService.getSoftswitchesAll();

        for (Softswitch currentSoftswitch : softswitches) {
            SoftswitchTriggers currentTriger = softswitchTriggersService.getTriggersBySoftswitchIdAndKey(currentSoftswitch.getId(), "api_exchange_enabled");
            if (!currentTriger.getValue()) {
                continue;
            }

            List<ChangesRegistery> changes = changesRegisteryService.getChangesBySoftswitch(currentSoftswitch.getId());
            if (changes.size() == 0) {
                continue;
            }

            try {
                telnet  = new TelnetClient();
                telnet.connect(currentSoftswitch.getHost(), currentSoftswitch.getApiPort());
                in = telnet.getInputStream();
                out = new PrintStream(telnet.getOutputStream());

                write(currentSoftswitch.getApi_username());
                write(currentSoftswitch.getApi_password());

                TimeUnit.SECONDS.sleep(1);

                readData();

                for (ChangesRegistery currentObject : changes) {

                    if (currentObject.getValues().contains("smppclientaccount_")) {
                        Long id = Long.parseLong(currentObject.getValues().replace("smppclientaccount_",""));
                        SmppClientAccount currentAccount = smppClientAccountService.getSmppClientAccountById(id);

                        if (currentAccount.getDontSync()) {
                            continue;
                        }

                        List<SmppClientSystemId> systemIdsList = smppClientSystemIdService.findSmppClientSystemIdsByAccount(currentAccount);
                        Boolean updateSuccess = true;
                        for (SmppClientSystemId currentSystemId : systemIdsList) {
                            Boolean result = UpdateUser(currentSystemId, currentSoftswitch);
                            if (!result) {
                                updateSuccess = false;
                            }
                        }

                        if (updateSuccess) {
                            changesRegisteryService.deleteChangesRegisteryById(currentObject.getId());
                        }

                    }

                    if (currentObject.getValues().contains("smppvendoraccount_")) {
                        Long id = Long.parseLong(currentObject.getValues().replace("smppvendoraccount_",""));
                        SmppVendorAccount currentAccount = smppVendorAccountService.getSmppVendorAccountById(id);

                         if (currentAccount.getDontSync()) {
                             continue;
                         }

                        Boolean updateSuccess = true;
                        if (currentAccount.getTag() == null) {
                            Integer newTag = smppVendorAccountService.getLastVendorTag();
                            newTag++;
                            smppVendorAccountService.UpdateTag(currentAccount, newTag);
                        }

                        updateSuccess = UpdateConnector(currentAccount, currentSoftswitch);

                        if (updateSuccess) {
                            changesRegisteryService.deleteChangesRegisteryById(currentObject.getId());
                        }

                    }

                    if (currentObject.getValues().contains("routingsmsrule_")) {
                        if (currentObject.getValues().contains("_DELETE")) {
                            String id = currentObject.getValues().replace("routingsmsrule_","");
                            id = id.replace("_DELETE","");
                            String[] parametersList = id.split("_");
                            deleteRoutingSmsRule(currentSoftswitch, parametersList[0], Long.parseLong(parametersList[1]), currentObject.getId());
                        } else {
                            String id = currentObject.getValues().replace("routingsmsrule_","");
                            updateRoutingSmsRule(currentSoftswitch, id, currentObject.getId());
                        }
                    }


                    if (currentObject.getValues().contains("dialpeer_")) {
                        Boolean updateSuccess = false;

                        String id = currentObject.getValues().replace("dialpeer_","");
                        VendorDialpeer dialpeer = vendorDialpeerService.getVendorDialpeerById(Long.parseLong(id));

                        if (dialpeer.getTag() == null) {
                            Integer newTag = smppVendorAccountService.getLastVendorTag();
                            newTag++;
                            vendorDialpeerService.UpdateTag(dialpeer, newTag);
                        }

                        updateSuccess = UpdateDialpeer(dialpeer, currentSoftswitch);

                        if (updateSuccess) {
                            changesRegisteryService.deleteChangesRegisteryById(currentObject.getId());
                        }
                    }

                    if (currentObject.getValues().contains("vendorratecurrent_")) {
                        Boolean updateSuccess = false;

                        String id = currentObject.getValues().replace("vendorratecurrent_","");
                        VendorRatesCurrent currentRate = vendorRatesCurrentService.getById(Long.valueOf(id));

                        if (currentRate != null) {
                            updateSuccess = updateVendorRate(currentRate, currentSoftswitch);
                        }

                        if (updateSuccess) {
                            changesRegisteryService.deleteChangesRegisteryById(currentObject.getId());
                        }

                    }

                    if (currentObject.getValues().contains("clientratecurrent_")) {
                        Boolean updateSuccess = false;

                        String id = currentObject.getValues().replace("clientratecurrent_","");
                        ClientRatesCurrent currentRate = clientRatesCurrentService.getById(Long.valueOf(id));

                        if (currentRate != null) {
                            updateSuccess = updateClientRate(currentRate, currentSoftswitch);
                        }

                        if (updateSuccess) {
                            changesRegisteryService.deleteChangesRegisteryById(currentObject.getId());
                        }
                    }

                }
                telnet.disconnect();
                in  = null;
                out = null;
                telnet = null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        loggerService.writeInfo("Softswitcch update task finished");

    }

    public String readData() {
        String returnString = "";
        try {
            char ch = (char) in.read();
            returnString = returnString + ch;
            while (in.available()!=0) {
                ch = (char) in.read();
                returnString = returnString + ch;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnString = returnString.replace("\n", "");
        returnString = returnString.replace("\r", "");
        return returnString;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean UpdateUser(SmppClientSystemId user, Softswitch softswitch) {
        if (user.getUid().equals("")) {
            smppClientSystemIdService.updateUid("n" + user.getId(), user);
        }

        Boolean returnValues = false;
        Boolean userCreated = false;
        try {
            write("user -s " + user.getUid());

            TimeUnit.SECONDS.sleep(1);

            String line = readData();

            if (line.contains("Unknown")) {
                write("user -a");
                write("uid " + user.getUid());
                write("username " + user.getSystemId());
            } else {
                write("user -u " + user.getUid());
            }
            write("password " + user.getPassword());
            write("gid group");
            write("ok");
            write("ko");

            TimeUnit.SECONDS.sleep(1);

            line = readData();

            if (line.contains("Successfully")) {
                userCreated = true;
                write("persist");

                TimeUnit.SECONDS.sleep(1);
            }

            if (userCreated) {
                Connection connection = null;
                ResultSet rs = null;
                Statement Statement = null;

                connection = DriverManager
                        .getConnection("jdbc:mysql://" + softswitch.getHost() + ":" + softswitch.getMysqlPort() + "/" + softswitch.getMysqlDatabase(), softswitch.getMysqlUsername(), softswitch.getMysqlPassword());
                Statement = connection.createStatement();
                rs = Statement.executeQuery("SELECT * \n" +
                        "FROM information_schema.tables\n" +
                        "WHERE table_schema = 'routing' \n" +
                        "    AND table_name = '" + user.getUid() +"'\n" +
                        "LIMIT 1;");
                if (!rs.next()) {
                    Statement.executeUpdate("CREATE TABLE " + user.getUid() + " LIKE n_num");
                }
                returnValues = true;
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValues;
    }

    private Boolean UpdateConnector(SmppVendorAccount connector, Softswitch softswitch) {
        Boolean returnValues = false;
        Boolean errorsFound = false;
        Boolean needEnable = false;

        String serverAnswer = "";

        List<SmppVendorIps> ipsList = smppVendorIpsService.findByAccountId(connector);
        if (ipsList.size() == 0) {
            return true;
        }

        for (SmppVendorIps currentIps : ipsList) {
            serverAnswer = "";

            if (currentIps.getCid().equals("")) {
                smppVendorIpsService.updateSmppVendorIpsCid(currentIps, "c" + currentIps.getId());
            }

            try {

                write("smppccm -s " + currentIps.getCid());

                TimeUnit.SECONDS.sleep(1);

                String line = readData();
                serverAnswer = serverAnswer + line;

                if (line.contains("Unknown")) {
                    write("smppccm -a");
                    write("cid " + currentIps.getCid());
                    needEnable = true;

                } else {
                    write("smppccm -u " + currentIps.getCid());
                }
                write("username " + currentIps.getSystemId());
                write("password " + currentIps.getPassword());
                write("host " + currentIps.getIp());
                write("port " + currentIps.getPort());
                write("password " + currentIps.getPassword());
                if (!currentIps.getSystemType().equals("")) {
                    write("systype " + currentIps.getSystemType());
                }

                if (currentIps.getSubmitThroughput()!=0L) {
                    write("submit_throughput " + Long.toString(currentIps.getSubmitThroughput()));
                } else {
                    write("submit_throughput 999999");
                }
                write("ok");
                TimeUnit.SECONDS.sleep(1);

                line = readData();
                serverAnswer = serverAnswer + line;


                if (line.contains("Successfully")) {
                    write("persist");
                    write("smppccm -1 " + currentIps.getCid());
                    write("persist");
                    if (connector.getDont_create_dp()==true) {
                        returnValues = true;
                    }
                } else {
                    errorsFound = true;
                    write("ko");
                }
                line = readData();
                serverAnswer = serverAnswer + line;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!errorsFound && connector.getDont_create_dp()!=true) {
            ipsList = smppVendorIpsService.findByAccountIdAllowed(connector);
            if (ipsList.isEmpty()) {
                write("mtrouter -r " + connector.getTag());
                write("persist");
                return true;
            }

            write("filter -a");
            write("type TagFilter");
            write("tag " + connector.getTag());
            write("fid Tag" + connector.getTag());
            write("ok");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String line = readData();
            serverAnswer = serverAnswer + line;

            write("mtrouter -a");
            if (ipsList.size() > 1) {
                write("type RandomRoundRobinMTRoute");
                String connectorsString = "";
                Integer iter = 0;
                for (SmppVendorIps currentIps : ipsList) {
                    iter++;
                    if (iter!= ipsList.size()) {
                        connectorsString = connectorsString + "smppc(" + currentIps.getCid() + ")" + ";";
                    } else {
                        connectorsString = connectorsString + "smppc(" + currentIps.getCid() + ")";
                    }
                }
                write("connectors " + connectorsString);
            } else {
                write("type StaticMTRoute");
                write("connector smppc(" + ipsList.get(0).getCid() + ")");
            }
            write("filters Tag" + connector.getTag());
            write("rate 0");
            write("order " + connector.getTag());
            write("ok");
            //write("ko");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            line = readData();
            serverAnswer = serverAnswer + line;
            if (line.contains("Successfully")) {
                write("persist");
                returnValues = true;
            }
        }

        return returnValues;
    }

    private Boolean UpdateDialpeer(VendorDialpeer dialpeer, Softswitch softswitch) {

        List<VendorDialpeerChild> ipsList = entityManager.createQuery("SELECT c FROM VendorDialpeerChild c WHERE c.vendorDialpeer = :dialpeer").setParameter("dialpeer", dialpeer).getResultList();
        Boolean returnValues = false;

        String serverAnswer = "";

        write("filter -a");
        write("type TagFilter");
        write("tag " + dialpeer.getTag());
        write("fid Tag" + dialpeer.getTag());
        write("ok");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String line = readData();
        serverAnswer = serverAnswer + line;


        List<String> cids = new ArrayList<>();
        for (VendorDialpeerChild currentIps : ipsList) {

            for (Integer iterator = 0; iterator < currentIps.getWeight();iterator++) {
                cids.add(currentIps.getSmppVendorIps().getCid());
            }
        }


        write("mtrouter -a");
        if (cids.size() > 1) {
            write("type RandomRoundRobinMTRoute");
            String connectorsString = "";
            Integer iter = 0;

            for (String currentIps : cids) {
                iter++;
                if (iter!= cids.size()) {
                    connectorsString = connectorsString + "smppc(" + currentIps + ")" + ";";
                } else {
                    connectorsString = connectorsString + "smppc(" + currentIps + ")";
                }
            }
            write("connectors " + connectorsString);
        } else {
            write("type StaticMTRoute");
            write("connector smppc(" + ipsList.get(0).getSmppVendorIps().getCid() + ")");
        }
        write("filters Tag" + dialpeer.getTag());
        write("rate 0");
        write("order " + dialpeer.getTag());
        write("ok");
        //write("ko");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        line = readData();
        serverAnswer = serverAnswer + line;
        if (line.contains("Successfully")) {
            write("persist");
            returnValues = true;
        }

        return returnValues;
    }

    private void deleteRoutingSmsRule(Softswitch softswitch, String id, Long accountId, Long changesRegisteryId) {
        Connection connection = null;
        ResultSet rs = null;
        Statement Statement = null;

        String deleteQuery = "";

        Boolean deleteChanges = true;

        //Query q = this.em.createQuery("SELECT c FROM RoutingSmsRule c WHERE c.id = :id");
        //q.setParameter("id", UUID.fromString(id));
        //RoutingSmsRule currentRule = (RoutingSmsRule) q.getSingleResult();

        List<String> queries = new ArrayList<>();


        SmppClientAccount currentAccount = smppClientAccountService.getSmppClientAccountById(accountId);
        if (currentAccount!=null) {
            List<SmppClientSystemId> listSystemIds = smppClientSystemIdService.findSmppClientSystemIdsByAccount(currentAccount);;
            if (listSystemIds.size()==0) {
                return;
            }
            for (SmppClientSystemId currentSystemId : listSystemIds) {
                if (currentSystemId.getUid().equals("")) {
                    deleteChanges = false;
                    continue;
                }
                queries.add("DELETE FROM " + currentSystemId.getUid() + " WHERE id = '" + id + "';");
            }
        }


        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + softswitch.getHost() + ":" + softswitch.getMysqlPort() + "/" + softswitch.getMysqlDatabase(), softswitch.getMysqlUsername(), softswitch.getMysqlPassword());
            Statement = connection.createStatement();
            for (String query : queries) {
                Statement.executeUpdate(query);
            }

            if (deleteChanges) {
                changesRegisteryService.deleteChangesRegisteryById(changesRegisteryId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (rs != null) try {
                rs.close();
            } catch (SQLException logOrIgnore) {
            }
            if (Statement != null) try {
                Statement.close();
            } catch (SQLException logOrIgnore) {
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException logOrIgnore) {
            }
        }
        if (rs != null) try {
            rs.close();
        } catch (SQLException logOrIgnore) {
        }
        if (Statement != null) try {
            Statement.close();
        } catch (SQLException logOrIgnore) {
        }
        if (connection != null) try {
            connection.close();
        } catch (SQLException logOrIgnore) {
        }
    }

    private void updateRoutingSmsRule(Softswitch softswitch, String id, Long changesRegisteryId) {

        Boolean deleteChanges = true;

        Query q = this.em.createQuery("SELECT c FROM RoutingSmsRule c WHERE c.id = :id");
        q.setParameter("id", id);
        RoutingSmsRule currentRule = (RoutingSmsRule) q.getSingleResult();

        if (currentRule!= null) {
            Connection connection = null;
            ResultSet rs = null;
            Statement Statement = null;


            //////////////////////////////////////////// Данные роутинга
            String mcc = currentRule.getMcc();
            String mnc = currentRule.getMnc();
            if (mnc.equals("Flt")) {
                mnc = "NULL";
            }

            Integer vendor = 0;
            if (currentRule.getVendorType() == VendorTypes.VendorDialpeer) {
                vendor = vendorDialpeerService.getVendorDialpeerById(currentRule.getVendor()).getTag();
            } else {
                vendor = smppVendorAccountService.getSmppVendorAccountById(currentRule.getVendor()).getTag();
            }

            Integer vendor_filter = 0;
            if (currentRule.getVendorFilterType() == VendorTypes.VendorDialpeer) {
                vendor_filter = vendorDialpeerService.getVendorDialpeerById(currentRule.getVendorFilter()).getTag();
            } else {
                SmppVendorAccount currentVendorAccount = smppVendorAccountService.getSmppVendorAccountById(currentRule.getVendorFilter());
                if (currentVendorAccount != null) {
                    vendor_filter = currentVendorAccount.getTag();
                } else {
                    vendor_filter = null;
                }

            }

            String source_filter = currentRule.getSourceFilter();
            if (source_filter.equals("")) {
                source_filter = "NULL";
            } else {
                source_filter = "'" + source_filter + "'";
            }
            String message_filter = currentRule.getMessageFilter();
            if (message_filter.equals("")) {
                message_filter = "NULL";
            } else {
                message_filter = "'" + message_filter + "'";
            }
            Boolean filtered = currentRule.getFiltered();
            Integer rule_order = currentRule.getRuleOrder();
            String new_source_addr = currentRule.getNewSourceAddr();
            if (new_source_addr != null) {
                if (new_source_addr.equals("")) {
                    new_source_addr = "NULL";
                } else {
                    new_source_addr = "'" + new_source_addr + "'";
                }
            } else {
                new_source_addr = "NULL";
            }

            String rule_id = currentRule.getId();
            Integer weight = currentRule.getWeight();
            Boolean replace_source = currentRule.getReplaceSource();
            Boolean replcae_registered_delivery = currentRule.getReplaceRegisteredDelivery();
            Boolean registered_delivery = currentRule.getRegisteredDelivery();
            Boolean filtered_content = currentRule.getFilteredContent();
            ///////////////////////////////////////////////////////////////////////////////////

            List<SmppClientSystemId> systemIdList = smppClientSystemIdService.findSmppClientSystemIdsByAccount(smppClientAccountService.getSmppClientAccountById(currentRule.getAccount()));
            for (SmppClientSystemId currentSystemId : systemIdList) {
                if (currentSystemId.getUid().equals("")) {
                    deleteChanges = false;
                    continue;
                }
                try {


                    connection = DriverManager
                            .getConnection("jdbc:mysql://" + softswitch.getHost() + ":" + softswitch.getMysqlPort() + "/" + softswitch.getMysqlDatabase(), softswitch.getMysqlUsername(), softswitch.getMysqlPassword());
                    Statement = connection.createStatement();
                    rs = Statement.executeQuery("SELECT COUNT(*) AS count FROM " + currentSystemId.getUid() + " WHERE id = '" + id + "';");
                    Integer count = 0;
                    if (rs.next()) {
                        count = rs.getInt("count");
                    }
                    String query = "";
                    if (count > 0) {
                        query = "UPDATE " + currentSystemId.getUid() + " SET mcc = " + mcc + ", mnc = " + mnc + ", vendor = " + vendor + ", source_filter = " + source_filter + ", " +
                                            "message_filter = " + message_filter + ", filtered = " + filtered + ", vendor_filter = " + vendor_filter + ", rule_order = " + rule_order +
                                            ", newsource_addr = " + new_source_addr + ", id = '" + rule_id + "', weight = " + weight + ", replace_source = " + replace_source +
                                            ", replace_registered_delivery = " + replcae_registered_delivery + ", registered_delivery = " + registered_delivery +
                                            ", filtered_content = " + filtered_content + " WHERE id = '" + rule_id + "';";
                    } else {
                        query = "INSERT INTO " + currentSystemId.getUid() + " (mcc, mnc, vendor, source_filter, message_filter, filtered, vendor_filter, rule_order, newsource_addr, id, weight," +
                                " replace_source, replace_registered_delivery, registered_delivery, filtered_content) VALUES (" + mcc + ", " + mnc + ", " + vendor + ", " + source_filter + ", " +
                                message_filter + ", " + filtered + ", " + vendor_filter + ", " + rule_order + ", " + new_source_addr + ", '" + rule_id + "', " + weight + ", " + replace_source + ", " +
                                replcae_registered_delivery + ", " + registered_delivery + ", " + filtered_content + ")";
                    }
                    Statement.executeUpdate(query);


                    if (rs != null) try {
                        rs.close();
                    } catch (SQLException logOrIgnore) {
                    }
                    if (Statement != null) try {
                        Statement.close();
                    } catch (SQLException logOrIgnore) {
                    }
                    if (connection != null) try {
                        connection.close();
                    } catch (SQLException logOrIgnore) {
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    if (rs != null) try {
                        rs.close();
                    } catch (SQLException logOrIgnore) {
                    }
                    if (Statement != null) try {
                        Statement.close();
                    } catch (SQLException logOrIgnore) {
                    }
                    if (connection != null) try {
                        connection.close();
                    } catch (SQLException logOrIgnore) {
                    }
                }
            }
            if (deleteChanges) {
                changesRegisteryService.deleteChangesRegisteryById(changesRegisteryId);
            }
        }
    }

    private Boolean updateClientRate(ClientRatesCurrent rate, Softswitch softswitch) {
        Boolean returnValue = false;

        Connection connection = null;
        ResultSet rs = null;
        ResultSet rsUpdate = null;
        Statement statement = null;

        SmppClientAccount clientAccount = rate.getSmppClientAccount();
        List<SmppClientSystemId> list = entityManager.createQuery("SELECT c FROM SmppClientSystemId c WHERE c.smppClientAccount = :clientAccount").setParameter("clientAccount", clientAccount).getResultList();
        for (SmppClientSystemId currentId : list) {
            String mcc = rate.getMcc();
            if (mcc == null) {
                mcc = "";
            }
            String mnc = rate.getMnc();
            if (mnc == null) {
                mnc = "Flt";
            } else {
                if (mnc.equals("")) {
                    mnc = "Flt";
                }
            }
            String price = rate.getRate().toString();
            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + softswitch.getHost() + ":" + softswitch.getMysqlPort() + "/" + softswitch.getMysqlDatabase(), softswitch.getMysqlUsername(), softswitch.getMysqlPassword());
                statement = connection.createStatement();
                rs = statement.executeQuery("SELECT client FROM actual_client_prices WHERE client = '" + currentId.getUid() + "' AND mcc = '" + mcc + "' AND mnc = '" + mnc + "'");

                if (rs.next()) {
                    statement.executeUpdate("UPDATE actual_client_prices SET price = " + price + " WHERE client = '" + currentId.getUid() + "' AND mcc = '" + mcc + "' AND mnc = '" + mnc + "'");
                } else {
                    statement.executeUpdate("INSERT INTO actual_client_prices (client, mcc, mnc, price) VALUES ('" + currentId.getUid() + "','" + mcc + "','" + mnc + "'," + price + ");");
                }
                returnValue = true;

                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (rsUpdate != null) try {
                    rsUpdate.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statement != null) try {
                    statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connection != null) try {
                    connection.close();
                } catch (SQLException logOrIgnore) {
                }
            } catch (SQLException e) {
                e.printStackTrace();

                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (rsUpdate != null) try {
                    rsUpdate.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statement != null) try {
                    statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connection != null) try {
                    connection.close();
                } catch (SQLException logOrIgnore) {
                }
            }



        }








        return returnValue;
    }

    private Boolean updateVendorRate(VendorRatesCurrent rate, Softswitch softswitch) {
        Boolean returnValue = false;

        Connection connection = null;
        ResultSet rs = null;
        ResultSet rsUpdate = null;
        Statement statement = null;

        Query q = entityManager.createNativeQuery("SELECT ips.cid FROM vendor_rates_current AS rate\n" +
                "LEFT JOIN smpp_vendor_ips AS ips ON ips.id = rate.smppvendorips_id\n" +
                "WHERE rate.id = ?");
        q.setParameter(1, rate.getId());
        List<String> currentCidList = q.getResultList();

        String cid = currentCidList.get(0);
        String mcc = rate.getMcc();
        if (mcc == null) {
            mcc = "";
        }
        String mnc = rate.getMnc();
        if (mnc == null) {
            mnc = "Flt";
        } else {
            if (mnc.equals("")) {
                mnc = "Flt";
            }
        }
        String price = rate.getRate().toString();

        if (cid != null) {
            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + softswitch.getHost() + ":" + softswitch.getMysqlPort() + "/" + softswitch.getMysqlDatabase(), softswitch.getMysqlUsername(), softswitch.getMysqlPassword());
                statement = connection.createStatement();
                rs = statement.executeQuery("SELECT client FROM actual_vendor_prices WHERE client = '" + cid + "' AND mcc = '" + mcc + "' AND mnc = '" + mnc + "'");

                if (rs.next()) {
                    statement.executeUpdate("UPDATE actual_vendor_prices SET price = " + price + " WHERE client = '" + cid + "' AND mcc = '" + mcc + "' AND mnc = '" + mnc + "'");
                } else {
                    statement.executeUpdate("INSERT INTO actual_vendor_prices (client, mcc, mnc, price) VALUES ('" + cid + "','" + mcc + "','" + mnc + "'," + price + ");");
                }
                returnValue = true;

                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (rsUpdate != null) try {
                    rsUpdate.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statement != null) try {
                    statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connection != null) try {
                    connection.close();
                } catch (SQLException logOrIgnore) {
                }


            } catch (SQLException e) {
                e.printStackTrace();

                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (rsUpdate != null) try {
                    rsUpdate.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statement != null) try {
                    statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connection != null) try {
                    connection.close();
                } catch (SQLException logOrIgnore) {
                }
            }
        }






        return returnValue;
    }
}
