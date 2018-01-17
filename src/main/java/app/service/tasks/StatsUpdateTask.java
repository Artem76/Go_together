package app.service.tasks;

import app.entity.StatsSmsRow;
import app.service.logger.LoggerService;
import app.service.statsSms.StatsSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * Created by Олег on 19.12.2017.
 */

@Service
public class StatsUpdateTask {

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    @Autowired
    StatsSmsService statsSmsService;

    @Autowired
    LoggerService loggerService;

    public void runParallelStream(){

        loggerService.writeInfo("Stats update task started");
        String key = "";

        Connection connectionLocal = null;
        Statement statementLocal = null;
        ResultSet rs = null;


        String query = "SELECT date_trunc('hour', created_at) as created_at, softswitch_id, uid, routed_cid, mcc, mnc, delivery_status as status, SUM(delivery_time) as delivery_time, SUM(1) as count\n" +
                "FROM mdr WHERE created_at BETWEEN date_trunc('hour', current_timestamp at time zone 'UTC' - Interval '120 minute') AND (current_timestamp at time zone 'UTC') AND (NOT(delivery_status IS NULL))\n" +
                "GROUP BY date_trunc('hour', created_at), softswitch_id, uid, routed_cid, mcc, mnc, delivery_status";

        try {
            connectionLocal = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statementLocal = connectionLocal.createStatement();
            rs = statementLocal.executeQuery(query);

            Integer totalsUpdated = 0;

            while (rs.next()) {
                key = "" + rs.getTimestamp("created_at") + " " + rs.getString("mcc") + " " + rs.getString("mnc") + " " + rs.getString("uid") + " " + rs.getString("routed_cid") + " " + rs.getString("status") + " " + rs.getLong("softswitch_id");
                StatsSmsRow currentRow = statsSmsService.getStatsByKeyFields(rs.getTimestamp("created_at"), rs.getString("mcc"), rs.getString("mnc"), rs.getString("uid"), rs.getString("routed_cid"), rs.getString("status"), rs.getLong("softswitch_id"));

                if (currentRow == null) {
                    currentRow = new StatsSmsRow();
                }

                currentRow.setUid(rs.getString("uid"));
                currentRow.setRouted_cid(rs.getString("routed_cid"));
                currentRow.setCreated_at(rs.getTimestamp("created_at"));
                currentRow.setMcc(rs.getString("mcc"));
                currentRow.setMnc(rs.getString("mnc"));
                currentRow.setStatus(rs.getString("status"));
                currentRow.setSoftswitch_id(rs.getLong("softswitch_id"));
                if (rs.getString("status") != null) {
                    if (rs.getString("status").equals("DELIVRD")) {
                        currentRow.setDelivery_time(rs.getLong("delivery_time"));
                    } else {
                        currentRow.setDelivery_time(0L);
                    }
                } else {
                    currentRow.setDelivery_time(0L);
                }
                currentRow.setCount(rs.getLong("count"));

                statsSmsService.save(currentRow);

                totalsUpdated++;

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

            loggerService.writeInfo("Stats update task finished without errors. Updated " + totalsUpdated + " rows of cache.");

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

            loggerService.writeInfo("Stats update task finished with errors. Error : \n" + e.toString());
            System.out.println(key);

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

    }

}
