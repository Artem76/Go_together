package app.service.softswitch;

import app.entity.enums.SoftswitchType;
import app.repository.SoftswitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.entity.Softswitch;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Олег on 06.07.2017.
 */

@Service
public class SoftswitchServiceImpl implements SoftswitchService {
    @Autowired
    SoftswitchRepository softswitchRepository;

    @Override
    public List<Softswitch>  getSoftswitchesAll() {
        return softswitchRepository.findAll();
    }

    @Override
    public Softswitch getSoftswitchById(Long id) {
        return softswitchRepository.findOne(id);
    }

    @Override
    @Transactional
    public boolean addSoftswitch(Softswitch softswitch) {
        softswitchRepository.save(softswitch);
        return true;
    }

    @Override
    @Transactional
    public boolean updateSoftswitch(long id, String name, SoftswitchType type, String host, Integer mysql_port, String mysql_database, String mysql_username, String mysql_password,
                                    String mysql_dr_table, String mysql_totals_table, String mysql_stats_table, String mysql_reports_table, Integer api_port,
                                    String api_username, String api_password) {

        Softswitch softswitch = softswitchRepository.getOne(id);
        softswitch.setName(name);
        softswitch.setSoftswitchType(type);
        softswitch.setHost(host);
        softswitch.setMysqlPort(mysql_port);
        softswitch.setMysqlDatabase(mysql_database);
        softswitch.setMysqlUsername(mysql_username);
        softswitch.setMysqlPassword(mysql_password);
        softswitch.setMysqlDrTable(mysql_dr_table);
        softswitch.setMysqlTotalsTable(mysql_totals_table);
        softswitch.setMysqlStatsTable(mysql_stats_table);
        softswitch.setMysqlReportsTable(mysql_reports_table);
        softswitch.setApiPort(api_port);
        softswitch.setApi_username(api_username);
        softswitch.setApi_password(api_password);


        softswitchRepository.save(softswitch);
        return true;

    }
}
