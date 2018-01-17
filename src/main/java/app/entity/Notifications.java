package app.entity;

/**
 * Created by Олег on 04.12.2017.
 */

import app.entity.enums.NotificationIssues;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "date")
    private Date initiationDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "text")
    private String text;

    @Column(name = "seen")
    private Boolean seen = false;

    @Column(name = "sms_sent")
    private Boolean smsSent = false;

    @Column(name = "notification_issue")
    private NotificationIssues notificationIssue;

    @Column(name = "next_notification")
    private Date nextNotification;

    @Column(name = "processed")
    private Boolean processed = false;

    @Column(name = "uniqueness_key")
    private String uniqueness_key = "";


    public Notifications(Date initiationDate, Long userId, String text, NotificationIssues issue, String uniqueness_key) {
        this.initiationDate = initiationDate;
        this.userId = userId;
        this.text = text;
        this.notificationIssue = issue;
        this.uniqueness_key = uniqueness_key;
    }

    public Notifications() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getInitiationDate() {
        return initiationDate;
    }

    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Boolean getSmsSent() {
        return smsSent;
    }

    public void setSmsSent(Boolean smsSent) {
        this.smsSent = smsSent;
    }

    public NotificationIssues getNotificationIssue() {
        return notificationIssue;
    }

    public void setNotificationIssue(NotificationIssues notificationIssue) {
        this.notificationIssue = notificationIssue;
    }

    public Date getNextNotification() {
        return nextNotification;
    }

    public void setNextNotification(Date nextNotification) {
        this.nextNotification = nextNotification;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getUniqueness_key() {
        return uniqueness_key;
    }

    public void setUniqueness_key(String uniqueness_key) {
        this.uniqueness_key = uniqueness_key;
    }
}
