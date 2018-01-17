package app.repository;

import app.entity.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 04.12.2017.
 */
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {

    @Query("SELECT c FROM Notifications c WHERE c.processed = FALSE")
    List<Notifications> getNotificationsToProcess();

    @Query("SELECT c FROM Notifications  c WHERE c.uniqueness_key = :key AND c.nextNotification > :nextNotification")
    List<Notifications> getProcessedNotificationsByKeyAndDate(@Param("key") String key,
                                                              @Param("nextNotification") Date nextNotification);

}
