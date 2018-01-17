package app.service.eventLog;

import app.entity.EventLog;
import app.entity.enums.EventLogLevels;
import app.entity.enums.EventLogSections;
import app.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Олег on 01.08.2017.
 */

@Service
public class EventLogServiceImpl implements EventLogService{

    @Autowired
    EventLogRepository eventLogRepository;

    public void AddLogRecord(Date date, EventLogLevels level, EventLogSections section, String message, String object) {
        EventLog newLogRecord = new EventLog(date, level, section, message, object);
        eventLogRepository.save(newLogRecord);
    }
}
