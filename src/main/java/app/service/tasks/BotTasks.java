package app.service.tasks;

import app.entity.*;
import app.entity.enums.NotificationIssues;
import app.repository.RoutingSmsRuleRepository;
import app.repository.VendorDialpeerRepository;
import app.service.settings.SettingsService;
import app.service.customer.CustomerService;
import app.service.notifications.NotificationsService;
import app.service.refbook.RefbookService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.softswitch.SoftswitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.util.Date;

/**
 * Created by Олег on 04.12.2017.
 */

@Service
public class BotTasks {

    @Autowired
    NotificationsService notificationsService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    CustomerService customerService;

    @Autowired
    RefbookService refbookService;

    @Autowired
    SoftswitchService softswitchService;

    @Autowired
    SmppVendorAccountService smppVendorAccountService;

    @Autowired
    VendorDialpeerRepository vendorDialpeerRepository;

    @Autowired
    RoutingSmsRuleRepository routingSmsRuleRepository;

    @Autowired
    SettingsService settingsService;



    public void runParallelStream(){


//        Boolean oneTime = true;
//
//        if(oneTime) {
//
//            Connection psqlConn = null;
//            Statement psqlSTatment = null;
//
//
//
//
//
//            Connection connection = null;
//            try {
//                connection = DriverManager
//                        .getConnection("jdbc:mysql://" + "88.99.150.30" + ":" + "3306" + "/" + "routing", "sms", "oleg0642");
//                Statement statement = connection.createStatement();
//                Statement updateSt = connection.createStatement();
//                ResultSet rs = statement.executeQuery("SELECT *,\"acesms_pr\" as table_name  FROM acesms_pr UNION ALL \n" +
//                        "SELECT *,\"acesms_ss\" as table_name  FROM acesms_ss UNION ALL \n" +
//                        "SELECT *,\"acesms_ws\" as table_name  FROM acesms_ws UNION ALL \n" +
//                        "SELECT *,\"acewh2\" as table_name  FROM acewh2    UNION ALL \n" +
//                        "SELECT *,\"alienics_pr\" as table_name  FROM alienics_pr UNION ALL \n" +
//                        "SELECT *,\"alienics_ws\" as table_name  FROM alienics_ws UNION ALL \n" +
//                        "SELECT *,\"antwerp_pr\" as table_name  FROM antwerp_pr UNION ALL \n" +
//                        "SELECT *,\"antwerp_sim\" as table_name  FROM antwerp_sim UNION ALL \n" +
//                        "SELECT *,\"antwerp_ws\" as table_name  FROM antwerp_ws UNION ALL \n" +
//                        "SELECT *,\"antwerp_ws\" as table_name  FROM antwerp_ws2 UNION ALL \n" +
//                        "SELECT *,\"avys_pr\" as table_name  FROM avys_pr   UNION ALL \n" +
//                        "SELECT *,\"avys_ws\" as table_name  FROM avys_ws   UNION ALL \n" +
//                        "SELECT *,\"bongolive_pr\" as table_name  FROM bongolive_pr UNION ALL \n" +
//                        "SELECT *,\"bongolive_ws\" as table_name  FROM bongolive_ws UNION ALL \n" +
//                        "SELECT *,\"broadband_pr\" as table_name  FROM broadband_pr UNION ALL \n" +
//                        "SELECT *,\"broadband_sim\" as table_name  FROM broadband_sim UNION ALL \n" +
//                        "SELECT *,\"broadband_ws\" as table_name  FROM broadband_ws UNION ALL \n" +
//                        "SELECT *,\"broadnet_ws\" as table_name  FROM broadnet_ws UNION ALL \n" +
//                        "SELECT *,\"broadnet_ws2\" as table_name  FROM broadnet_ws2 UNION ALL \n" +
//                        "SELECT *,\"busybee_pr\" as table_name  FROM busybee_pr UNION ALL \n" +
//                        "SELECT *,\"busybee_pr\" as table_name  FROM busybee_ws UNION ALL \n" +
//                        "SELECT *,\"ccs_pr\" as table_name  FROM ccs_pr    UNION ALL \n" +
//                        "SELECT *,\"ccs_ws\" as table_name  FROM ccs_ws    UNION ALL \n" +
//                        "SELECT *,\"dexatel_pr\" as table_name  FROM dexatel_pr UNION ALL \n" +
//                        "SELECT *,\"dexatel_ws\" as table_name  FROM dexatel_ws UNION ALL \n" +
//                        "SELECT *,\"flames_pr\" as table_name  FROM flames_pr UNION ALL \n" +
//                        "SELECT *,\"flames_ws\" as table_name  FROM flames_ws UNION ALL \n" +
//                        "SELECT *,\"flynode_ws\" as table_name  FROM flynode_ws UNION ALL \n" +
//                        "SELECT *,\"fortytwo_pr\" as table_name  FROM fortytwo_pr UNION ALL \n" +
//                        "SELECT *,\"fortytwo_ws\" as table_name  FROM fortytwo_ws UNION ALL \n" +
//                        "SELECT *,\"generenka\" as table_name  FROM generenka UNION ALL \n" +
//                        "SELECT *,\"gts_ws\" as table_name  FROM gts_ws    UNION ALL \n" +
//                        "SELECT *,\"intico_pr\" as table_name  FROM intico_pr UNION ALL \n" +
//                        "SELECT *,\"intico_ws\" as table_name  FROM intico_ws UNION ALL \n" +
//                        "SELECT *,\"intis_pr\" as table_name  FROM intis_pr  UNION ALL \n" +
//                        "SELECT *,\"kolt_dr\" as table_name  FROM kolt_dr   UNION ALL \n" +
//                        "SELECT *,\"kolt_sim\" as table_name  FROM kolt_sim  UNION ALL \n" +
//                        "SELECT *,\"kolt_ws\" as table_name FROM kolt_ws   UNION ALL \n" +
//                        "SELECT *,\"lexico_dr\" as table_name FROM lexico_dr UNION ALL \n" +
//                        "SELECT *,\"lexico_sim\" as table_name  FROM lexico_sim UNION ALL \n" +
//                        "SELECT *,\"lexico_ws\" as table_name  FROM lexico_ws UNION ALL \n" +
//                        "SELECT *,\"lleida_pr\" as table_name  FROM lleida_pr UNION ALL \n" +
//                        "SELECT *,\"lleida_ws\" as table_name  FROM lleida_ws UNION ALL \n" +
//                        "SELECT *,\"lovy_ws\" as table_name  FROM lovy_ws   UNION ALL \n" +
//                        "SELECT *,\"mastersk_pr\" as table_name  FROM mastersk_pr UNION ALL \n" +
//                        "SELECT *,\"mastersk_ws\" as table_name  FROM mastersk_ws UNION ALL \n" +
//                        "SELECT *,\"maverick_ws\" as table_name  FROM maverick_ws UNION ALL \n" +
//                        "SELECT *,\"mhd_ws\" as table_name  FROM mhd_ws    UNION ALL \n" +
//                        "SELECT *,\"mitto_dr\" as table_name  FROM mitto_dr  UNION ALL \n" +
//                        "SELECT *,\"mitto_pr\" as table_name  FROM mitto_pr  UNION ALL \n" +
//                        "SELECT *,\"mitto_ws\" as table_name  FROM mitto_ws  UNION ALL \n" +
//                        "SELECT *,\"monty_ws1\" as table_name  FROM monty_ws1 UNION ALL \n" +
//                        "SELECT *,\"mrm_mkt\" as table_name  FROM mrm_mkt   UNION ALL \n" +
//                        "SELECT *,\"mrmess_ws\" as table_name  FROM mrmess_ws UNION ALL \n" +
//                        "SELECT *,\"n1\" as table_name  FROM n1        UNION ALL \n" +
//                        "SELECT *,\"n2\" as table_name  FROM n2        UNION ALL \n" +
//                        "SELECT *,\"relario_pr\" as table_name  FROM relario_pr UNION ALL \n" +
//                        "SELECT *,\"relario_ws\" as table_name  FROM relario_ws UNION ALL \n" +
//                        "SELECT *,\"routesms_pr\" as table_name  FROM routesms_pr UNION ALL \n" +
//                        "SELECT *,\"routesms_ws\" as table_name  FROM routesms_ws UNION ALL \n" +
//                        "SELECT *,\"rsc_pr\" as table_name  FROM rsc_pr    UNION ALL \n" +
//                        "SELECT *,\"rsc_sim\" as table_name  FROM rsc_sim   UNION ALL \n" +
//                        "SELECT *,\"rsc_ws\" as table_name  FROM rsc_ws    UNION ALL \n" +
//                        "SELECT *,\"signatel_dr\" as table_name  FROM signatel_dr UNION ALL \n" +
//                        "SELECT *,\"signatel_sim\" as table_name  FROM signatel_sim UNION ALL \n" +
//                        "SELECT *,\"signatel_ws\" as table_name  FROM signatel_ws UNION ALL \n" +
//                        "SELECT *,\"silver_pr\" as table_name  FROM silver_pr UNION ALL \n" +
//                        "SELECT *,\"silver_ws\" as table_name  FROM silver_ws UNION ALL \n" +
//                        "SELECT *,\"smscons_mkt\" as table_name  FROM smscons_mkt UNION ALL \n" +
//                        "SELECT *,\"smscons_pr\" as table_name  FROM smscons_pr UNION ALL \n" +
//                        "SELECT *,\"smscons_ws\" as table_name  FROM smscons_ws UNION ALL \n" +
//                        "SELECT *,\"speedflow_ws\" as table_name  FROM speedflow_ws UNION ALL \n" +
//                        "SELECT *,\"svm_sim\" as table_name  FROM svm_sim   UNION ALL \n" +
//                        "SELECT *,\"svm_ws\" as table_name  FROM svm_ws    UNION ALL \n" +
//                        "SELECT *,\"unifonic_ws\" as table_name  FROM unifonic_ws UNION ALL \n" +
//                        "SELECT *,\"viahub_pr\" as table_name  FROM viahub_pr UNION ALL \n" +
//                        "SELECT *,\"viahub_sim\" as table_name  FROM viahub_sim UNION ALL \n" +
//                        "SELECT *,\"viahub_ws\" as table_name  FROM viahub_ws UNION ALL \n" +
//                        "SELECT *,\"vivaldi_ws\" as table_name  FROM vivaldi_ws UNION ALL \n" +
//                        "SELECT *,\"wavecell_dc\" as table_name  FROM wavecell_dc UNION ALL \n" +
//                        "SELECT *,\"wavecell_ss7\" as table_name  FROM wavecell_ss7 UNION ALL \n" +
//                        "SELECT *,\"zen_pr\" as table_name  FROM zen_pr    UNION ALL \n" +
//                        "SELECT *,\"zen_ws\" as table_name  FROM zen_ws; ");
//
//
//                Integer it = 0;
//                Integer skiped = 0;
//
////                Integer idIt = 0;
////                while (rs.next()) {
////                    idIt++;
////                    updateSt.executeUpdate("UPDATE " + rs.getString("table_name") + " SET id = '" + idIt + "' WHERE id = '" + rs.getString("id") + "' and mcc = " + rs.getString("mcc") + " and mnc = " + rs.getString("mnc"));
////                }
//
//                psqlConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/prob", "postgres", "root");
//                psqlSTatment = psqlConn.createStatement();
//
//                while (rs.next()) {
//                    it++;
//                    SmppClientSystemId currId = smppClientSystemIdService.getSmppClientSystemIdByUid(rs.getString("table_name"));
//                    if (currId == null) {
//                        System.out.println(rs.getString("table_name"));
//                        continue;
//                    }
//
//
//                    List<BigInteger> currentAccountidList = entityManager.createNativeQuery("SELECT smppclientaccount_id FROM smpp_client_system_id WHERE id = " + currId.getId()).getResultList();
//
//                    SmppClientAccount currentAccount = currId.getSmppClientAccount();
//                    String mcc = rs.getString("mcc");
//                    String mnc = rs.getString("mnc");
//                    if (mnc == null) {
//                        mnc = "Flt";
//                    }
//                    String id = rs.getString("id");
//                    Boolean filtered = rs.getBoolean("filtered");
//                    Boolean filtered_content = rs.getBoolean("filtered_content");
//                    String message_filter = rs.getString("message_filter");
//                    String newsourceaddr = rs.getString("newsource_addr");
//                    Boolean registered_delivery = rs.getBoolean("registered_delivery");
//                    Boolean replace_registered_delivery = rs.getBoolean("replace_registered_delivery");
//                    Boolean replace_source = rs.getBoolean("replace_source");
//                    Integer rule_order = rs.getInt("rule_order");
//                    String source_filter = rs.getString("source_filter");
//                    Integer vendor = rs.getInt("vendor");
//                    Integer vendor_filter = rs.getInt("vendor_filter");
//                    Integer weight = rs.getInt("weight");
//
//
//                    if (vendor.equals(56)) {
//                        vendor = 6;
//                    }
//
//                    if (vendor_filter.equals(56)) {
//                        vendor_filter = 6;
//                    }
//
//                    if (filtered == null) {
//                        filtered = false;
//                    }
//                    if (filtered_content == null) {
//                        filtered_content = false;
//                    }
//
//                    if (registered_delivery == null) {
//                        registered_delivery = false;
//                    }
//
//                    if (replace_registered_delivery == null) {
//                        replace_registered_delivery = false;
//                    }
//
//                    if (replace_source == null) {
//                        replace_source = false;
//                    }
//
//
//                    ///////////////////////
//                    System.out.println("////////////////////////");
//                    System.out.println("vendor : " + vendor);
//                    System.out.println("vendor fiulters : " + vendor_filter);
//                    Long vendorId = null;
//                    if (vendor.equals(110) || vendor.equals(111) || vendor.equals(1) || vendor.equals(2) || vendor.equals(3) || vendor.equals(0) || vendor.equals(10000)) {
//                        VendorDialpeer vendorEnt = vendorDialpeerRepository.getDpByTag(vendor);
//                        if (vendorEnt == null) {
//                            System.out.println("tag not found : " + vendor);
//                            continue;
//                        }
//                        System.out.println("vendor name : " + vendorEnt.getName());
//                        System.out.println("vendor tag : " + vendorEnt.getTag());
//                        System.out.println("vendor id : " + vendorEnt.getId());
//                        vendorId = vendorEnt.getId();
//                        System.out.println("vendor var id : " + vendorId);
//                    } else {
//                        SmppVendorAccount vendorEnt = smppVendorAccountService.getAccountGyTag(vendor);
//                        if (vendorEnt == null) {
//                            System.out.println("tag not found : " + vendor);
//                            continue;
//                        }
//                        System.out.println("vendor name : " + vendorEnt.getName());
//                        System.out.println("vendor tag : " + vendorEnt.getTag());
//                        System.out.println("vendor id : " + vendorEnt.getId());
//                        vendorId = vendorEnt.getId();
//                        System.out.println("vendor id : " + vendorEnt.getId());
//                    }
//                    VendorTypes vendorType = VendorTypes.SmppClientAccount;
//                    if (vendor.equals(110) || vendor.equals(111) || vendor.equals(1) || vendor.equals(2) || vendor.equals(3) || vendor.equals(0) || vendor.equals(10000)) {
//                        vendorType = VendorTypes.VendorDialpeer;
//                    }
//                    //////////////////////
//
//
//                    Long vendorFilterId = null;
//                    if (vendor_filter.equals(110) || vendor_filter.equals(111) || vendor_filter.equals(1) || vendor_filter.equals(2) || vendor_filter.equals(3) || vendor_filter.equals(0) || vendor_filter.equals(10000)) {
//                        VendorDialpeer vendorFilterEnt = vendorDialpeerRepository.getDpByTag(vendor_filter);
//                        if (vendorFilterEnt == null) {
//                            System.out.println("tag not found : " + vendor);
//                            continue;
//                        }
//                        System.out.println("vendor filter name : " + vendorFilterEnt.getName());
//                        System.out.println("vendor filter tag : " + vendorFilterEnt.getTag());
//                        System.out.println("vendor filter id : " + vendorFilterEnt.getId());
//                        vendorFilterId = vendorFilterEnt.getId();
//                        System.out.println("vendor var id : " + vendorFilterId);
//                    } else {
//                        SmppVendorAccount vendorFilterEnt = smppVendorAccountService.getAccountGyTag(vendor_filter);
//                        if (vendorFilterEnt == null) {
//                            System.out.println("tag not found : " + vendor_filter);
//                            continue;
//                        }
//                        System.out.println("vendor filter name : " + vendorFilterEnt.getName());
//                        System.out.println("vendor filter tag : " + vendorFilterEnt.getTag());
//                        System.out.println("vendor filter id : " + vendorFilterEnt.getId());
//                        vendorFilterId = vendorFilterEnt.getId();
//                        System.out.println("vendor var id : " + vendorFilterId);
//                    }
//
//                    VendorTypes vendorFilterType = VendorTypes.SmppClientAccount;
//                    if (vendor_filter.equals(110) || vendor_filter.equals(111) || vendor_filter.equals(1) || vendor_filter.equals(2) || vendor_filter.equals(3) || vendor_filter.equals(0) || vendor_filter.equals(10000)) {
//                        vendorFilterType = VendorTypes.VendorDialpeer;
//                    }
//
//
//                    //System.out.println("Vendor : " + vendor  + "    Vendor Filter : " + vendor_filter + " vendorAccountId : " +vendorId + "  Vendor type: " + vendorType);
//
//
//
//                    RoutingSmsRule newRule = new RoutingSmsRule();
//
//                    newRule.setId(id);
//                    newRule.setRuleOrder(rule_order);
//                    newRule.setLevel(SmsRoutingLevel.Primary);
//                    newRule.setAccount(currentAccountidList.get(0).longValue());
//                    newRule.setMcc(mcc);
//                    newRule.setMnc(mnc);
//                    newRule.setVendor(vendorId);
//                    newRule.setSourceFilter(source_filter);
//                    newRule.setMessageFilter(message_filter);
//                    newRule.setVendorFilter(vendorFilterId);
//                    newRule.setWeight(weight);
//                    newRule.setFiltered(filtered);
//                    newRule.setReplaceSource(replace_source);
//                    newRule.setRegisteredDelivery(replace_registered_delivery);
//                    newRule.setRegisteredDelivery(registered_delivery);
//                    newRule.setFilteredContent(filtered_content);
//                    newRule.setVendorType(vendorType);
//                    newRule.setVendorFilterType(vendorFilterType);
//                    newRule.setNewSourceAddr(newsourceaddr);
//
//
//                    routingSmsRuleRepository.save(newRule);
//
//                    //List<String> c = entityManager.createNativeQuery("SELECT id FROM routing_sms_rules").getResultList();
//                    //String currentRuleId = list.get(list.size() - 1);
//
//
////                    ResultSet psqlRs = psqlSTatment.executeQuery("SELECT id FROM routing_sms_rules");
////
////                    String newId = "";
////                    while (psqlRs.next()) {
////                        newId = psqlRs.getString("id");
////                    }
////
////                    System.out.println(newId);
////                    psqlRs.close();
////
////
////
////
////                    try {
////                        String query_text = "UPDATE " + rs.getString("table_name") + " SET id = '" + newId + "' WHERE id = '" + id + "'";
////                        System.out.println(query_text);
////                        updateSt.executeUpdate(query_text);
////                    } catch (SQLException e) {
////                        e.printStackTrace();
////                        skiped++;
////                        System.out.println("Skiping:    old id: " + id + "     New id : " + newId + "   " + mcc + " " + mnc);
////                    }
////
////
////                    System.out.println("Iterator : " + it + "       Skiped : " + skiped);
//
//                    //routingSmsRuleService.SaveRoutingRule(id, SmsRoutingLevel.Primary, currentAccountidList.get(0).longValue(), mcc, mnc, vendorId, source_filter, message_filter, vendorFilterId, rule_order,
//                    //      weight, filtered, replace_source, replace_registered_delivery, registered_delivery, filtered_content, vendorType, vendorFilterType);
//
//
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            oneTime = false;
//
//            System.out.println("Rules created!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
//
//
//
//
//        }
//        try {
//            wait(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        Boolean botEnabled = false;

        Settings settings = settingsService.getSettingByKey("global_bot_enabled");
        if (settings != null) {
            if (!settings.getValue().equals("")) {
                if (settings.getValue().equals("true")) {
                    botEnabled = true;
                }
            }
        }




        if (botEnabled) {
            //Billing
            checkLastMinuteBalance();


            //System
            checkProcessList();
            checkLastMinute();
        }

    }
    private void checkLastMinuteBalance() {
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.add(Calendar.MINUTE, -1);
        Date startDate = calendar.getTime();

        Query q = entityManager.createQuery("SELECT c.mcc, c.mnc, c.uid, SUM(c.client_price - c.vendor_price_unconfirmed) as balance FROM Mdr c WHERE c.created_at BETWEEN :startDate AND :endDate GROUP BY c.mcc, c.mnc, c.uid");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);

