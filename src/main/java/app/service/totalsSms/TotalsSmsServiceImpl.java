package app.service.totalsSms;

import app.entity.TotalsCalculationScheduler;
import app.entity.TotalsSmsRow;
import app.repository.TotalsSmsRepository;
import app.service.totalsCalculationScheduler.TotalsCalculationSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Олег on 09.07.2017.
 */
@Service
public class TotalsSmsServiceImpl implements TotalsSmsService {

    public static boolean savingFlag = false;

    @Value("${spring.datasource.url}")
    private String sqlConnectionString;

    @Value("${spring.datasource.username}")
    private String sqlUsername;

    @Value("${spring.datasource.password}")
    private String sqlPassword;

    @Autowired
    TotalsSmsRepository totalsSmsRepository;

    @Autowired
    TotalsCalculationSchedulerService totalsCalculationSchedulerService;

    @Override
    public List<TotalsSmsRow> getTotalsByDate(Date date) {
        return totalsSmsRepository.findTotalsByDate(date);
    }

    @Override
    public TotalsSmsRow getTotalsByKeyFields(Date date, String mcc, String mnc, String uid, String routed_cid, long softswitch_id, long client_account_id,
                                             long vendor_account_id, long vendor_account_ip_id) {
        return totalsSmsRepository.getTotalsByKeyFields(date, mcc, mnc, uid, routed_cid, softswitch_id);
    }

    @Override
    public void save(TotalsSmsRow totalsSmsRow){
        totalsSmsRepository.save(totalsSmsRow);
    }

    @Override
    public boolean getSavingFlag() {
        return savingFlag;
    }

    @Override
    public void setSavingFlag(boolean flag) {
        savingFlag = flag;
    }

    @Override
    public List<TotalsSmsRow> getTotalsByDay(Date date) {
        List<TotalsSmsRow> returnList = new ArrayList<>();

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();


        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();

        //System.out.println("Start date: " + startDate);
        //System.out.println("End date: " + endDate);

        returnList = totalsSmsRepository.getTotalsSmsByDate(startDate, endDate);

        return returnList;
    }

    @Override
    public void recalculateTotalsByDates(Date startDate, Date endDate) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager
                    .getConnection(sqlConnectionString, sqlUsername, sqlPassword);
            statement = connection.prepareStatement(getQueryByDates());
            statement.setTimestamp(1, new Timestamp(startDate.getTime()));
            statement.setTimestamp(2, new Timestamp(endDate.getTime()));

            rs = statement.executeQuery();
            while (rs.next()) {
                TotalsSmsRow curentRow = totalsSmsRepository.getTotalsByKeyFields(rs.getTimestamp("created_at"), rs.getString("mcc"), rs.getString("mnc"),
                        rs.getString("uid"), rs.getString("routed_cid"), rs.getLong("softswitch_id"));

                if (curentRow == null) {
                    curentRow = new TotalsSmsRow(rs.getString("uid"), rs.getString("routed_cid"), rs.getTimestamp("created_at"), rs.getString("mcc"),
                            rs.getString("mnc"), rs.getFloat("client_price"), rs.getFloat("vendor_price"),
                            rs.getLong("attempts_count"), rs.getLong("attempts_success"), rs.getLong("registered_delivery"), rs.getLong("softswitch_id"));
                } else {
                    curentRow.setClient_price(rs.getFloat("client_price"));
                    curentRow.setVendor_price(rs.getFloat("vendor_price"));
                    curentRow.setAttempts_count(rs.getLong("attempts_count"));
                    curentRow.setAttempts_success(rs.getLong("attempts_success"));
                    curentRow.setRegistered_delivery(rs.getLong("registered_delivery"));
                }
                totalsSmsRepository.save(curentRow);

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


    }

    @Override
    public void recalculateTotalsByDatesAndCustomer(Date startDate, Date endDate, Long customerId) {

    }

    private String getQueryByDates() {
        return "SELECT\n" +
                "\tcreated_at,\n" +
                "    uid,\n" +
                "    routed_cid,\n" +
                "    mcc,\n" +
                "    mnc,\n" +
                "    softswitch_id,\n" +
                "    SUM(attempts_count) AS attempts_count,\n" +
                "    SUM(attempts_success) AS attempts_success,\n" +
                "    SUM(registered_delivery) AS registered_delivery,\n" +
                "    SUM(client_price) AS client_price,\n" +
                "    SUM(vendor_price) AS vendor_price\n" +
                "FROM (\n" +
                "\tSELECT \n" +
                "\t\tdate_trunc('hour', created_at) as created_at, \n" +
                "\t\tuid, \n" +
                "\t\trouted_cid, \n" +
                "\t\tmcc, \n" +
                "\t\tmnc,\n" +
                "\t\t1 as attempts_count,\n" +
                "\t\tCASE WHEN registered_delivery THEN 1 ELSE 0 END as registered_delivery,\n" +
                "\t\tCASE WHEN submit_status = 'ESME_ROK' THEN 1 ELSE 0 END AS attempts_success,\n" +
                "\t\tclient_price,\n" +
                "\t\tvendor_price,\n" +
                "    \tsoftswitch_id\n" +
                "\tFROM mdr\n" +
                "\t\tWHERE created_at >= ? AND created_at <= ?\n" +
                ") AS AllData\n" +
                "GROUP BY\n" +
                "\tcreated_at,\n" +
                "    uid,\n" +
                "    routed_cid,\n" +
                "    mcc,\n" +
                "    mnc,\n" +
                "    softswitch_id";
    }
}
