package app.entity;

import app.entity.enums.EmailType;

import javax.persistence.*;
import java.util.*;

/**
 * Created by АРТЕМ on 11.09.2017.
 */
@Entity
@Table(name = "email_contents")
public class EmailContent {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_type")
    private EmailType emailType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incomingTT_id")
    private IncomingTT incomingTT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outgoingTT_id")
    private OutgoingTT outgoingTT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emailAccount_id")
    private EmailAccount emailAccount;

    @Column(name = "from_email")
    private String from;

    @Column(name = "to_email")
    private String to;

    @Column(name = "copy")
    private String copy;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "text_type_html")
    private Boolean textTypeHtml;

    @Column(name = "date")
    private Date date;

    @Column(name = "date_send")
    private Date dateSend;

    @Column(name = "processed")
    private Boolean processed = false;

    @Column(name = "email_attachments")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "emailContent")
    private Set<EmailAttachment> emailAttachmentList = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientRatesUpdate_id")
    private ClientRatesUpdate clientRatesUpdate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VendorRatesUpdate vendorRatesUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outgoingInvoice_id")
    private OutgoingInvoice outgoingInvoice;

    public EmailContent() {
    }

    public EmailContent(EmailType emailType, EmailAccount emailAccount, String from, String to, String copy, String subject, String body, Boolean textTypeHtml, Date date, Boolean processed) {
        this.emailType = emailType;
        this.emailAccount = emailAccount;
        this.from = from;
        this.to = to;
        this.copy = copy;
        this.subject = subject;
        this.body = body;
        this.textTypeHtml = textTypeHtml;
        this.date = date;
        this.processed = processed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public EmailAccount getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(EmailAccount emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<EmailAttachment> getEmailAttachmentList() {
        return emailAttachmentList;
    }

    public void setEmailAttachmentList(Set<EmailAttachment> emailAttachmentList) {
        this.emailAttachmentList = emailAttachmentList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getTextTypeHtml() {
        return textTypeHtml;
    }

    public void setTextTypeHtml(Boolean textTypeHtml) {
        this.textTypeHtml = textTypeHtml;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addEmailAttachment(EmailAttachment emailAttachment){
        this.emailAttachmentList.add(emailAttachment);
    }

    public IncomingTT getIncomingTT() {
        return incomingTT;
    }

    public void setIncomingTT(IncomingTT incomingTT) {
        this.incomingTT = incomingTT;
    }

    public OutgoingTT getOutgoingTT() {
        return outgoingTT;
    }

    public void setOutgoingTT(OutgoingTT outgoingTT) {
        this.outgoingTT = outgoingTT;
    }

    public ClientRatesUpdate getClientRatesUpdate() {
        return clientRatesUpdate;
    }

    public void setClientRatesUpdate(ClientRatesUpdate clientRatesUpdate) {
        this.clientRatesUpdate = clientRatesUpdate;
    }

    public VendorRatesUpdate getVendorRatesUpdate() {
        return vendorRatesUpdate;
    }

    public void setVendorRatesUpdate(VendorRatesUpdate vendorRatesUpdate) {
        this.vendorRatesUpdate = vendorRatesUpdate;
    }

    public Date getDateSend() {
        return dateSend;
    }

    public void setDateSend(Date dateSend) {
        this.dateSend = dateSend;
    }

    public OutgoingInvoice getOutgoingInvoice() {
        return outgoingInvoice;
    }

    public void setOutgoingInvoice(OutgoingInvoice outgoingInvoice) {
        this.outgoingInvoice = outgoingInvoice;
    }
}
