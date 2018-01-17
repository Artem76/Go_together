package app.controller;

import app.service.emailDelivery.EmailDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskEmail {
    @Autowired
    EmailDeliveryService emailDeliveryService;

//    @Scheduled(fixedDelay = 10000)
    public void runStreamTaskEmail(){
        emailDeliveryService.sendAndGetEmail(true);
    }

}
