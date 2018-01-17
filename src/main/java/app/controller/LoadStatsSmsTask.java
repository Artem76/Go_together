package app.controller;

import app.service.tasks.StatsUpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 19.12.2017.
 */

@Component
public class LoadStatsSmsTask {

    @Autowired
    StatsUpdateTask statsUpdateTask;

    //@Scheduled(fixedDelay = 2000)
    public void runStreamMDR(){
        statsUpdateTask.runParallelStream();
    }
}
