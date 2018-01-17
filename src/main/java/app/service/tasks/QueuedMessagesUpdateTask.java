package app.service.tasks;

import app.entity.*;
import app.service.logger.LoggerService;
import app.service.softswitch.SoftswitchService;
import app.service.softswitchTriggers.SoftswitchTriggersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Олег on 15.10.2017.
 */

@Service
public class QueuedMessagesUpdateTask {

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    @Autowired
    SoftswitchService softswitchService;

    @Autowired
    SoftswitchTriggersService softswitchTriggersService;

    @Autowired
    LoggerService loggerService;


    public void runParallelStream(){

        loggerService.writeInfo("Responses load task started");
        float averageUpdateTime = 0;

        Connection connection = null;
        ResultSet rs = null;
        Statement Statement = null;
        Connection connectionLocal = null;
        Statement statementLocal = null;

        Integer responsesLoaded = 0;

        List<Softswitch> Softswitches = softswitchService.getSoftswitchesAll();
        for (Softswitch softswEnt : Softswitches) {
            SoftswitchTriggers currentTriger = softswitchTriggersService.getTriggersBySoftswitchIdAndKey(softswEnt.getId(), "mdr_load_enabled");
            if (!currentTriger.getValue()) {
                continue;
            }

            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + softswEnt.getHost() + ":" + softswEnt.getMysqlPort() + "/" + softswEnt.getMysqlDatabase(), softswEnt.getMysqlUsername(), softswEnt.getMysqlPassword());
                Statement = connection.createStatement();

                connectionLocal = DriverManager
                        .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
                statementLocal = connectionLocal.createStatement();

                rs = Statement.executeQuery(getQueuedMessagesQuery(softswEnt.getLastLoadedDr()));

                List<Long> responsesToDelete = new ArrayList<>();

                Integer it = 0;
                Date startDate = new Date();
                while (rs.next()) {
                    it++;
                    Integer updatedRows = 0;
                    String submitStatus = rs.getString("submit_status");
                    if (submitStatus != null) {
                        if (submitStatus.equals("ESME_ROK")) {
                            updatedRows = statementLocal.executeUpdate("UPDATE mdr SET submit_status = '" + rs.getString("submit_status") + "', vendor_msgid = '" + rs.getString("vendor_msgid") +
                                    "', vendor_price = vendor_price_unconfirmed WHERE msgid = '" + rs.getString("msgid") + "'");
                            if (updatedRows > 0) {
                                responsesToDelete.add(rs.getLong("cdr_id"));
                            }
                        } else {
                            updatedRows = statementLocal.executeUpdate("UPDATE mdr SET submit_status = '" + rs.getString("submit_status") + "', vendor_msgid = '" + rs.getString("vendor_msgid") +
                                    "' WHERE msgid = '" + rs.getString("msgid") + "'");
                            if (updatedRows > 0) {
                                responsesToDelete.add(rs.getLong("cdr_id"));
                            }
                        }
                    } else {
                        updatedRows = statementLocal.executeUpdate("UPDATE mdr SET submit_status = '" + rs.getString("submit_status") + "', vendor_msgid = '" + rs.getString("vendor_msgid") +
                                "' WHERE msgid = '" + rs.getString("msgid") + "'");
                        if (updatedRows > 0) {
                            responsesToDelete.add(rs.getLong("cdr_id"));
                        }
                    }
                    responsesLoaded++;
                }

                if (responsesLoaded != 0) {
                    averageUpdateTime = ((startDate.getTime() - new java.util.Date().getTime())/1000)/responsesLoaded;
                }

                if (responsesToDelete.size() > 0) {
                    String cdrIdParameter = "";
                    it = 1;
                    for (Long currentCdrId : responsesToDelete) {
                        if (it != responsesToDelete.size()) {
                            cdrIdParameter = cdrIdParameter + "" + currentCdrId + ",";
                        } else {
                            cdrIdParameter = cdrIdParameter + "" + currentCdrId + "";
                        }
                        it++;
                    }

                    Statement.executeUpdate("DELETE FROM responses WHERE cdr_id IN (" + cdrIdParameter + ")");
                }

                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (Statement != null) try {
                    Statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statementLocal != null) try {
                    statementLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connection != null) try {
                    connection.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connectionLocal != null) try {
                    connectionLocal.close();
                } catch (SQLException logOrIgnore) {
                }

                loggerService.writeInfo("Responses load task finished without errors. Loaded " + responsesLoaded + " responses. Average UPDATE time " + averageUpdateTime + " sec.");

            } catch (Exception e) {
                e.printStackTrace();
                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (Statement != null) try {
                    Statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statementLocal != null) try {
                    statementLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connection != null) try {
                    connection.close();
                } catch (SQLException logOrIgnore) {
                }
                if (connectionLocal != null) try {
                    connectionLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                loggerService.writeInfo("Responses load task finished with errors. Error:\n " + e.toString());
            }


        }




    }

    private String getQueuedMessagesQuery(Long last_cdr_id) {
        return "SELECT * FROM responses LIMIT 10000";
    }

}
