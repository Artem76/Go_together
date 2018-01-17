package app.service.tasks;

import app.entity.*;
import app.repository.*;
import app.service.logger.LoggerService;
import app.service.clientRatesHistroy.ClientRatesHistoryService;
import app.service.refbook.RefbookService;
import app.service.softswitch.SoftswitchService;
import app.service.softswitchTriggers.SoftswitchTriggersService;
import app.service.totalsCalculationScheduler.TotalsCalculationSchedulerService;
import app.service.totalsSms.TotalsSmsService;
import app.service.vendorRatesHistory.VendorRatesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;
import java.util.Date;


@Service
public class MDRLoadTask {
    @Autowired
    SoftswitchService softswitchService;

    @Autowired
    SoftswitchRepository softswitchRepository;

    @Autowired
    MdrRepository MdrRepository;

    @Autowired
    TotalsSmsRepository totalsSmsRepository;

    @Autowired
    SoftswitchTriggersService softswitchTriggersService;

    @Autowired
    TotalsSmsService totalsSmsService;

    @Autowired
    RefbookService refbookService;

    @Autowired
    ClientRatesHistoryService clientRatesHistoryService;

    @Autowired
    VendorRatesHistoryService vendorRatesHistoryService;

    @Autowired
    TotalsCalculationSchedulerService totalsCalculationSchedulerService;

