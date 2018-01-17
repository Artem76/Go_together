package app.service.indexReportService;

import app.entity.Refbook;
import app.entity.SmppClientSystemId;
import app.entity.SmppVendorIps;
import app.entityXML.IndexReport.ConsumptionRow;
import app.entityXML.IndexReport.IndexManagerReport;
import app.entityXML.IndexReport.MessageActivityChartRow;
import app.entityXML.IndexReport.ProfitRow;
import app.service.refbook.RefbookService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.smppVendorIps.SmppVendorIpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Олег on 14.10.2017.
 */

@Service
public class IndexManagerReportServiceImpl implements IndexManagerReportService {

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    RefbookService refbookService;

    @Autowired
    SmppVendorIpsService smppVendorIpsService;


    @Override
    public IndexManagerReport getIndexManagerReport(String login) {
        Connection connection = null;
        ResultSet rs = null;
        Statement statement = null;
        IndexManagerReport currentReport = new IndexManagerReport(0L, 0, 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        try {
            connection = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT SUM(attempts_count) as messages_count, SUM(client_price) as client_sum, SUM(vendor_price) as vendor_sum, SUM(client_price) - SUM(vendor_price) as profit FROM totals_sms WHERE created_at BETWEEN date_trunc('day', current_timestamp at time zone 'UTC') AND date_trunc('day', current_timestamp at time zone 'UTC') + Interval '23 hour' + Interval '59 minute' + Interval '59 second'");
            if (rs.next()) {
                currentReport.setMessagesCount(rs.getLong("messages_count"));
                currentReport.setIncomingSum(rs.getFloat("client_sum"));
                currentReport.setOutgoingSum(rs.getFloat("vendor_sum"));
                currentReport.setProfit(rs.getFloat("profit"));
            }
            ArrayList<MessageActivityChartRow> tempList = new ArrayList<>();
            ArrayList<MessageActivityChartRow> curretChartList = new ArrayList<>();

            rs = statement.executeQuery("SELECT hour, SUM(count) as count FROM (SELECT extract(hour from created_at) as hour, attempts_count as count FROM totals_sms WHERE created_at BETWEEN date_trunc('day', current_timestamp at time zone 'UTC') AND date_trunc('day', current_timestamp at time zone 'UTC') + Interval '23 hour' + Interval '59 minute' + Interval '59 second') as Data GROUP BY hour ORDER BY hour");
            while (rs.next()) {
                tempList.add(new MessageActivityChartRow(rs.getInt("hour"), rs.getLong("count")));
            }
            Integer i = 0;
            for (i = 0; i < 26; i++) {
                Boolean hourFound = false;
                for (MessageActivityChartRow currentRow : tempList) {
                    if (currentRow.getHour() == i) {
                        hourFound = true;
                        curretChartList.add(currentRow);
                        break;
                    }
                }
                if (!hourFound) {
                    curretChartList.add(new MessageActivityChartRow(i, 0L));
                }

            }
            currentReport.setMessageActivityTable(curretChartList);

            ArrayList<ProfitRow> currentProfitArray = new ArrayList<>();
            rs = statement.executeQuery("SELECT uid, mcc, SUM(attempts_count) as count,  SUM(client_price) - SUM(vendor_price) as profit FROM totals_sms WHERE created_at BETWEEN date_trunc('day', current_timestamp at time zone 'UTC') AND date_trunc('day', current_timestamp at time zone 'UTC') + Interval '23 hour' + Interval '59 minute' + Interval '59 second' GROUP BY uid, mcc ORDER BY SUM(client_price) - SUM(vendor_price) DESC LIMIT 10");
            while (rs.next()) {
                String countyName = rs.getString("mcc");
                Refbook currentCountry = refbookService.getRootRefbookByMcc(rs.getString("mcc"));
                if (currentCountry != null) {
                    countyName = currentCountry.getCountry();
                }

                SmppClientSystemId currentSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(rs.getString("uid"));
                String customerName = rs.getString("uid");
                if (currentSystemId != null) {
                    customerName = currentSystemId.getSmppClientAccount().getCustomer().getName() + " - " + currentSystemId.getSmppClientAccount().getName();
                }
                currentProfitArray.add(new ProfitRow(customerName, rs.getLong("count"), rs.getFloat("profit"), countyName));
            }
            currentReport.setProfitTable(currentProfitArray);

            ArrayList<ConsumptionRow> currentClientConsumption = new ArrayList<>();
            ArrayList<ConsumptionRow> currentVendorConsumption = new ArrayList<>();

            rs = statement.executeQuery("SELECT uid, SUM(client_price) as sum FROM totals_sms WHERE created_at BETWEEN date_trunc('day', current_timestamp at time zone 'UTC') AND date_trunc('day', current_timestamp at time zone 'UTC') + Interval '23 hour' + Interval '59 minute' + Interval '59 second' GROUP BY uid ORDER BY SUM(client_price) DESC");
            while (rs.next()) {
                String customerName = rs.getString("uid");
                String accountName = rs.getString("uid");
                SmppClientSystemId currentSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(rs.getString("uid"));
                if (currentSystemId != null) {
                    accountName = currentSystemId.getSmppClientAccount().getName();
                    customerName = currentSystemId.getSmppClientAccount().getCustomer().getName();
                }

                currentClientConsumption.add(new ConsumptionRow(customerName, accountName, rs.getFloat("sum")));
            }
            currentReport.setClientConsumptionTable(currentClientConsumption);



            rs = statement.executeQuery("SELECT routed_cid, SUM(vendor_price) as sum FROM totals_sms WHERE created_at BETWEEN date_trunc('day', current_timestamp at time zone 'UTC') AND date_trunc('day', current_timestamp at time zone 'UTC') + Interval '23 hour' + Interval '59 minute' + Interval '59 second' GROUP BY routed_cid ORDER BY SUM(vendor_price) DESC");
            while (rs.next()) {
                String customerName = rs.getString("routed_cid");
                String accountName = rs.getString("routed_cid");
                SmppVendorIps currentIps = smppVendorIpsService.getSmppVendorIpsByCid(rs.getString("routed_cid"));
                if (currentIps != null) {
                    accountName = currentIps.getSmppVendorAccount().getName();
                    customerName = currentIps.getSmppVendorAccount().getCustomer().getName();
                }

                currentVendorConsumption.add(new ConsumptionRow(customerName, accountName, rs.getFloat("sum")));
            }
            currentReport.setVendorConsumptionTable(currentVendorConsumption);


        } catch (SQLException e) {
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

        return currentReport;
    }

}
   