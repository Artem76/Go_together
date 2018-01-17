package app.service.tasks;

import app.entity.Softswitch;
import app.entity.SoftswitchTriggers;
import app.entity.StatsSmsRow;
import app.repository.SoftswitchRepository;
import app.repository.StatsSmsRepository;
import app.service.logger.LoggerService;
import app.service.softswitch.SoftswitchService;
import app.service.softswitchTriggers.SoftswitchTriggersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Олег on 16.08.2017.
 */

@Service
public class ReportsLoadTask {
    @Autowired
    SoftswitchRepository softswitchRepository;

    @Autowired
    SoftswitchService softswitchService;

    @Autowired
    StatsSmsRepository statsSmsService;

    @Autowired
    SoftswitchTriggersService softswitchTriggersService;

    @Autowired
    LoggerService loggerService;

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    public void runParallelStream(){

        loggerService.writeInfo("Reports update task started");
        float averageUpdateTime = 0;

        //System.out.println("Start report");
        Connection connection = null;
        ResultSet rs = null;
        Statement Statement = null;
        Connection connectionLocal = null;
        Statement statementLocal = null;

        Integer reportsUpdated = 0;

        List<Softswitch> Softswitches = softswitchService.getSoftswitchesAll();
        for (Softswitch softswEnt : Softswitches) {
            SoftswitchTriggers currentTriger = softswitchTriggersService.getTriggersBySoftswitchIdAndKey(softswEnt.getId(), "mdr_load_enabled");
            if (!currentTriger.getValue()) {
                continue;
            }
            try {
                ArrayList<StatsSmsRow> statsArray = new ArrayList<StatsSmsRow>();
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + softswEnt.getHost() + ":" + softswEnt.getMysqlPort() + "/" + softswEnt.getMysqlDatabase(), softswEnt.getMysqlUsername(), softswEnt.getMysqlPassword());
                Statement = connection.createStatement();
                rs = Statement.executeQuery(getReportsQuery(softswEnt.getLastLoadedDr()));

                HashMap<String, ArrayList<String>> statusMap = new HashMap<String, ArrayList<String>>();
                Integer messagesCount = 0;
                Integer it = 0;

                connectionLocal = DriverManager
                        .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
                statementLocal = connectionLocal.createStatement();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                List<Long> reportsToDelete = new ArrayList<>();

                Date startDate = new Date();
                while (rs.next()) {
                    Integer updatedRows = statementLocal.executeUpdate("UPDATE mdr SET delivery_status = '" + rs.getString("status") +
                                        "', status_at = '" + format.format(rs.getTimestamp("status_at")) + "', delivery_time = EXTRACT(epoch FROM '" + format.format(rs.getTimestamp("status_at")) + "'::timestamp - created_at) WHERE msgid = '" + rs.getString("msgid") + "'");

                    if (updatedRows > 0) {
                        reportsToDelete.add(rs.getLong("cdr_id"));
                    }
                    it++;
                    reportsUpdated++;
                }
                if (reportsUpdated != 0) {
                    averageUpdateTime = ((startDate.getTime() - new java.util.Date().getTime())/1000)/reportsUpdated;
                }


                if (reportsToDelete.size() > 0) {
                    String cdrIdParameter = "";
                    it = 1;
                    for (Long currentCdrId : reportsToDelete) {
                        if (it != reportsToDelete.size()) {
                            cdrIdParameter = cdrIdParameter + "" + currentCdrId + ",";
                        } else {
                            cdrIdParameter = cdrIdParameter + "" + currentCdrId + "";
                        }
                        it++;
                    }

                    Statement.executeUpdate("DELETE FROM reports WHERE cdr_id IN (" + cdrIdParameter + ")");
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
                if (statementLocal != null) try {
                    statementLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connectionLocal != null) try {
                    connectionLocal.close();
                } catch (SQLException logOrIgnore) {
                }


                loggerService.writeInfo("Reports update task finished without errors. Updated " + reportsUpdated + " reports. Average UPDATE time " + averageUpdateTime + " sec.");

            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
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
                if (statementLocal != null) try {
                    statementLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connectionLocal != null) try {
                    connectionLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                loggerService.writeInfo("Reports update task finished with errors. Error:\n " + e.toString());
                return;
            } catch (Exception e) {
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
    }

    private String getReportsQuery(Long last_cdr_id) {
        return "SELECT * FROM reports LIMIT 10000";
    }
}
