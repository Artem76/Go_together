package app.service.mdrXmlList;

import app.entity.*;
import app.entityXML.mdrList.MdrXml;
import app.entityXML.mdrList.MdrXmlList;
import app.entityXML.reportSmsStatistic.MessagingFlowRow;
import app.service.refbook.RefbookService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.smppVendorIps.SmppVendorIpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 11.08.2017.
 */
@Service
public class MdrXmlListServiceImpl implements MdrXmlListService {

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    @Autowired
    SmppClientAccountService smppClientAccountService;

    @Autowired
    SmppVendorAccountService smppVendorAccountService;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    SmppVendorIpsService smppVendorIpsService;

    @Autowired
    RefbookService refbookService;

    @Override
    public MdrXmlList getMdrXmlListByParam(List<String> msgid_list_filter, //+++
                                           List<String> vendor_msgid_list_filter, //+++
                                           String created_at_start_filter, //+++
                                           String created_at_end_filter, //+++
                                           String smpp_client_account_id_filter,//+++
                                           String smpp_vendor_account_id_filter,//+++
                                           List<String> destination_addr_list_filter,//+++
                                           List<String> source_addr_list_filter,//+++
                                           String mcc_filter,//+++
                                           List<String> mnc_list_filter,//+++
                                           Long first_number,
                                           Long last_number,
                                           Integer amount_of_rows,
                                           String direction,// "next" or "previous"
                                           String sort_list_in_string,
                                           Long min_id,
                                           Long max_id) {


        List<MdrXml> mdrXmls = new ArrayList<>();
        Long new_first_number = 0l;
        Long new_last_number = 0l;
        String finalQuery = "";
        Connection connectionLocal = null;
        Statement statementLocal = null;
        ResultSet rs = null;



        String query = " FROM mdr WHERE created_at between '" + created_at_start_filter + "' AND '" + created_at_end_filter + "' ";
        // msgid Filter
        if (msgid_list_filter.size()!=0 && !(msgid_list_filter.size()==1 && msgid_list_filter.get(0).equals(""))) {
            query = query + " AND (";
            Integer i = 1;
            for (String currentMsgid : msgid_list_filter) {
                query = query + "msgid = '" + currentMsgid.trim() + "'";
                if (i!=msgid_list_filter.size()) {
                    query = query + " OR ";
                }
                i++;
            }
            query = query + ")";
        }
        // Vendor msgid filter
        if (vendor_msgid_list_filter.size()!=0 && !(vendor_msgid_list_filter.size()==1 && vendor_msgid_list_filter.get(0).equals(""))) {
            query = query + " AND (";
            Integer i = 1;
            for (String currentvendorMsgid : vendor_msgid_list_filter) {
                query = query + "vendor_msgid = '" + currentvendorMsgid.trim() + "'";
                if (i!=vendor_msgid_list_filter.size()) {
                    query = query + " OR ";
                }
                i++;
            }
            query = query + ")";
        }
        // Destination Address Filter
        if (destination_addr_list_filter.size()!=0 && !(destination_addr_list_filter.size()==1 && destination_addr_list_filter.get(0).equals(""))) {
            query = query + " AND (";
            Integer i = 1;
            for (String currentDestinationAddr : destination_addr_list_filter) {
                query = query + "destination_addr = '" + currentDestinationAddr.trim() + "'";
                if (i!=destination_addr_list_filter.size()) {
                    query = query + " OR ";
                }
                i++;
            }
            query = query + ")";
        }
        // Source Address Filter
        if (source_addr_list_filter.size()!=0 && !(source_addr_list_filter.size()==1 && source_addr_list_filter.get(0).equals(""))) {
            query = query + " AND (";
            Integer i = 1;
            for (String currentSourceAddr : source_addr_list_filter) {
                query = query + "source_addr = '" + currentSourceAddr.trim() + "'";
                if (i!=source_addr_list_filter.size()) {
                    query = query + " OR ";
                }
                i++;
            }
            query = query + ")";
        }
        // Client account filter
        if (!smpp_client_account_id_filter.equals("0")) {
            SmppClientAccount currentClientAccount = smppClientAccountService.getSmppClientAccountById(Long.valueOf(smpp_client_account_id_filter));
            List<SmppClientSystemId> currentClientSystemIdList = currentClientAccount.getSmppClientSystemIdList();
            if (currentClientSystemIdList.size() != 0) {
                Integer i = 1;
                query = query + " AND (";
                for (SmppClientSystemId currentSystemId : currentClientSystemIdList) {
                    query = query + " uid = '" + currentSystemId.getUid() + "'";
                    if (i!=currentClientSystemIdList.size()) {
                        query = query + " OR ";
                    }
                    i++;
                }
                query = query + ")";
            }
        }
        // Vendor account filter
        if (!smpp_vendor_account_id_filter.equals("0")) {
            SmppVendorAccount currentVendorAccount = smppVendorAccountService.getSmppVendorAccountById(Long.valueOf(smpp_vendor_account_id_filter));
            List<SmppVendorIps> currentVendorIpsList = currentVendorAccount.getSmppVendorIpsList();
            if (currentVendorIpsList.size() != 0) {
                Integer i = 1;
                query = query + " AND (";
                for (SmppVendorIps currentVendorIps : currentVendorIpsList) {
                    query = query + " routed_cid = '" + currentVendorIps.getCid() + "'";
                    if (i!=currentVendorIpsList.size()) {
                        query = query + " OR ";
                    }
                    i++;
                }
                query = query + ")";
            }
        }
        //MCC filter
        if(!mcc_filter.equals("0")) {
            query = query + " AND mcc = '" + mcc_filter + "'";
        }
        //MNC filter
        if (mnc_list_filter.size()!=0 && !(mnc_list_filter.size()==1 && mnc_list_filter.get(0).equals(""))) {
            query = query + " AND (";
            Integer i = 1;
            for (String currentMNC : mnc_list_filter) {
                query = query + "mnc = '" + currentMNC.trim() + "'";
                if (i!=mnc_list_filter.size()) {
                    query = query + " OR ";
                }
                i++;
            }
            query = query + ")";
        }

        if (!sort_list_in_string.equals("")) {
            query = query + " ORDER BY " + sort_list_in_string;
        }



        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statementLocal = connectionLocal.createStatement();

            String queryIdLimit = "";

            if (max_id.equals(0L) && min_id.equals(0L)) {
                rs = statementLocal.executeQuery(" SELECT MAX(id) AS max_id, MIN(id) AS min_id " + query);
                if (rs.next()) {
                    max_id = rs.getLong("max_id");
                    if (max_id == null) {
                        max_id = 0L;
                    }

                    min_id = rs.getLong("min_id");
                    if (min_id == null) {
                        min_id = 0L;
                    }
                }
            } else {
                queryIdLimit = " AND id BETWEEN " + min_id + " AND " + max_id;
            }

            query = "SELECT * FROM (SELECT ROW_NUMBER() OVER (), * FROM ( SELECT * " + query + queryIdLimit +" ) AS numbered_data) AS data_for_condition";

            if (direction.equals("next")) {
                query = query + " WHERE row_number > " + last_number;
            }

            if (direction.equals("previous")) {
                query = query + " WHERE row_number > " + first_number + " - " + (amount_of_rows + 1);
            }

            query = query + " LIMIT " + amount_of_rows;

            rs = statementLocal.executeQuery(query);

            Boolean searchFirstNumber = true;

            while (rs.next()) {

                if (searchFirstNumber) {
                    new_first_number = rs.getLong("row_number");
                    searchFirstNumber = false;
                }

                String sourceConnector = "";
                if (rs.getString("source_connector").equals("smppsapi")) {
                    sourceConnector = "SMPP";
                } else {
                    sourceConnector = rs.getString("source_connector");
                }
                String uid = rs.getString("uid");
                String routed_cid = rs.getString("routed_cid");
                SmppClientSystemId currentSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(uid);
                if (currentSystemId!=null) {
                    SmppClientAccount currentAccount = currentSystemId.getSmppClientAccount();
                    if (currentAccount!=null) {
                        uid = currentAccount.getCustomer().getName() + " - " + currentAccount.getName() + " - " + currentSystemId.getSystemId();
                    }
                }
                SmppVendorIps currentIp = smppVendorIpsService.getSmppVendorIpsByCid(routed_cid);
                if (currentIp!=null) {
                    SmppVendorAccount currentVendorAccount = currentIp.getSmppVendorAccount();
                    if (currentVendorAccount!=null) {
                        routed_cid = currentVendorAccount.getCustomer().getName() + " - " + currentVendorAccount.getName() + " - " + currentIp.getSystemId();
                    }
                }

                mdrXmls.add(new MdrXml(sourceConnector, rs.getString("msgid"), rs.getTimestamp("created_at"), rs.getString("mcc"), rs.getString("mnc"), uid, routed_cid, rs.getString("vendor_msgid"), rs.getString("source_addr"), rs.getString("destination_addr"), rs.getInt("pdu_count"), rs.getInt("trials"), rs.getTimestamp("status_at"), rs.getBoolean("registered_delivery"), rs.getFloat("client_price"), rs.getFloat("vendor_price"), rs.getString("submit_status"), rs.getString("delivery_status"), rs.getLong("delivery_time"), rs.getString("short_message")));
                new_last_number = rs.getLong("row_number");
            }

            if (rs != null) try {
                rs.close();
            } catch (SQLException logOrIgnore) {
            }
            if (statementLocal != null) try {
                statementLocal.close();
            } catch (SQLException logOrIgnore) {
            }
            if (connectionLocal != null) try {
                connectionLocal.close();
            } catch (SQLException logOrIgnore) {
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (rs != null) try {
                rs.close();
            } catch (SQLException logOrIgnore) {
            }
            if (statementLocal != null) try {
                statementLocal.close();
            } catch (SQLException logOrIgnore) {
            }
            if (connectionLocal != null) try {
                connectionLocal.close();
            } catch (SQLException logOrIgnore) {
            }
        }

        MdrXmlList mdrXmlList = new MdrXmlList(mdrXmls, new_first_number, new_last_number, min_id, max_id);
        return mdrXmlList;
    }


