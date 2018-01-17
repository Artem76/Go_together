package app.entity;

import app.entity.enums.EventLogLevels;
import app.entity.enums.EventLogSections;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Олег on 01.08.2017.
 */

@Entity
@Table(name = "event_log", indexes = {
        @Index(columnList = "date, verbosity_level, event_section", name = "event_log_idx")})
public class EventLog {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "verbosity_level")
    private EventLogLevels verbosity_level;

    @NotNull
    @Column(name = "event_section")
    private EventLogSections event_section;

    @NotNull
    @Column(name = "message")
    private String message;

    @Column(name = "object", length = 50)
    private String object;

    public EventLog() {

    }

    public EventLog(Date date, EventLogLevels verbosity_level,EventLogSections event_section, String message, String object) {
        this.date = date;
        this.verbosity_level = verbosity_level;
        this.message = message;
        this.object = object;
        this.event_section = event_section;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventLogLevels getVerbosity_level() {
        return verbosity_level;
    }

    public void setVerbosity_level(EventLogLevels verbosity_level) {
        this.verbosity_level = verbosity_level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public EventLogSections getEvent_section() {
        return event_section;
    }

    public void setEvent_section(EventLogSections event_section) {
        this.event_section = event_section;
    }
}
