package app.controller;

import app.service.tasks.CachesUpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 02.11.2017.
 */

@Component
public class LoadCachesUodateTask {

    @Autowired
    CachesUpdateTask cachesUpdateTask;

    //@Scheduled(fixedDelay = 1000)
    public void runCachesUpdate(){
        cachesUpdateTask.runParallelStream();
    }
}
