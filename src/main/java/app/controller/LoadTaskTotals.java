package app.controller;

import app.service.tasks.TotalsSMSLoadTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 13.12.2017.
 */
@Component
public class LoadTaskTotals {

    @Autowired
    TotalsSMSLoadTask totalsSMSLoadTask;

    //@Scheduled(fixedDelay = 1000)
    public void runStreamReportsLoad(){
        totalsSMSLoadTask.runParallelStream();
    }
}
