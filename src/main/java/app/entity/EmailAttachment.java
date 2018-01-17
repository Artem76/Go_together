package app.entity;

import javax.persistence.*;

/**
 * Created by АРТЕМ on 11.09.2017.
 */
@Entity
@Table(name = "email_attachments")
public class EmailAttachment {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_body", columnDefinition="bytea")
    private byte[] fileBody;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emailContent_id")
    private EmailContent emailContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outgoingInvoice_id")
    private OutgoingInvoice outgoingInvoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public EmailAttachment() {
    }

    public EmailAttachment(String fileName, byte[] fileBody, EmailContent emailContent) {
        this.fileName = fileName;
        this.fileBody = fileBody;
        this.emailContent = emailContent;
    }

    public EmailAttachment(String fileName, byte[] fileBody, EmailContent emailContent, OutgoingInvoice outgoingInvoice) {
        this.fileName = fileName;
        this.fileBody = fileBody;
        this.emailContent = emailContent;
        this.outgoingInvoice = outgoingInvoice;
    }

    public EmailAttachment(String fileName, byte[] fileBody, EmailContent emailContent, OutgoingInvoice outgoingInvoice, Customer customer) {
        this.fileName = fileName;
        this.fileBody = fileBody;
        this.emailContent = emailContent;
        this.outgoingInvoice = outgoingInvoice;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileBody() {
        return fileBody;
    }

    public void setFileBody(byte[] fileBody) {
        this.fileBody = fileBody;
    }

    public EmailContent getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(EmailContent emailContent) {
        this.emailContent = emailContent;
    }

    public OutgoingInvoice getOutgoingInvoice() {
        return outgoingInvoice;
    }

    public void setOutgoingInvoice(OutgoingInvoice outgoingInvoice) {
        this.outgoingInvoice = outgoingInvoice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
