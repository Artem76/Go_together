package app.controller;

import app.service.tasks.RateActualizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Олег on 29.11.2017.
 */
@Component
public class LoadTaskRateActualization {

    @Autowired
    RateActualizator rateActualizator;

    //@Scheduled(fixedDelay = 5000)
    public void runStreamReportsLoad(){
        rateActualizator.runParallelStream();
    }
}
