package app.service.reportSmsStatistic;

import app.entity.*;
import app.entityXML.reportSmsStatistic.*;
import app.service.clientRatesCurrent.ClientRatesCurrentService;
import app.service.refbook.RefbookService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.smppVendorIps.SmppVendorIpsService;
import app.service.vendorRatesCurrent.VendorRatesCurrentService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by АРТЕМ on 14.07.2017.
 */
@Service
public class ReportSmsStatisticServiceImpl implements ReportSmsStatisticService{

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    @Autowired
    SmppClientAccountService smppClientAccountService;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    SmppVendorIpsService smppVendorIpsService;

    @Autowired
    RefbookService refbookService;

    @Autowired
    SmppVendorAccountService smppVendorAccountService;

    @Autowired
    ClientRatesCurrentService clientRatesCurrentService;

    @Autowired
    VendorRatesCurrentService vendorRatesCurrentService;


    @Override
    public ReportSmsStatistic getReportSmsStatistic(CustomUser user, Long id_customer, String mcc, String date_start, String date_end, String report_option) {


        ReportSmsStatistic reportSmsStatistic = new ReportSmsStatistic();
        Random random = new Random();

        Connection connection = null;
        ResultSet rs = null;
        Statement Statement = null;

        try {
            connection = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            Statement = connection.createStatement();
            rs = Statement.executeQuery(getStatisticsQuery(id_customer, mcc, date_start, date_end));

            HashSet<String> customerHashset = new HashSet<String>();
            HashSet<String> accountHashset = new HashSet<String>();
            HashSet<String> countryHashset = new HashSet<String>();
            HashSet<String> networkHashset = new HashSet<String>();

            String customer_id = "";
            String vendor_id = "";
            Long account_id = -1L;
            String country_id = "";
            String network_id = "";

            String last_customer_id = "";
            String last_vendor_id = "";
            Long last_account_id = -1L;
            String last_country_id = "";
            String last_network_id = "";

            CustomerRssXml customerRssXml = null;
            AccountRssXml accountRssXml = null;
            CountryRssXml countryRssXml = null;
            NetworkRssXml networkRssXml = null;
            VendorRssXml vendorRssXml = null;


            while (rs.next()) {
                float dlr = rs.getFloat("Delivered") * 100 / rs.getFloat("AttemptsTotal");
                Integer adt = 0;
                if (rs.getLong("Delivered")!=0L) {
                    adt = Math.round(rs.getLong("DeliveryTime") / rs.getLong("Delivered"));
                }


                customer_id = "nullclient";
                vendor_id = "nullvendor";
                account_id = rs.getLong("smppclientaccount_id");
                country_id = rs.getString("mcc");
                network_id = rs.getString("mnc");

                String currentCustomerName = "UNKNOWN";
                String currentVendorName = "UNKNOWN (" + rs.getString("routed_cid") + ")";
                String currentAccountName = "UNKNOWN (" + rs.getString("uid") + ")";
                String currentCountryName = "UNKNOWN (" + rs.getString("mcc") + ")";
                String currentNetworkName = "UNKNOWN (" + rs.getString("mnc") + ")";

                SmppClientAccount currentClientAccount = smppClientAccountService.getSmppClientAccountById(rs.getLong("smppclientaccount_id"));
                if (currentClientAccount!=null) {
                    currentCustomerName = currentClientAccount.getCustomer().getName();
                    currentAccountName = currentClientAccount.getName();
                    customer_id = Long.toString(currentClientAccount.getCustomer().getId());
                }

                SmppVendorAccount currentVendorAccount = smppVendorAccountService.getSmppVendorAccountById(rs.getLong("smppvendoraccount_id"));
                if (currentVendorAccount!=null) {
                    currentVendorName = currentVendorAccount.getCustomer().getName() + " - " + currentVendorAccount.getName();
                    vendor_id = Long.toString(currentVendorAccount.getId());
                }

                if (network_id.equals("Flt")) {
                    Refbook currentRefbook = refbookService.getRootRefbookByMcc(country_id);
                    if (currentRefbook!=null) {
                        currentCountryName = currentRefbook.getCountry();
                        currentNetworkName = "FLAT";
                    }
                    network_id = network_id + rs.getString("mcc");
                } else {
                    Refbook currentRefbookCountry = refbookService.getRootRefbookByMcc(country_id);
                    if (currentRefbookCountry!=null) {
                        currentCountryName = currentRefbookCountry.getCountry();
                    }
                    List<Refbook> currentRefbookNetworkList = refbookService.getRefbookByMccAndMnc(country_id, network_id);
                    if (currentRefbookNetworkList.size()!=0) {
                        Refbook currentRefbookNetwork = currentRefbookNetworkList.get(0);
                        if (currentRefbookNetwork!=null) {
                            currentNetworkName = currentRefbookNetwork.getNetwork();
                        } else {
                            currentRefbookNetwork = refbookService.getRootRefbookByMcc(country_id);
                            if (currentRefbookNetwork!=null) {
                                currentCountryName = currentRefbookNetwork.getCountry();
                            }
                        }
                    }

                }

                Double clientPrice = 0.0;
                if (currentClientAccount != null) {
                    ClientRatesCurrent currentClientPrice = clientRatesCurrentService.getRate(currentClientAccount, country_id, network_id);
                    if (currentClientPrice == null) {
                        currentClientPrice = clientRatesCurrentService.getRate(currentClientAccount, country_id, "");
                        if (currentClientPrice != null) {
                            clientPrice = currentClientPrice.getRate().doubleValue();
                        }
                    } else {
                        clientPrice = currentClientPrice.getRate().doubleValue();
                    }
                }


                double vendorPrice = 0.0;
                if (currentVendorAccount != null) {
                    List<SmppVendorIps> ipsList = currentVendorAccount.getSmppVendorIpsList();
                    if (ipsList.size() > 0) {
                        for (SmppVendorIps currentIps : ipsList) {
                            VendorRatesCurrent currentRate = vendorRatesCurrentService.getRate(currentIps, country_id, network_id);
                            if (currentRate != null) {
                                if (vendorPrice < currentRate.getRate()) {
                                    vendorPrice = currentRate.getRate();
                                }
                            }
                        }

                        if (vendorPrice == 0.00) {
                            for (SmppVendorIps currentIps : ipsList) {
                                VendorRatesCurrent currentRate = vendorRatesCurrentService.getRate(currentIps, country_id, "");
                                if (currentRate != null) {
                                    if (vendorPrice < currentRate.getRate()) {
                                        vendorPrice = currentRate.getRate();
                                    }
                                }
                            }
                        }

                    }
                }




                if (!customer_id.equals(last_customer_id)) {

                    customerRssXml = new CustomerRssXml(customer_id,
                            currentCustomerName,
                            rs.getLong("AttemptsTotal"),
                            rs.getLong("AttemptsSuccess"),
                            0.0,
                            0.0,
                            dlr,
                            adt,
                            round(rs.getDouble("IncommingSum")),
                            round(rs.getDouble("OutgoingSum")),
                            round(rs.getDouble("IncommingSum") - rs.getDouble("OutgoingSum")),
                            rs.getLong("Delivered"),
                            rs.getLong("Undelivered"),
                            rs.getLong("Rejected"),
                            rs.getLong("Other"),
                            rs.getLong("DeliveryTime"));
                    reportSmsStatistic.addCustomerRssXml(customerRssXml);
                    //System.out.println("--" + customerRssXml.getId());
                } else {
                    customerRssXml.setAttempts_count(customerRssXml.getAttempts_count() + rs.getLong("AttemptsTotal"));
                    customerRssXml.setAttempts_success(customerRssXml.getAttempts_success() + rs.getLong("AttemptsSuccess"));
                    customerRssXml.setDelivered(customerRssXml.getDelivered() + rs.getLong("Delivered"));
                    customerRssXml.setUndelivered(customerRssXml.getUndelivered() + rs.getLong("Undelivered"));
                    customerRssXml.setRejected(customerRssXml.getRejected() + rs.getLong("Rejected"));
                    customerRssXml.setOther(customerRssXml.getOther() + rs.getLong("Other"));
                    customerRssXml.setIncoming_sum(customerRssXml.getIncoming_sum() + rs.getLong("IncommingSum"));
                    customerRssXml.setOutgoing_sum(customerRssXml.getOutgoing_sum() + rs.getLong("OutgoingSum"));
                    customerRssXml.setProfit(customerRssXml.getIncoming_sum() - customerRssXml.getOutgoing_sum());
                    customerRssXml.setDelivery_time(customerRssXml.getDelivery_time() + rs.getLong("DeliveryTime"));
                    float dlr_totals = customerRssXml.getDelivered() * 100 / customerRssXml.getAttempts_count();
                    customerRssXml.setDlr(dlr_totals);
                    Integer adt_totals = 0;
                    if (customerRssXml.getDelivered()!=0) {
                        adt_totals = Math.round(customerRssXml.getDelivery_time() / customerRssXml.getDelivered());
                    }
                    customerRssXml.setAdt(adt_totals);
                }
                last_customer_id = customer_id;
                if (!account_id.equals(last_account_id)) {
                    accountRssXml = new AccountRssXml(customer_id + "_" + account_id,
                            currentAccountName,
                            rs.getLong("AttemptsTotal"),
                            rs.getLong("AttemptsSuccess"),
                            0.0,
                            0.0,
                            dlr,
                            adt,
                            round(rs.getDouble("IncommingSum")),
                            round(rs.getDouble("OutgoingSum")),
                            round(rs.getDouble("IncommingSum") - rs.getDouble("OutgoingSum")),
                            rs.getLong("Delivered"),
                            rs.getLong("Undelivered"),
                            rs.getLong("Rejected"),
                            rs.getLong("Other"),
                            rs.getLong("DeliveryTime"));
                    customerRssXml.addAccountRssXml(accountRssXml);
                    //System.out.println("----" + accountRssXml.getId());
                } else {
                    accountRssXml.setAttempts_count(accountRssXml.getAttempts_count() + rs.getLong("AttemptsTotal"));
                    accountRssXml.setAttempts_success(accountRssXml.getAttempts_success() + rs.getLong("AttemptsSuccess"));
                    accountRssXml.setDelivered(accountRssXml.getDelivered() + rs.getLong("Delivered"));
                    accountRssXml.setUndelivered(accountRssXml.getUndelivered() + rs.getLong("Undelivered"));
                    accountRssXml.setRejected(accountRssXml.getRejected() + rs.getLong("Rejected"));
                    accountRssXml.setOther(accountRssXml.getOther() + rs.getLong("Other"));
                    accountRssXml.setIncoming_sum(accountRssXml.getIncoming_sum() + rs.getLong("IncommingSum"));
                    accountRssXml.setOutgoing_sum(accountRssXml.getOutgoing_sum() + rs.getLong("OutgoingSum"));
                    accountRssXml.setProfit(accountRssXml.getIncoming_sum() - accountRssXml.getOutgoing_sum());
                    accountRssXml.setDelivery_time(accountRssXml.getDelivery_time() + rs.getLong("DeliveryTime"));
                    float dlr_totals = accountRssXml.getDelivered() * 100 / accountRssXml.getAttempts_count();
                    accountRssXml.setDlr(dlr_totals);
                    Integer adt_totals = 0;
                    if (accountRssXml.getDelivered()!=0) {
                        adt_totals = Math.round(accountRssXml.getDelivery_time() / accountRssXml.getDelivered());
                    }
                    accountRssXml.setAdt(adt_totals);
                }
                last_account_id = account_id;

                if (!(account_id + "_" + country_id).equals(last_country_id)) {
                    countryRssXml = new CountryRssXml(customer_id + "_" + account_id + "_" + country_id,
                            currentCountryName,
                            rs.getLong("AttemptsTotal"),
                            rs.getLong("AttemptsSuccess"),
                            0.0,
                            0.0,
                            dlr,
                            adt,
                            round(rs.getDouble("IncommingSum")),
                            round(rs.getDouble("OutgoingSum")),
                            round(rs.getDouble("IncommingSum") - rs.getDouble("OutgoingSum")),
                            rs.getLong("Delivered"),
                            rs.getLong("Undelivered"),
                            rs.getLong("Rejected"),
                            rs.getLong("Other"),
                            rs.getLong("DeliveryTime"));
                    accountRssXml.addCountrieRssXml(countryRssXml);
                    //System.out.println("------" + countryRssXml.getId());
                } else {
                    countryRssXml.setAttempts_count(countryRssXml.getAttempts_count() + rs.getLong("AttemptsTotal"));
                    countryRssXml.setAttempts_success(countryRssXml.getAttempts_success() + rs.getLong("AttemptsSuccess"));
                    countryRssXml.setDelivered(countryRssXml.getDelivered() + rs.getLong("Delivered"));
                    countryRssXml.setUndelivered(countryRssXml.getUndelivered() + rs.getLong("Undelivered"));
                    countryRssXml.setRejected(countryRssXml.getRejected() + rs.getLong("Rejected"));
                    countryRssXml.setOther(countryRssXml.getOther() + rs.getLong("Other"));
                    countryRssXml.setIncoming_sum(countryRssXml.getIncoming_sum() + rs.getLong("IncommingSum"));
                    countryRssXml.setOutgoing_sum(countryRssXml.getOutgoing_sum() + rs.getLong("OutgoingSum"));
                    countryRssXml.setProfit(countryRssXml.getIncoming_sum() - countryRssXml.getOutgoing_sum());
                    countryRssXml.setDelivery_time(countryRssXml.getDelivery_time() + rs.getLong("DeliveryTime"));
                    float dlr_totals = countryRssXml.getDelivered() * 100 / countryRssXml.getAttempts_count();
                    countryRssXml.setDlr(dlr_totals);
                    Integer adt_totals = 0;
                    if (countryRssXml.getDelivered()!=0) {
                        adt_totals = Math.round(countryRssXml.getDelivery_time() / countryRssXml.getDelivered());
                    }
                    countryRssXml.setAdt(adt_totals);
                }
                last_country_id = account_id + "_" + country_id;

                if (!(account_id + "_" + country_id + "_" + network_id).equals(last_network_id)) {
                    networkRssXml = new NetworkRssXml(customer_id + "_" + account_id + "_" + country_id + "_" + network_id,
                            currentNetworkName,
                            rs.getLong("AttemptsTotal"),
                            rs.getLong("AttemptsSuccess"),
                            clientPrice,
                            0.0,
                            dlr,
                            adt,
                            round(rs.getDouble("IncommingSum")),
                            round(rs.getDouble("OutgoingSum")),
                            round(rs.getDouble("IncommingSum") - rs.getDouble("OutgoingSum")),
                            rs.getLong("Delivered"),
                            rs.getLong("Undelivered"),
                            rs.getLong("Rejected"),
                            rs.getLong("Other"),
                            rs.getLong("DeliveryTime"));
                    countryRssXml.addNetworkRssXml(networkRssXml);
                } else {
                    networkRssXml.setAttempts_count(networkRssXml.getAttempts_count() + rs.getLong("AttemptsTotal"));
                    networkRssXml.setAttempts_success(networkRssXml.getAttempts_success() + rs.getLong("AttemptsSuccess"));
                    networkRssXml.setDelivered(networkRssXml.getDelivered() + rs.getLong("Delivered"));
                    networkRssXml.setUndelivered(networkRssXml.getUndelivered() + rs.getLong("Undelivered"));
                    networkRssXml.setRejected(networkRssXml.getRejected() + rs.getLong("Rejected"));
                    networkRssXml.setOther(networkRssXml.getOther() + rs.getLong("Other"));
                    networkRssXml.setIncoming_sum(networkRssXml.getIncoming_sum() + rs.getLong("IncommingSum"));
                    networkRssXml.setOutgoing_sum(networkRssXml.getOutgoing_sum() + rs.getLong("OutgoingSum"));
                    networkRssXml.setProfit(networkRssXml.getIncoming_sum() - networkRssXml.getOutgoing_sum());
                    networkRssXml.setDelivery_time(networkRssXml.getDelivery_time() + rs.getLong("DeliveryTime"));
                    float dlr_totals = networkRssXml.getDelivered() * 100 / networkRssXml.getAttempts_count();
                    networkRssXml.setDlr(dlr_totals);
                    Integer adt_totals = 0;
                    if (networkRssXml.getDelivered()!=0) {
                        adt_totals = Math.round(networkRssXml.getDelivery_time() / networkRssXml.getDelivered());
                    }
                    networkRssXml.setAdt(adt_totals);
                }
                last_network_id = account_id + "_" + country_id + "_" + network_id;

                vendorRssXml = new VendorRssXml(customer_id + "_" + account_id + "_" + country_id + "_" + network_id + "_" + vendor_id,
                        currentVendorName,
                        rs.getLong("AttemptsTotal"),
                        rs.getLong("AttemptsSuccess"),
                        0.0,
                        vendorPrice,
                        dlr,
                        adt,
                        round(rs.getDouble("IncommingSum")),
                        round(rs.getDouble("OutgoingSum")),
                        round(rs.getDouble("IncommingSum") - rs.getDouble("OutgoingSum")),
                        rs.getLong("Delivered"),
                        rs.getLong("Undelivered"),
                        rs.getLong("Rejected"),
                        rs.getLong("Other"),
                        rs.getLong("DeliveryTime"));
                networkRssXml.addVendorRssXml(vendorRssXml);

            }


            rs = Statement.executeQuery(getLastMinuteQuery());
            List<LastMinuteRowXML> currentLastMinuteList = new ArrayList<>();
            while (rs.next()) {
                SmppClientSystemId currentSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(rs.getString("uid"));
                String customerName = rs.getString("uid");
                if (currentSystemId!=null) {
                    customerName = currentSystemId.getSmppClientAccount().getCustomer().getName() + " - " + currentSystemId.getSmppClientAccount().getName();
                }

                SmppVendorIps currentVendorIps = smppVendorIpsService.getSmppVendorIpsByCid(rs.getString("routed_cid"));
                String vendorName = rs.getString("routed_cid");
                if (currentVendorIps!=null) {
                    vendorName = currentVendorIps.getSmppVendorAccount().getCustomer().getName() + " - " + currentVendorIps.getSmppVendorAccount().getName();
                }

                String countryName = "";
                String networkName = "";

                if (rs.getString("mnc").equals("Flt")) {
                    Refbook currentRefbook = refbookService.getRootRefbookByMcc(rs.getString("mcc"));
                    if (currentRefbook!=null) {
                        countryName = currentRefbook.getCountry();
                        networkName = "FLAT";
                    }
                } else {
                    Refbook currentRefbookCountry = refbookService.getRootRefbookByMcc(rs.getString("mcc"));
                    if (currentRefbookCountry!=null) {
                        countryName = currentRefbookCountry.getCountry();
                    }
                    List<Refbook> currentRefbookNetworkList = refbookService.getRefbookByMccAndMnc(rs.getString("mcc"), rs.getString("mnc"));
                    if (currentRefbookNetworkList.size()!= 0) {
                        Refbook currentRefbookNetwork = currentRefbookNetworkList.get(0);
                        if (currentRefbookNetwork!=null) {
                            networkName = currentRefbookNetwork.getNetwork();
                        } else {
                            currentRefbookNetwork = refbookService.getRootRefbookByMcc(rs.getString("mcc"));
                            if (currentRefbookNetwork!=null) {
                                countryName = currentRefbookNetwork.getCountry();
                            }
                        }
                    }
                }


                currentLastMinuteList.add(new LastMinuteRowXML(customerName, countryName + " - " + networkName, vendorName, rs.getFloat("client_sum"),
                        rs.getFloat("vendor_sum"), rs.getFloat("profit"), rs.getLong("count")));
            }

            reportSmsStatistic.setLastMinuteRowXml(currentLastMinuteList);

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

        return reportSmsStatistic;
    }

    private void setProperties(Object row) {

    }


    private String getLastMinuteQuery() {
        //where created_at >= (current_timestamp at time zone 'UTC') - interval '1 minutes'
        return "SELECT uid, mcc, mnc, routed_cid, SUM(1) as count, SUM(client_price) as client_sum, SUM(vendor_price_unconfirmed) as vendor_sum, SUM(client_price) - SUM(vendor_price_unconfirmed) as profit from mdr WHERE created_at BETWEEN (current_timestamp at time zone 'UTC' - Interval '59 second') AND (current_timestamp at time zone 'UTC')  group by uid, mcc, mnc, routed_cid ORDER BY count DESC, profit";
    }


    private String getStatisticsQuery(Long customer, String mcc, String start_date, String end_date) {
        String returnString = "";

        String queryPart1 = "SELECT\n" +
                "\taccount_ids.customer_id,\n" +
                "\tvendor_account_ids.customer_id,\n" +
                "\tsystem_ids.smppclientaccount_id,\n" +
                "\tvendor_ips.smppvendoraccount_id,\n" +
                "\tstring_agg(distinct  Report.uid, ',') as uid,\n" +
                "\tstring_agg(distinct  Report.routed_cid, ',') as routed_cid,\n" +
                "\tReport.mcc,\n" +
                "\tReport.mnc,\n" +
                "    SUM(Report.DeliveryTime) as DeliveryTime,\n" +
                "\tSUM(Report.IncommingSum) AS IncommingSum,\n" +
                "\tSUM(Report.OutgoingSum) AS OutgoingSum,\n" +
                "\tSUM(Report.AttemptsTotal) AS AttemptsTotal,\n" +
                "\tSUM(Report.AttemptSuccess) AS AttemptsSuccess,\n" +
                "\tSUM(Report.Delivered) AS Delivered,\n" +
                "    SUM(Report.Undelivered) AS Undelivered,\n" +
                "    SUM(Report.Rejected) AS Rejected,\n" +
                "    SUM(Report.Other) AS Other\n" +
                "FROM (\n" +
                "\tSELECT\n" +
                "\t\tTotals.created_at,\n" +
                "\t\tTotals.uid,\n" +
                "\t\tTotals.routed_cid,\n" +
                "\t\tTotals.mcc,\n" +
                "\t\tTotals.mnc,\n" +
                "\t\tTotals.IncommingSum,\n" +
                "\t\tTotals.OutgoingSum,\n" +
                "\t\tTotals.AttemptsTotal,\n" +
                "\t\tTotals.AttemptSuccess,\n" +
                "\t\tTotals.RegiteredDelivery,\n" +
                "    \tStats.DeliveryTime,\n" +
                "\t\tStats.Delivered,\n" +
                "\t\tStats.Undelivered,\n" +
                "\t\tStats.Rejected,\n" +
                "\t\tStats.Other\n" +
                "\tFROM\n" +
                "\t\t(SELECT \n" +
                "\t\t\ttotals.created_at,\n" +
                "\t\t\ttotals.uid,\n" +
                "\t\t\ttotals.routed_cid,\n" +
                "\t\t\ttotals.mcc,\n" +
                "\t\t\ttotals.mnc,\n" +
                "\t\t\tSUM(totals.client_price) as IncommingSum,\n" +
                "\t\t\tSUM(totals.vendor_price) as OutgoingSum,\n" +
                "\t\t\tSUM(totals.attempts_count) as AttemptsTotal,\n" +
                "\t\t\tSUM(totals.attempts_success) as AttemptSuccess,\n" +
                "\t\t\tSUM(totals.registered_delivery) as RegiteredDelivery\n" +
                "\t\tFROM \n" +
                "\t\t\ttotals_sms as totals \n" + "         WHERE totals.created_at between '[StartDate]' AND '[EndDate]'\n" +
                "\t\tGROUP BY created_at, uid, routed_cid, mcc, mnc\n" +
                "\t\t) AS Totals\n" +
                "\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tcreated_at,\n" +
                "\t\t\t\tuid,\n" +
                "\t\t\t\trouted_cid,\n" +
                "\t\t\t\tmcc,\n" +
                "\t\t\t\tmnc,\n" +
                "        \t\tSUM(delivery_time) as DeliveryTime,\n" +
                "\t\t\t\tSUM(CASE WHEN status = 'DELIVRD' THEN count ELSE 0 END) as Delivered,\n" +
                "\t\t\t\tSUM(CASE WHEN status = 'UNDELIV' THEN count ELSE 0 END) as Undelivered,\n" +
                "\t\t\t\tSUM(CASE WHEN status = 'REJECTD' THEN count ELSE 0 END) as Rejected,\n" +
                "\t\t\t\tSUM(CASE WHEN status <> 'DELIVRD' and status <> 'UNDELIV' and status <> 'REJECTD' THEN count ELSE 0 END) as Other\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tstats_sms\n" + "         WHERE created_at between '[StartDate]' AND '[EndDate]' \n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tcreated_at,\n" +
                "\t\t\t\tuid,\n" +
                "\t\t\t\trouted_cid,\n" +
                "\t\t\t\tmcc,\n" +
                "\t\t\t\tmnc\n" +
                "\t\t) AS Stats\n" +
                "\tON\n" +
                "\t\tTotals.created_at = Stats.created_at AND\n" +
                "\t\tTotals.uid = Stats.uid AND\n" +
                "\t\tTotals.routed_cid = Stats.routed_cid AND\n" +
                "\t\tTotals.mcc = Stats.mcc AND\n" +
                "\t\tTotals.mnc = Stats.mnc\n" +
                ") AS Report\n" +
                "LEFT JOIN smpp_client_system_id AS system_ids ON system_ids.uid = Report.uid\n" +
                "LEFT JOIN smpp_client_accounts AS account_ids ON account_ids.id = system_ids.smppclientaccount_id\n" +
                "LEFT JOIN smpp_vendor_ips AS vendor_ips ON vendor_ips.cid = Report.routed_cid\n" +
                "LEFT JOIN smpp_vendor_accounts AS vendor_account_ids ON vendor_account_ids.id = vendor_ips.smppvendoraccount_id\n" +
                "\n";


        String queryPart2 = "GROUP BY\n" +
                "\taccount_ids.customer_id,\n" +
                "\tsystem_ids.smppclientaccount_id,\n" +
                "\tvendor_ips.smppvendoraccount_id,\n" +
                "\tReport.mcc,\n" +
                "\tReport.mnc,\t\n" +
                "\tvendor_account_ids.customer_id\n" +
                "ORDER BY\n" +
                "\taccount_ids.customer_id,\n" +
                "\tsystem_ids.smppclientaccount_id,\n" +
                "\tReport.mcc,\n" +
                "\tReport.mnc";

        returnString = queryPart1.replace("[StartDate]", start_date.replace("/","-")).replace("[EndDate]", end_date.replace("/", "-"));

        String otherConditions = "";

        if (!customer.equals(0L) || !mcc.equals("0")) {
            otherConditions = " WHERE ";
            if (!customer.equals(0L)) {
                otherConditions = otherConditions + " account_ids.customer_id = " + customer + " ";
            }


            if (!mcc.equals("0")) {
                if (!customer.equals(0L)) {
                    otherConditions = otherConditions + " AND Report.mcc = '" + mcc + "' ";
                } else {
                    otherConditions = otherConditions + " Report.mcc = '" + mcc + "' ";
                }
            }
        }

        returnString = returnString + otherConditions + queryPart2;

        return returnString;
    }

    private static double round(double value) {
        Integer places = 2;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}