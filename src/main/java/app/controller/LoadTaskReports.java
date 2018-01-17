package app.controller;

import app.service.tasks.ReportsLoadTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 22.08.2017.
 */

@Component
public class LoadTaskReports {
    @Autowired
    ReportsLoadTask reportsLoadTask;

    //@Scheduled(fixedDelay = 1000)
    public void runStreamReportsLoad(){
        reportsLoadTask.runParallelStream();
    }
}
