package app.controller;

import app.service.tasks.BotTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 04.12.2017.
 */

@Component
public class LoadTaskBot {

    @Autowired
    BotTasks botTasks;

    //@Scheduled(fixedDelay = 10000)
    public void runStreamMDR(){
        botTasks.runParallelStream();
    }
}