    @Override
    public List<MessagingFlowRow> getMessagingFlowList() {
        List<MessagingFlowRow> returnList = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        Statement Statement = null;

        try {
            connection = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            Statement = connection.createStatement();
            rs = Statement.executeQuery("SELECT uid, routed_cid, mcc, mnc, source_addr, destination_addr, short_message FROM mdr WHERE created_at BETWEEN (current_timestamp at time zone 'UTC' - Interval '60 second') AND (current_timestamp at time zone 'UTC')");
            while (rs.next()) {
                String clientName = "Unknown (" + rs.getString("uid") + ")";
                String vendorName = "Unknown (" + rs.getString("routed_cid") + ")";
                String clientAccountName = "Unknown (" + rs.getString("uid") + ")";
                String vendorAccountName = "Unknown (" + rs.getString("routed_cid") + ")";
                String countryName = "Unknown(" + rs.getString("mcc") + ")";
                String networkName = "Unknown(" + rs.getString("mnc") + ")";

                SmppClientSystemId currentSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(rs.getString("uid"));
                SmppVendorIps currentVendorIp = smppVendorIpsService.getSmppVendorIpsByCid(rs.getString("routed_cid"));

                if (currentSystemId != null) {
                    clientName = currentSystemId.getSmppClientAccount().getCustomer().getName();
                    clientAccountName = currentSystemId.getSmppClientAccount().getName();
                }

                if (currentVendorIp != null) {
                    vendorName = currentVendorIp.getSmppVendorAccount().getCustomer().getName();
                    vendorAccountName = currentVendorIp.getSmppVendorAccount().getName();
                }

                Refbook currentCountry = refbookService.getRootRefbookByMcc(rs.getString("mcc"));
                Refbook currentNetwork = null;
                List<Refbook> currentNetworkList = refbookService.getRefbookByMccAndMnc(rs.getString("mcc"), rs.getString("mnc"));
                if (currentNetworkList.size()!=0) {
                    currentNetwork = currentNetworkList.get(0);
                }


                if (currentCountry != null) {
                    countryName = currentCountry.getCountry();
                }

                if (currentNetwork != null) {
                    networkName = currentNetwork.getNetwork();
                    if (networkName.equals("Flt")) {
                        networkName = "FLAT";
                    }
                }

                if(networkName.equals("Unknown(Flt)")) {
                    networkName = "FLAT";
                }

                returnList.add(new MessagingFlowRow(clientName, clientAccountName, countryName, networkName, rs.getString("source_addr"),
                        rs.getString("destination_addr"), vendorName, vendorAccountName, rs.getString("short_message")));
            }
            if (rs != null) {
                rs.close();
            }
            if (Statement != null) {
                Statement.close();
            }
            if (connection != null) {
                connection.close();
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

        return returnList;
    }
}
