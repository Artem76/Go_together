package app.entityXML.incomingTTXML;

import java.util.Date;

/**
 * Created by Олег on 29.08.2017.
 */
public class OutgoingTTXML {
    private Long id;

    private String customer;

    private String dateOpened;

    private String dateClosed;

    private String userOpened;

    private String userClosed;

    private String status;

    private String subject;

    private Integer externalTTCount;

    private  Integer externalTTClosed;

    public OutgoingTTXML(Long id, String customer, String dateOpened, String dateClosed, String userOpened, String userClosed, String status, String subject) {
        this.id = id;
        this.customer = customer;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.userOpened = userOpened;
        this.userClosed = userClosed;
        this.status = status;
        this.subject = subject;
    }

    public OutgoingTTXML(Long id, String customer, String dateOpened, String dateClosed, String userOpened, String userClosed, String status, String subject, Integer externalTTCount, Integer externalTTClosed) {
        this.id = id;
        this.customer = customer;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.userOpened = userOpened;
        this.userClosed = userClosed;
        this.status = status;
        this.subject = subject;
        this.externalTTCount = externalTTCount;
        this.externalTTClosed = externalTTClosed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OutgoingTTXML() {

    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getUserOpened() {
        return userOpened;
    }

    public void setUserOpened(String userOpened) {
        this.userOpened = userOpened;
    }

    public String getUserClosed() {
        return userClosed;
    }

    public void setUserClosed(String userClosed) {
        this.userClosed = userClosed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
