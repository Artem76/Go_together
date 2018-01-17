package app.entity;

/**
 * Created by Олег on 20.10.2017.
 */

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sms_outgoing_invoices")
public class OutgoingInvoice {

    @Id
    @GeneratedValue
    private long id;

    private Date date;

    private Date startDate;

    private Date endDate;

    private Date payDate;

    private Long customer_id;

    private String sent_to;

    private String billingPeriodName;

    private Long billingPeriodId;

    private Double invoiceSum;

    private Boolean confirmed;

    private Boolean processed;

    @Column(name = "email_attachments")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "outgoingInvoice")
    private Set<EmailAttachment> emailAttachmentList = new HashSet<>();

    @Column(name = "email_contents")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "outgoingInvoice")
    private Set<EmailContent> emailContents = new HashSet<>();

    public OutgoingInvoice() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getSent_to() {
        return sent_to;
    }

    public void setSent_to(String sent_to) {
        this.sent_to = sent_to;
    }

    public String getBillingPeriodName() {
        return billingPeriodName;
    }

    public void setBillingPeriodName(String billingPeriodName) {
        this.billingPeriodName = billingPeriodName;
    }

    public Long getBillingPeriodId() {
        return billingPeriodId;
    }

    public void setBillingPeriodId(Long billingPeriodId) {
        this.billingPeriodId = billingPeriodId;
    }

    public Double getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(Double invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Set<EmailAttachment> getEmailAttachmentList() {
        return emailAttachmentList;
    }

    public void setEmailAttachmentList(Set<EmailAttachment> emailAttachmentList) {
        this.emailAttachmentList = emailAttachmentList;
    }

    public void addEmailAttachment(EmailAttachment emailAttachment){
        this.emailAttachmentList.add(emailAttachment);
    }

    public Set<EmailContent> getEmailContents() {
        return emailContents;
    }

    public void setEmailContents(Set<EmailContent> emailContents) {
        this.emailContents = emailContents;
    }

    public void addEmailContent(EmailContent emailContent){
        this.emailContents.add(emailContent);
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
}
