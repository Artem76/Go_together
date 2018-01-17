package app.service.notifications;

import app.entity.Notifications;
import app.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 04.12.2017.
 */

@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    NotificationsRepository notificationsRepository;

    @Override
    public void save(Notifications notification) {
        notificationsRepository.save(notification);
    }

    @Override
    public List<Notifications> getNotificationsToSend() {
        List<Notifications> returnList = notificationsRepository.getNotificationsToProcess();

        return returnList;
    }

    @Override
    public List<Notifications> getProcessedNotificationsByKeyAndDate(String key, Date nextDate) {
        return notificationsRepository.getProcessedNotificationsByKeyAndDate(key, nextDate);
    }

}
