package app.controller;

import app.service.tasks.MDRLoadTask;
import app.service.tasks.ReportsLoadTask;
import app.service.tasks.SoftswitchUpdateTask;
import app.service.tasks.TotalsSMSLoadTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoadTasksMdr {
    @Autowired
    MDRLoadTask mdrLoadTask;

    //@Scheduled(fixedDelay = 500)
    public void runStreamMDR(){
        //System.out.println("Start");
        mdrLoadTask.runParallelStream();
    }

}