        List<Object[]> list = q.getResultList();

        for (Object[] Obj : list) {
            double balance = (double) Obj[3];


            if (balance < -0.1) {
                String uid = (String) Obj[2];

                List<Object[]> accounts = entityManager.createNativeQuery("SELECT customer_id as id, name as name from smpp_client_accounts as accounts WHERE id IN (SELECT smppclientaccount_id from smpp_client_system_id where uid = :uid)").setParameter("uid", uid).getResultList();
                if (accounts.size() > 0) {

                    Object[] account = accounts.get(0);
                    BigInteger id = (BigInteger) account[0];
                    Customer currentCustomer = customerService.getCustomerById(id.longValue());
                    String customerFullName = currentCustomer.getName() + " - " + (String) account[1];

                    String mcc = (String) Obj[0];
                    String mnc = (String) Obj[1];

                    String DirectionFullName = "";

                    Refbook countryRefbook = refbookService.getRootRefbookByMcc(mcc);
                    if (countryRefbook != null) {
                        if (mnc.equals("Flt")) {
                            DirectionFullName = countryRefbook.getCountry() + " (" + mcc + ")\n" + "FLAT";
                        } else {
                            List<Refbook> currentRefbookNetworkList = refbookService.getRefbookByMccAndMnc(mcc, mnc);
                            if (currentRefbookNetworkList.size() > 0) {
                                DirectionFullName = countryRefbook.getCountry() + " (" + mcc + ")\n" + currentRefbookNetworkList.get(0).getNetwork() + " (" + mnc + ")";
                            } else {
                                DirectionFullName = countryRefbook.getCountry() + " (" + mcc + ")\n" + "FLAT";
                            }
                        }
                    }

                    String uniqueness_key = NotificationIssues.NegativeBalance + "_" + uid + "_" + mcc;

                    List<Notifications> notificationsList = notificationsService.getProcessedNotificationsByKeyAndDate(uniqueness_key, endDate);
                    String messageText = "Negative balance last minute: \nClient : " + customerFullName + "\nDirection : \n" + DirectionFullName + " \nBalance : " + String.format("%.02f", balance);
                    if (notificationsList.size() == 0) {
                        sendMessageToSharedChat(messageText);

                        calendar = Calendar.getInstance();
                        Date sentDate = calendar.getTime();
                        calendar.add(Calendar.MINUTE, 30);
                        Date nextDate = calendar.getTime();

                        Notifications notification = new Notifications(sentDate, 0L, messageText, NotificationIssues.NegativeBalance, uniqueness_key);
                        notification.setNextNotification(nextDate);
                        notification.setProcessed(true);

                        notificationsService.save(notification);
                    }
                }

            }


        }
    }

    private void checkProcessList() {
        List<Softswitch> list = softswitchService.getSoftswitchesAll();

        for (Softswitch softswEnt : list) {
            Connection connection = null;
            ResultSet rs = null;
            Statement statement = null;

            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + softswEnt.getHost() + ":" + softswEnt.getMysqlPort() + "/" + softswEnt.getMysqlDatabase(), softswEnt.getMysqlUsername(), softswEnt.getMysqlPassword());
                statement = connection.createStatement();
                rs = statement.executeQuery("SHOW PROCESSLIST");

                Integer it = 0;
                while (rs.next()) {
                    it++;
                }

                if (it > 15) {
                    String uniqueness_key = NotificationIssues.ToManyConnections + "_" + softswEnt.getId();
                    List<Notifications> notificationsList = notificationsService.getProcessedNotificationsByKeyAndDate(uniqueness_key, new Date());
                    if (notificationsList.size() == 0) {
                        sendMessageToSystemChat("Softswitch : " + softswEnt.getName() + "\nConnections count : " + it);

                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MINUTE, 30);
                        Date nextDate = calendar.getTime();

                        Notifications notification = new Notifications(new Date(), 0L, "Softswitch : " + softswEnt.getName() + "\nConnections count : " + it, NotificationIssues.ToManyConnections, uniqueness_key);
                        notification.setNextNotification(nextDate);
                        notification.setProcessed(true);
                        notificationsService.save(notification);
                    }
                }

                if (rs != null) try {
                    rs.close();
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


            } catch (Exception e) {
                e.printStackTrace();

                if (rs != null) try {
                    rs.close();
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

    }

    private void checkLastMinute() {
        List<Object[]> activeSmsList = entityManager.createNativeQuery("SELECT uid, mcc, mnc, routed_cid, SUM(1) as count, SUM(client_price) as client_sum, SUM(vendor_price) as vendor_sum, SUM(client_price) - SUM(vendor_price) as profit from mdr WHERE created_at BETWEEN (current_timestamp at time zone 'UTC' - Interval '59 second') AND (current_timestamp at time zone 'UTC')  group by uid, mcc, mnc, routed_cid ORDER BY count DESC, profit").getResultList();
        if (activeSmsList.size() == 0) {
            String uniqueness_key = NotificationIssues.NoLastMinute.toString();
            List<Notifications> notificationsList = notificationsService.getProcessedNotificationsByKeyAndDate(uniqueness_key, new Date());

            if (notificationsList.size() == 0) {
                sendMessageToSystemChat("Billing : No last minute data");

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 30);
                Date nextDate = calendar.getTime();

                Notifications notification = new Notifications(new Date(), 0L, "Billing : No last minute data", NotificationIssues.NoLastMinute, uniqueness_key);
                notification.setNextNotification(nextDate);
                notification.setProcessed(true);
                notificationsService.save(notification);
            }

        }
    }

    private void sendMessageToSystemChat(String message) {

        String chatId = null;
        String botId = null;

        Settings settings = settingsService.getSettingByKey("global_bot_system_chat_id");
        if (settings != null) {
            if (!settings.getValue().equals("")) {
                chatId = settings.getValue();
            }
        }

        settings = settingsService.getSettingByKey("global_bot_api_key");
        if (settings != null) {
            if (!settings.getValue().equals("")) {
                botId = settings.getValue();
            }
        }


        if (chatId != null && botId != null) {

            String url_bot = "https://api.telegram.org/bot" + botId + "/sendMessage?chat_id=" + chatId + "&text=";
            try {
                String messageToSend = URLEncoder.encode(message, "UTF-8");

                url_bot = url_bot + messageToSend;

                URL obj = new URL(url_bot);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "\"Mozilla/5.0\"");

                con.getResponseCode();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessageToSharedChat(String message) {

        String chatId = null;
        String botId = null;

        Settings settings = settingsService.getSettingByKey("global_bot_alert_chat_id");
        if (settings != null) {
            if (!settings.getValue().equals("")) {
                chatId = settings.getValue();
            }
        }

        settings = settingsService.getSettingByKey("global_bot_api_key");
        if (settings != null) {
            if (!settings.getValue().equals("")) {
                botId = settings.getValue();
            }
        }


        if (chatId != null && botId != null) {
            String url_bot = "https://api.telegram.org/bot" + botId + "/sendMessage?chat_id=" + chatId + "&text=";
            try {
                String messageToSend = URLEncoder.encode(message, "UTF-8");

                url_bot = url_bot + messageToSend;

                URL obj = new URL(url_bot);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "\"Mozilla/5.0\"");

                con.getResponseCode();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessageToNocChat(String message) {

        String chatId = null;
        String botId = null;

        Settings settings = settingsService.getSettingByKey("global_noc_system_chat_id");
        if (settings != null) {
            if (!settings.getValue().equals("")) {
                chatId = settings.getValue();
            }
        }

        settings = settingsService.getSettingByKey("global_bot_api_key");
        if (settings != null) {
            if (!settings.getValue().equals("")) {
                botId = settings.getValue();
            }
        }


        if (chatId != null && botId != null) {
            String url_bot = "https://api.telegram.org/bot" + botId + "/sendMessage?chat_id=" + chatId + "&text=";
            try {
                String messageToSend = URLEncoder.encode(message, "UTF-8");

                url_bot = url_bot + messageToSend;

                URL obj = new URL(url_bot);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "\"Mozilla/5.0\"");

                con.getResponseCode();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
