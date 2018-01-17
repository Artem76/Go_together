package app.service.softswitch;

import app.entity.Softswitch;
import app.entity.enums.SoftswitchType;

import java.util.List;

/**
 * Created by Олег on 06.07.2017.
 */
public interface SoftswitchService {
    List<Softswitch> getSoftswitchesAll();
    Softswitch getSoftswitchById(Long id);
    boolean addSoftswitch(Softswitch softswitch);
    boolean updateSoftswitch(long id, String name, SoftswitchType type, String host, Integer mysql_port, String mysql_database, String mysql_username, String mysql_password,
                             String mysql_dr_table, String mysql_totals_table, String mysql_stats_table, String mysql_reports_table, Integer api_port,
                             String api_username, String api_password);
}