    @Autowired
    LoggerService loggerService;

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    public void runParallelStream(){

        loggerService.writeInfo("MDR load task started.");
        float averageUpdateTime = 0;

        //System.out.println("Start mdr");
        List<Softswitch> Softswitches = softswitchService.getSoftswitchesAll();

        for (Softswitch softswEnt : Softswitches) {
            SoftswitchTriggers currentTriger = softswitchTriggersService.getTriggersBySoftswitchIdAndKey(softswEnt.getId(), "mdr_load_enabled");
            if (!currentTriger.getValue()) {
                continue;
            }
            Connection connection = null;
            Connection connectionLocal = null;
            ResultSet rsLocal = null;
            ResultSet rs = null;
            Statement statementLocal = null;
            Statement Statement = null;
            Statement statementUpdate = null;

            Integer mdrLoaded = 0;

            List<Long> mdrToDelete = new ArrayList<>();

            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + softswEnt.getHost() + ":" + softswEnt.getMysqlPort() + "/" + softswEnt.getMysqlDatabase() + "?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes", softswEnt.getMysqlUsername(), softswEnt.getMysqlPassword());
                connectionLocal = DriverManager
                        .getConnection(sqlConnectionString + "?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes", sqlUsername, sqlPassword);
                Statement = connection.createStatement();
                rs = Statement.executeQuery(getMdrQuery(softswEnt.getLastLoadedDr()));
                Long last_loaded_dr = -1L;
                Date last_loaded_dr_date = null;

                Long iter = 0L;

                statementLocal = connectionLocal.createStatement();
                rsLocal = statementLocal.executeQuery("SELECT id FROM mdr ORDER BY id DESC LIMIT 1");
                if (rsLocal.next()) {
                    iter = rsLocal.getLong("id");
                }
                rsLocal.close();
                statementLocal.close();

                List<Date> updateTotalsDates = new ArrayList<>();
                Boolean dateFound;

                Date startTaskDate = new Date();
                while (rs.next()) {
                    iter++;


                    /// Если сообщение пришло без мцц, мнц то находим их и ищем цену
                    float clientPrice = rs.getFloat(".client_price");
                    float vendorPrice = rs.getFloat(".vendor_price");

                    String mcc = rs.getString("mcc");
                    String mnc = rs.getString("mnc");

                    if (mcc == null) {
                        Refbook currentRefbook = refbookService.getRefbookByNumber(rs.getString("destination_addr"));
                        if (currentRefbook != null) {
                            mcc = currentRefbook.getMcc();
                            mnc = currentRefbook.getMnc();
                            clientPrice = clientRatesHistoryService.getRateByMccAndMnc(rs.getString("uid"), rs.getDate("created_at"), mcc, mnc);
                            if (rs.getString("submit_status").equals("ESME_ROK")) {
                                vendorPrice = vendorRatesHistoryService.getRateByMccAndMnc(rs.getString("routed_cid"), rs.getDate("created_at"), mcc, mnc);
                            }
                        }
                    } else {
                        if (mcc.equals(" ")) {
                            Refbook currentRefbook = refbookService.getRefbookByNumber(rs.getString("destination_addr"));
                            if (currentRefbook != null) {
                                mcc = currentRefbook.getMcc();
                                mnc = currentRefbook.getMnc();
                            }
                        }
                    }



                    PreparedStatement ps = connectionLocal.prepareStatement("INSERT INTO mdr (id, client_price, created_at, destination_addr, mcc, mnc, msgid, pdu_count, registered_delivery, routed_cid," +
                            "short_message,softswitch_id, source_addr, source_connector,trials, uid, vendor_price_unconfirmed, cdr_id, submit_status) VALUES (?, ?, ?, ?, ?, ?, ?" +
                            ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Queued')");

                    ps.setLong(1, iter);

                    ps.setFloat(2, clientPrice);
                    ps.setTimestamp(3, rs.getTimestamp("created_at"));
                    ps.setString(4, rs.getString("destination_addr"));
                    ps.setString(5, mcc);
                    ps.setString(6, mnc);
                    ps.setString(7, rs.getString("msgid"));
                    ps.setInt(8, rs.getInt("pdu_count"));
                    ps.setBoolean(9, rs.getBoolean("registered_delivery"));
                    ps.setString(10, rs.getString("routed_cid"));
                    ps.setString(11, rs.getString("short_message").replace('\0', ' '));
                    ps.setLong(12, softswEnt.getId());
                    ps.setString(13, rs.getString("source_addr"));
                    ps.setString(14, rs.getString("source_connector"));
                    ps.setInt(15, rs.getInt("trials"));
                    ps.setString(16, rs.getString("uid"));
                    ps.setFloat(17, vendorPrice);
                    ps.setLong(18, rs.getLong("cdr_id"));

                    Date messageDate = rs.getTimestamp("date_trunc");

                    dateFound = false;

                    for (Date currentDate : updateTotalsDates) {
                        if (currentDate.equals(messageDate)) {
                            dateFound = true;
                            break;
                        }
                    }

                    if (!dateFound) {
                        updateTotalsDates.add(messageDate);
                    }

                    // Осталось дозагрузить
                    //delivery_time, delivery_status, status_at, submit_status, vendor_msgid, vendor_price зполнить из vendor_price_unconfirmed

                    ps.executeUpdate();

                    mdrToDelete.add(rs.getLong("cdr_id"));

                    last_loaded_dr = rs.getLong("cdr_id");
                    mdrLoaded++;
                }

                if (mdrLoaded != 0) {
                    averageUpdateTime = ((startTaskDate.getTime() - new java.util.Date().getTime())/1000)/mdrLoaded;
                }

                if (last_loaded_dr != -1L) {
                    ///Делаем итоги!!!
                    //rs = Statement.executeQuery(getTotalsQuery(softswEnt.getLastLoadedDr(), last_loaded_dr));

                    softswEnt.setLastLoadedDr(last_loaded_dr);
                }


                Integer it = 1;
                if (mdrToDelete.size() > 0) {
                    String cdrIdParameter = "";
                    it = 1;
                    for (Long currentCdrId : mdrToDelete) {
                        if (it != mdrToDelete.size()) {
                            cdrIdParameter = cdrIdParameter + "" + currentCdrId + ",";
                        } else {
                            cdrIdParameter = cdrIdParameter + "" + currentCdrId + "";
                        }
                        it++;
                    }

                    Statement.executeUpdate("DELETE FROM log WHERE cdr_id IN (" + cdrIdParameter + ")");
                }

                softswitchRepository.save(softswEnt);
                connection.close();
                connectionLocal.close();


                for (Date currentDate : updateTotalsDates) {
                    TimeZone timeZone = TimeZone.getTimeZone("UTC");
                    Calendar calendar = Calendar.getInstance(timeZone);
                    calendar.setTime(currentDate);

                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    Date startDate = calendar.getTime();


                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    Date endDate = calendar.getTime();

                    totalsCalculationSchedulerService.updateTotalsScheduler(startDate, endDate, null);
                }


                loggerService.writeInfo("MDR load task finished without errors. Loaded " + mdrLoaded + " messages. Average INSERT time " + averageUpdateTime + " sec.");
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                totalsSmsService.setSavingFlag(false);
                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (rsLocal != null) try {
                    rsLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                if (Statement != null) try {
                    Statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statementUpdate != null) try {
                    statementUpdate.close();
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
                loggerService.writeInfo("MDR load task finished with errors. Error:\n" + e.toString());
                return;
            } catch (Exception e) {
                e.printStackTrace();
                totalsSmsService.setSavingFlag(false);
                if (rs != null) try {
                    rs.close();
                } catch (SQLException logOrIgnore) {
                }
                if (rsLocal != null) try {
                    rsLocal.close();
                } catch (SQLException logOrIgnore) {
                }
                if (Statement != null) try {
                    Statement.close();
                } catch (SQLException logOrIgnore) {
                }
                if (statementUpdate != null) try {
                    statementUpdate.close();
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
            }
            if (rs != null) try {
                rs.close();
            } catch (SQLException logOrIgnore) {
            }
            if (rsLocal != null) try {
                rsLocal.close();
            } catch (SQLException logOrIgnore) {
            }
            if (Statement != null) try {
                Statement.close();
            } catch (SQLException logOrIgnore) {
            }
            if (statementUpdate != null) try {
                statementUpdate.close();
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
        }
    }

    private String getMdrQuery(Long lastMdr) {
        return "select *, (created_at - interval MINUTE(created_at) minute - interval SECOND(created_at) second) as date_trunc, CASE WHEN mnc_client_price <> 0 THEN mnc_client_price ELSE mnc_cleint_priceflt END as client_price, CASE WHEN mnc_vendor_price <> 0 THEN mnc_vendor_price ELSE mnc_vendor_priceflt END as vendor_price from (\n" +
                "select log.*, CAST(acp.price as DECIMAL(10,5)) as mnc_client_price, CAST(acpflt.price as DECIMAL(10,5)) as mnc_cleint_priceflt, CAST(COALESCE(avp.price, 0) as DECIMAL(10,5)) as mnc_vendor_price, CAST(COALESCE(avpflt.price, 0) as DECIMAL(10,5)) as mnc_vendor_priceflt from (\n" +
                "select * from log " + " WHERE cdr_id > " + lastMdr + " LIMIT 10000) as log  \n" +
                "LEFT JOIN actual_client_prices as acp ON acp.client = log.uid AND acp.mcc = log.mcc AND acp.mnc = log.mnc \n" +
                "LEFT JOIN actual_client_prices as acpflt ON acpflt.client = log.uid AND acpflt.mcc = log.mcc AND acpflt.mnc = 'Flt' \n" +
                "LEFT JOIN actual_vendor_prices as avp ON avp.client = log.routed_cid AND avp.mcc = log.mcc AND avp.mnc = log.mnc \n" +
                "LEFT JOIN actual_vendor_prices as avpflt ON avpflt.client = log.routed_cid AND avpflt.mcc = log.mcc AND avpflt.mnc = 'Flt') as alldata ORDER BY alldata.cdr_id;";
    }
}