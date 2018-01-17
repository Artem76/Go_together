package app.service.notifications;

import app.entity.Notifications;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 04.12.2017.
 */
public interface NotificationsService {

    void save(Notifications notification);
    List<Notifications> getNotificationsToSend();
    List<Notifications> getProcessedNotificationsByKeyAndDate(String key, Date nextDate);
}
