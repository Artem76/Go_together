package app.service.tasks;

import app.entity.*;
import app.entity.enums.DebitCredit;
import app.entity.enums.FinanceEvents;
import app.service.emailAttachment.EmailAttachmentService;
import app.service.logger.LoggerService;
import app.service.incomingInvoice.IncomingInvoiceService;
import app.service.incomingInvoice.IncomingInvoiceTrafficDetailsService;
import app.service.soa.SoaService;
import app.service.customer.CustomerService;
import app.service.outgoingInvoice.OutgoingInvoiceService;
import app.service.outgoingInvoice.OutgoingInvoiceTrafficDetailsService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorIps.SmppVendorIpsService;
import app.service.smsBillingTerms.SmsBillingTermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Олег on 02.11.2017.
 */

@Service
public class CachesUpdateTask {

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SmsBillingTermsService smsBillingTermsService;

    @Autowired
    private SoaService soaService;

    @Autowired
    private SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    private SmppVendorIpsService smppVendorIpsService;

    @Autowired
    private OutgoingInvoiceService outgoingInvoiceService;

    @Autowired
    private IncomingInvoiceService incomingInvoiceService;

    @Autowired
    private OutgoingInvoiceTrafficDetailsService outgoingInvoiceTrafficDetailsService;

    @Autowired
    private IncomingInvoiceTrafficDetailsService incomingInvoiceTrafficDetailsService;

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private EmailAttachmentService emailAttachmentService;

    private static String day;

    public void runParallelStream(){

        loggerService.writeInfo("Caches update task started.");
        //SOA
        List<Customer> customersList = customerService.getCustomerAll();
        for (Customer currentCustomer : customersList) {
            if (currentCustomer.getSmsBillingTerms()==null) {
                continue;
            }

            List<String> customersUidList = getUidsListByCustomerId(currentCustomer.getId());
            List<String> customersCidList = getCidListByCustomerId(currentCustomer.getId());

            SmsBillingTerms customerBillingTerm = currentCustomer.getSmsBillingTerms();
            //customerBillingTerm.getBillingDays();

            updateClientCachesByDate(customerBillingTerm.getCurrentBillingTermsStartDate(),
                    customerBillingTerm.getCurrentBillingTermsEndDate(), customerBillingTerm.getCurrentBillingTermsPayDate(), customersUidList, currentCustomer.getId(), customerBillingTerm.getId());

            updateVendorCachesByDate(customerBillingTerm.getCurrentBillingTermsStartDate(),
                    customerBillingTerm.getCurrentBillingTermsEndDate(), customerBillingTerm.getCurrentBillingTermsPayDate(), customersCidList, currentCustomer.getId(), customerBillingTerm.getId());


            createInvoices(currentCustomer.getId(), customersUidList, customersCidList);
        }
        loggerService.writeInfo("Caches update task finished.");




    }

