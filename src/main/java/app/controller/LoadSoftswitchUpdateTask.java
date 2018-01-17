package app.controller;

import app.service.tasks.SoftswitchUpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 27.08.2017.
 */

@Component
public class LoadSoftswitchUpdateTask {
    @Autowired
    SoftswitchUpdateTask softswitchUpdateTask;

    //@Scheduled(fixedDelay = 1000)
    public void runStreamMDR(){
        softswitchUpdateTask.runParallelStream();
    }
}
