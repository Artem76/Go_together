package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 20.12.2017.
 */

@Entity
@Table(name = "basic_tests_details")
public class BasicTestDetails {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "test_id")
    private Long testId;

    @Column(name = "source_addr")
    private String sourceAddr;

    @Column(name = "destination_addr")
    private String destinationAddr;

    @Column(name = "text")
    private String text;

    @Column(name = "vendor_account_id")
    private Long vendorAccount;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "submit_status")
    private String submitStatus;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "must_be_delivered")
    private Boolean mustBeDelivered = false;

    @Column(name = "msgid")
    private String msgid;

    @Column(name = "vendor_msgid")
    private String vendorMsgid;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "mnc")
    private String mnc;

    public BasicTestDetails() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    public String getDestinationAddr() {
        return destinationAddr;
    }

    public void setDestinationAddr(String destinationAddr) {
        this.destinationAddr = destinationAddr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getVendorAccount() {
        return vendorAccount;
    }

    public void setVendorAccount(Long vendorAccount) {
        this.vendorAccount = vendorAccount;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Boolean getMustBeDelivered() {
        return mustBeDelivered;
    }

    public void setMustBeDelivered(Boolean mustBeDelivered) {
        this.mustBeDelivered = mustBeDelivered;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getVendorMsgid() {
        return vendorMsgid;
    }

    public void setVendorMsgid(String vendorMsgid) {
        this.vendorMsgid = vendorMsgid;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }
}
