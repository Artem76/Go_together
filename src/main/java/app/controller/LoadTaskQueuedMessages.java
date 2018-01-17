package app.controller;

import app.service.tasks.QueuedMessagesUpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Олег on 15.10.2017.
 */

@Component
public class LoadTaskQueuedMessages {

    @Autowired
    QueuedMessagesUpdateTask queuedMessagesUpdateTask;

    //@Scheduled(fixedDelay = 1000)
    public void runStreamReportsLoad(){
        queuedMessagesUpdateTask.runParallelStream();
    }
}
