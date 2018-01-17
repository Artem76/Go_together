package app.entity;

import app.entity.enums.TTStatuses;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Олег on 29.08.2017.
 */

@Entity
@Table(name = "incoming_tt", indexes = {
        @Index(columnList = "date, customer_account_id", name = "incoming_tt_idx")})
public class IncomingTT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "customer_account_id")
    private Long customer_account_id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "solution")
    private String solution;

    @Column(name = "date_closed")
    private Date dateClosed;

    @Column(name = "status")
    private TTStatuses status;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_closed")
    private Long userClosed;

    @Column(name = "processed")
    private Boolean processed = false;

//    @ElementCollection
//    @CollectionTable(name="incomming_tt_related_tt", joinColumns=@JoinColumn(name="outgoing_tt_ids"))
//    @Column(name = "related_outgoing_tt")
//    private List<Long> relatedTT = new ArrayList<>();

//    @ElementCollection
//    @CollectionTable(name="incomming_tt_related_mdr", joinColumns=@JoinColumn(name="incoming_tt_msgids"))
//    @Column(name = "related_msgids")
//    private List<String> relatedMsgids = new ArrayList<>();

    @Column(name = "message_log_for_tt_list")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "incomingTT")
    private Set<MessageLogForTT> messageLogForTTList = new HashSet<>();

    @Column(name = "email_contents")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "incomingTT")
    private Set<EmailContent> emailContentList = new HashSet<>();

    @Column(name = "outgoing_tt_list")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "incomingTT")
    private Set<OutgoingTT> outgoingTTList = new HashSet<>();

    public IncomingTT(Date date, Long customer_account_id, String subject, String solution, TTStatuses status, Long userCreated) {
        this.date = date;
        this.customer_account_id = customer_account_id;
        this.subject = subject;
        this.solution = solution;
        this.status = status;
        this.userCreated = userCreated;
    }

    public IncomingTT(Date date, Long customer_account_id, String subject, String solution, TTStatuses status,
                      Long userCreated, Set<MessageLogForTT> messageLogForTTList) {
        this.date = date;
        this.customer_account_id = customer_account_id;
        this.subject = subject;
        this.solution = solution;
        this.status = status;
        this.userCreated = userCreated;
        this.messageLogForTTList = messageLogForTTList;
    }

    public IncomingTT(Date date, Long customer_account_id, String subject, String solution, TTStatuses status, Long userCreated, EmailContent emailContent) {
        this.date = date;
        this.customer_account_id = customer_account_id;
        this.subject = subject;
        this.solution = solution;
        this.status = status;
        this.userCreated = userCreated;
        this.emailContentList.add(emailContent);
    }

    public IncomingTT(Date date, Date dateClosed, Long customer_account_id, String subject, String solution, TTStatuses status,
                      Long userCreated, Long userClosed, Set<MessageLogForTT> messageLogForTTList) {
        this.date = date;
        this.customer_account_id = customer_account_id;
        this.subject = subject;
        this.solution = solution;
        this.status = status;
        this.userCreated = userCreated;
        this.messageLogForTTList = messageLogForTTList;
        this.userClosed = userClosed;
        this.dateClosed = dateClosed;
    }

    public IncomingTT() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public TTStatuses getStatus() {
        return status;
    }

    public void setStatus(TTStatuses status) {
        this.status = status;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserClosed() {
        return userClosed;
    }

    public void setUserClosed(Long userClosed) {
        this.userClosed = userClosed;
    }

    public Long getCustomer_account_id() {
        return customer_account_id;
    }

    public void setCustomer_account_id(Long customer_account_id) {
        this.customer_account_id = customer_account_id;
    }

    public List<EmailContent> getEmailContentList() {
        List<EmailContent> returnList = new ArrayList<>();
        for (EmailContent ec: emailContentList) {
            if (!returnList.contains(ec)) returnList.add(ec);
        }
        return returnList;
    }

    public void setEmailContentList(Set<EmailContent> emailContentList) {
        this.emailContentList = emailContentList;
    }

    public void addEmailContent(EmailContent emailContent){
        this.emailContentList.add(emailContent);
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Set<OutgoingTT> getOutgoingTTList() {
        return outgoingTTList;
    }

    public void setOutgoingTTList(Set<OutgoingTT> outgoingTTList) {
        this.outgoingTTList = outgoingTTList;
    }

    public void addOutgoingTT(OutgoingTT outgoingTT){
        this.outgoingTTList.add(outgoingTT);
    }

    public void addOutgoingTTList(List<OutgoingTT> outgoingTTList){
        this.outgoingTTList.addAll(outgoingTTList);
    }

    public Set<MessageLogForTT> getMessageLogForTTList() {
        return messageLogForTTList;
    }

    public void setMessageLogForTTList(Set<MessageLogForTT> messageLogForTTList) {
        this.messageLogForTTList = messageLogForTTList;
    }

    public void addMessageLogForTT(MessageLogForTT messageLogForTT){
        this.messageLogForTTList.add(messageLogForTT);
    }

    public void addMessageLogForTTList(List<MessageLogForTT> messageLogForTTList){
        this.messageLogForTTList.addAll(messageLogForTTList);
    }
}
