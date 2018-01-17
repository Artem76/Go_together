package app.repository;

import app.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Олег on 01.08.2017.
 */
public interface EventLogRepository  extends JpaRepository<EventLog, Long> {
}
