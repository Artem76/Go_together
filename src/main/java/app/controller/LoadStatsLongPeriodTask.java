package app.controller;

import app.service.tasks.StatsUpdateTask;
import app.service.tasks.StatsUpdateTaskLongPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 20.12.2017.
 */

@Component
public class LoadStatsLongPeriodTask {

    @Autowired
    StatsUpdateTaskLongPeriod statsUpdateTaskLongPeriod;

    //@Scheduled(fixedDelay = 60000)
    public void runStreamMDR(){
        statsUpdateTaskLongPeriod.runParallelStream();
    }
}