    private void updateVendorCachesByDate(Date startDate, Date endDate, Date payDate, List<String> cidList, Long customerId, Long billingTermsId) {
        if (cidList.size() == 0) {
            return;
        }

        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringStartDate = sqlFormat.format(startDate);
        String stringendDate = sqlFormat.format(endDate);

        String query = "SELECT SUM(attempts_success) AS attempts, SUM(vendor_price) AS sum FROM totals_sms WHERE created_at BETWEEN '" + stringStartDate + "' AND '" + stringendDate + "' AND routed_cid IN ";

        Soa currentSoa = soaService.getSoaByPeriod(startDate, endDate, customerId, billingTermsId, DebitCredit.Credit, FinanceEvents.Current_Traffic);
        if (currentSoa == null) {
            currentSoa = new Soa(customerId, billingTermsId, startDate, endDate, payDate, 0.0,0L, FinanceEvents.Current_Traffic, DebitCredit.Credit);
            closePreviousBillingPreiod(customerId, startDate, DebitCredit.Credit, billingTermsId, FinanceEvents.Current_Traffic);
        } else {
            if (currentSoa.getEvent() != FinanceEvents.Current_Traffic || currentSoa.getClosed()) {
                return;
            }
        }


        Connection connectionLocal = null;

        Statement statementLocal = null;

        ResultSet rsLocal = null;

        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statementLocal = connectionLocal.createStatement();

            String cids = getSqlParametersStringByList(cidList);
            query = query + "(" + cids + ")";

            rsLocal = statementLocal.executeQuery(query);

            if (rsLocal.next()) {
                currentSoa.setCount(rsLocal.getLong("attempts"));
                currentSoa.setSum(rsLocal.getDouble("sum"));
                currentSoa.setFinalSum(rsLocal.getDouble("sum"));
            }

            soaService.save(currentSoa);

            if (rsLocal != null) try {
                rsLocal.close();
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

        } catch (SQLException e) {
            e.printStackTrace();
            if (rsLocal != null) try {
                rsLocal.close();
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
        if (rsLocal != null) try {
            rsLocal.close();
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

    private void updateClientCachesByDate(Date startDate, Date endDate, Date payDate, List<String> uidList, Long customerId, Long billingTermsId) {
        if (uidList.size() == 0) {
            return;
        }

        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringStartDate = sqlFormat.format(startDate);
        String stringendDate = sqlFormat.format(endDate);

        String query = "SELECT SUM(attempts_count) AS attempts, SUM(client_price) AS sum FROM totals_sms WHERE created_at BETWEEN '" + stringStartDate + "' AND '" + stringendDate + "' AND uid IN ";

        Soa currentSoa = soaService.getSoaByPeriod(startDate, endDate, customerId, billingTermsId, DebitCredit.Debit, FinanceEvents.Current_Traffic);
        if (currentSoa == null) {
            currentSoa = new Soa(customerId, billingTermsId, startDate, endDate, payDate, 0.0,0L, FinanceEvents.Current_Traffic, DebitCredit.Debit);
            closePreviousBillingPreiod(customerId, startDate, DebitCredit.Debit, billingTermsId, FinanceEvents.Current_Traffic);
        } else {
            if (currentSoa.getEvent() != FinanceEvents.Current_Traffic || currentSoa.getClosed()) {
                return;
            }
        }

        Connection connectionLocal = null;

        Statement statementLocal = null;

        ResultSet rsLocal = null;

        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statementLocal = connectionLocal.createStatement();

            String uids = getSqlParametersStringByList(uidList);

            query = query + "(" + uids + ")";
            rsLocal = statementLocal.executeQuery(query);

            if (rsLocal.next()) {
                currentSoa.setCount(rsLocal.getLong("attempts"));
                currentSoa.setSum(rsLocal.getDouble("sum"));
                currentSoa.setFinalSum(rsLocal.getDouble("sum"));
            }

            soaService.save(currentSoa);

            if (rsLocal != null) try {
                rsLocal.close();
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

        } catch (SQLException e) {
            e.printStackTrace();
            if (rsLocal != null) try {
                rsLocal.close();
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
        if (rsLocal != null) try {
            rsLocal.close();
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

    private String getSqlParametersStringByList(List<String> list) {
        String cids = "";
        Integer i = 1;
        for (String cid : list) {
            if (i != list.size()) {
                cids = cids + "'" + cid + "', ";
            } else {
                cids = cids + "'" + cid + "'";
            }

            i++;
        }

        return cids;
    }

    public List<String> getUidsListByCustomerId(Long id) {
        List<String> returnList = new ArrayList<>();

        Connection connectionLocal = null;
        PreparedStatement pstmt = null;
        ResultSet rsLocal = null;

        String query = "SELECT customers.id, clientaccounts.id, clientuids.uid AS uid  FROM customers AS customers \n" +
                "LEFT JOIN smpp_client_accounts AS clientaccounts ON clientaccounts.customer_id = customers.id\n" +
                "LEFT JOIN smpp_client_system_id AS clientuids ON clientaccounts.id = clientuids.smppclientaccount_id\n" +
                "WHERE customers.id = ?";

        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            pstmt = connectionLocal.prepareStatement(query);
            pstmt.setLong(1, id);

            rsLocal = pstmt.executeQuery();
            while (rsLocal.next()) {
                returnList.add(rsLocal.getString("uid"));
            }
            if (rsLocal != null) try {
                rsLocal.close();
            } catch (SQLException logOrIgnore) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException logOrIgnore) {
            }
            if (connectionLocal != null) try {
                connectionLocal.close();
            } catch (SQLException logOrIgnore) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (rsLocal != null) try {
                rsLocal.close();
            } catch (SQLException logOrIgnore) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException logOrIgnore) {
            }
            if (connectionLocal != null) try {
                connectionLocal.close();
            } catch (SQLException logOrIgnore) {
            }
        }
        if (rsLocal != null) try {
            rsLocal.close();
        } catch (SQLException logOrIgnore) {
        }
        if (pstmt != null) try {
            pstmt.close();
        } catch (SQLException logOrIgnore) {
        }
        if (connectionLocal != null) try {
            connectionLocal.close();
        } catch (SQLException logOrIgnore) {
        }

        return returnList;
    }

    public List<String> getCidListByCustomerId(Long id) {
        List<String> returnList = new ArrayList<>();

        Connection connectionLocal = null;
        PreparedStatement pstmt = null;
        ResultSet rsLocal = null;

        String query = "select customers.id, vendoraccounts.id, vendorips.cid  from customers as customers \n" +
                "left join smpp_vendor_accounts as vendoraccounts on vendoraccounts.customer_id = customers.id\n" +
                "left join smpp_vendor_ips as vendorips on vendoraccounts.id = vendorips.smppvendoraccount_id\n" +
                "WHERE customers.id = ?";

        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            pstmt = connectionLocal.prepareStatement(query);
            pstmt.setLong(1, id);

            rsLocal = pstmt.executeQuery();
            while (rsLocal.next()) {
                returnList.add(rsLocal.getString("cid"));
            }
            if (rsLocal != null) try {
                rsLocal.close();
            } catch (SQLException logOrIgnore) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException logOrIgnore) {
            }
            if (connectionLocal != null) try {
                connectionLocal.close();
            } catch (SQLException logOrIgnore) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (rsLocal != null) try {
                rsLocal.close();
            } catch (SQLException logOrIgnore) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException logOrIgnore) {
            }
            if (connectionLocal != null) try {
                connectionLocal.close();
            } catch (SQLException logOrIgnore) {
            }
        }
        if (rsLocal != null) try {
            rsLocal.close();
        } catch (SQLException logOrIgnore) {
        }
        if (pstmt != null) try {
            pstmt.close();
        } catch (SQLException logOrIgnore) {
        }
        if (connectionLocal != null) try {
            connectionLocal.close();
        } catch (SQLException logOrIgnore) {
        }

        return returnList;
    }

    private void closePreviousBillingPreiod(Long customerId, Date newBillingPeriodStartDate, DebitCredit debitCredit, Long billingPeriodId, FinanceEvents event) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(newBillingPeriodStartDate);
        calendar.add(Calendar.HOUR_OF_DAY, -2);

        SmsBillingTerms currentPeriod = smsBillingTermsService.getSmsBillingTermsById(billingPeriodId);

        if (currentPeriod != null) {
            Date lastPeriodDate = calendar.getTime();
            Date startDate = currentPeriod.getBillingTermsStartDateByDate(lastPeriodDate);
            Date endDate = currentPeriod.getBillingTermsEndDateBbyDate(lastPeriodDate);

            Soa currentSoa = soaService.getSoaByPeriod(startDate, endDate, customerId, billingPeriodId, debitCredit, event);
            if (currentSoa != null) {
                if (!currentSoa.getClosed()) {
                    currentSoa.setClosed(true);
                    soaService.save(currentSoa);
                }
            }

        }




    }

    private void createInvoices(Long customerId, List<String> uidList, List<String> cidList) {
        List<Soa> list = soaService.getClosedSoaByCustomerAndEvent(customerId, FinanceEvents.Current_Traffic);
        for (Soa currentSoa : list) {
            //Outgoing invoice
            if (currentSoa.getDebitCredit() == DebitCredit.Debit) {
                updateOutgoingInvoice(null, customerId, currentSoa, uidList);
            }

            //Incoming invoice
            if (currentSoa.getDebitCredit() == DebitCredit.Credit) {
                updateIncoimingInvoice(null, customerId, currentSoa, cidList);
            }
        }

        outgoingInvoiceService.createEmail();
    }

    public void updateIncoimingInvoice(IncomingInvoice currentInvoice, Long customerId, Soa currentSoa, List<String> cidList) {
        Connection connectionLocal = null;
        Statement statementLocal = null;
        ResultSet rsLocal = null;

        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringStartDate = sqlFormat.format(currentSoa.getStartDate());
        String stringendDate = sqlFormat.format(currentSoa.getEndDate());

        String query = "SELECT routed_cid, mcc, mnc, SUM(attempts_success) AS count, SUM(vendor_price) AS sum FROM totals_sms WHERE created_at BETWEEN '" + stringStartDate + "' AND '" + stringendDate + "' AND uid IN ";
        String cids = getSqlParametersStringByList(cidList);
        query = query + "(" + cids + ") GROUP BY routed_cid, mcc, mnc";

        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statementLocal = connectionLocal.createStatement();
            rsLocal = statementLocal.executeQuery(query);
            List<IncomingInvoiceTrafficDetails> listTraffic = new ArrayList<>();
            Double totalSum = 0.00;
            while (rsLocal.next()) {
                String systemId = "";
                SmppVendorIps currentIps = smppVendorIpsService.getSmppVendorIpsByCid(rsLocal.getString("routed_cid"));
                if (currentIps != null) {
                    systemId = currentIps.getSystemId();
                }
                IncomingInvoiceTrafficDetails currentTrafficDetails = new IncomingInvoiceTrafficDetails();
                currentTrafficDetails.setCid(systemId);
                currentTrafficDetails.setMcc(rsLocal.getString("mcc"));
                currentTrafficDetails.setMnc(rsLocal.getString("mnc"));
                currentTrafficDetails.setCount(rsLocal.getLong("count"));
                currentTrafficDetails.setSum(rsLocal.getDouble("sum"));
                totalSum = totalSum + rsLocal.getDouble("sum");

                listTraffic.add(currentTrafficDetails);
            }

            if (currentInvoice == null) {
                currentInvoice = new IncomingInvoice();
            }
            currentInvoice.setStartDate(currentSoa.getStartDate());
            currentInvoice.setEndDate(currentSoa.getEndDate());
            currentInvoice.setPayDate(currentSoa.getPayDate());
            currentInvoice.setInvoiceSum(totalSum);
            currentInvoice.setBillingPeriodId(currentSoa.getBillingTermsId());
            currentInvoice.setCustomer_id(customerId);

            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            Calendar calendar = Calendar.getInstance(timeZone);

            currentInvoice.setDate(calendar.getTime());
            incomingInvoiceService.save(currentInvoice);

            for (IncomingInvoiceTrafficDetails currentDetail : listTraffic) {
                currentDetail.setInvoice_id(currentInvoice.getId());
                incomingInvoiceTrafficDetailsService.save(currentDetail);
            }

            currentSoa.setEvent(FinanceEvents.Incoming_Invoice);
            currentSoa.setSum(totalSum);
            currentSoa.setFinalSum(totalSum);
            currentSoa.setEventOwnerId(currentInvoice.getId());

            soaService.save(currentSoa);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rsLocal != null) try {
            rsLocal.close();
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

    public void updateOutgoingInvoice(OutgoingInvoice currentInvoice, Long customerId, Soa currentSoa, List<String> uidList) {
        Connection connectionLocal = null;
        Statement statementLocal = null;
        ResultSet rsLocal = null;

        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringStartDate = sqlFormat.format(currentSoa.getStartDate());
        String stringendDate = sqlFormat.format(currentSoa.getEndDate());

        String query = "SELECT uid, mcc, mnc, SUM(attempts_count) AS count, SUM(client_price) AS sum FROM totals_sms WHERE created_at BETWEEN '" + stringStartDate + "' AND '" + stringendDate + "' AND uid IN ";
        String uids = getSqlParametersStringByList(uidList);
        query = query + "(" + uids + ") GROUP BY uid, mcc, mnc";

        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statementLocal = connectionLocal.createStatement();
            rsLocal = statementLocal.executeQuery(query);
            List<OutgoingInvoiceTrafficDetails> listTraffic = new ArrayList<>();
            Double totalSum = 0.00;
            while (rsLocal.next()) {
                String systemId = "";
                SmppClientSystemId currentSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(rsLocal.getString("uid"));
                if (currentSystemId != null) {
                    systemId = currentSystemId.getSystemId();
                }
                OutgoingInvoiceTrafficDetails currentTrafficDetails = new OutgoingInvoiceTrafficDetails();
                currentTrafficDetails.setUid(systemId);
                currentTrafficDetails.setMcc(rsLocal.getString("mcc"));
                currentTrafficDetails.setMnc(rsLocal.getString("mnc"));
                currentTrafficDetails.setCount(rsLocal.getLong("count"));
                currentTrafficDetails.setSum(rsLocal.getDouble("sum"));
                totalSum = totalSum + rsLocal.getDouble("sum");

                listTraffic.add(currentTrafficDetails);
            }

            if (currentInvoice == null) {
                currentInvoice = new OutgoingInvoice();
            }
            currentInvoice.setStartDate(currentSoa.getStartDate());
            currentInvoice.setEndDate(currentSoa.getEndDate());
            currentInvoice.setPayDate(currentSoa.getPayDate());
            currentInvoice.setInvoiceSum(totalSum);
            currentInvoice.setBillingPeriodId(currentSoa.getBillingTermsId());
            currentInvoice.setCustomer_id(customerId);

            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            Calendar calendar = Calendar.getInstance(timeZone);

            currentInvoice.setDate(calendar.getTime());
            currentInvoice = outgoingInvoiceService.save(currentInvoice);

            List<EmailAttachment> emailAttachmentList = emailAttachmentService.createAttachmentsForOutgoingInvoice(currentInvoice);
            currentInvoice.setEmailAttachmentList(new HashSet<>(emailAttachmentList));
            currentInvoice.setProcessed(false);
            currentInvoice.setConfirmed(false);
            currentInvoice = outgoingInvoiceService.save(currentInvoice);

            for (OutgoingInvoiceTrafficDetails currentDetail : listTraffic) {
                currentDetail.setInvoice_id(currentInvoice.getId());
                outgoingInvoiceTrafficDetailsService.save(currentDetail);
            }

            currentSoa.setEvent(FinanceEvents.Outgoing_Invoice);
            currentSoa.setSum(totalSum);
            currentSoa.setFinalSum(totalSum);
            currentSoa.setEventOwnerId(currentInvoice.getId());

            soaService.save(currentSoa);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rsLocal != null) try {
            rsLocal.close();
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

}


