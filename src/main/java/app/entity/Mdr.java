package app.entity;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 * Created by Олег on 09.07.2017.
 */

@Entity
@Table(name = "mdr", indexes = {
        @Index(columnList = "msgid, vendor_msgid, created_at, uid, routed_cid, destination_addr, source_addr", name = "mdr_idx")})
public class Mdr {
    @Id
    @Column(name = "id", length = 45)
    private long id;

    @Column(name = "msgid", length = 45)
    private String msgid;

    @Column(name = "source_connector", length = 15)
    private String source_connector;

    @Column(name = "routed_cid", length = 100)
    private String routed_cid;

    @Column(name = "source_addr", length = 40)
    private String source_addr;

    @Column(name = "destination_addr", length = 40)
    private String destination_addr;

    @Column(name = "pdu_count")
    private Integer pdu_count;

    @Column(name = "short_message", columnDefinition="text")
    private String short_message;

    @Column(name = "binary_message")
    private Blob binary_message;

    @Column(name = "uid", length = 100)
    private String uid;

    @Column(name = "trials")
    private Integer trials;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "status_at")
    private Timestamp status_at;

    @Column(name = "registered_delivery")
    private Boolean registered_delivery;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "mnc")
    private String mnc;

    @Column(name = "vendor_msgid", length = 45)
    private String vendor_msgid;

    @Column(name = "client_price")
    private Float client_price;

    @Column(name = "vendor_price")
    private Float vendor_price;

    @Column(name = "vendor_price_unconfirmed")
    private Float vendor_price_unconfirmed;

    @Column(name = "submit_status", length = 100)
    private String submit_status;

    @Column(name = "delivery_status", length = 100)
    private String delivery_status;

    @Column(name = "delivery_time", length = 100)
    private Long delivery_time;

    @Column(name = "softswitch_id", length = 100)
    private Long softswitch_id;

    @Column(name = "cdr_id", length = 100)
    private Long cdrId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mdr(String msgid, String source_connector, String routed_cid, String source_addr, String destination_addr,
               Integer pdu_count, String short_message, Blob binary_message, String uid, Integer trials, Timestamp created_at,
               Timestamp status_at, Boolean registered_delivery, String mcc, String mnc, String vendor_msgid, Float client_price,
               Float vendor_price, String submit_status, String delivery_status, Long delivery_time) {
        this.msgid = msgid;
        this.source_connector = source_connector;
        this.routed_cid = routed_cid;
        this.source_addr = source_addr;
        this.destination_addr = destination_addr;
        this.pdu_count = pdu_count;
        this.short_message = short_message;
        this.binary_message = binary_message;
        this.uid = uid;
        this.trials = trials;
        this.created_at = created_at;
        this.status_at = status_at;
        this.registered_delivery = registered_delivery;
        this.mcc = mcc;
        this.mnc = mnc;
        this.vendor_msgid = vendor_msgid;
        this.client_price = client_price;
        this.vendor_price = vendor_price;
        this.submit_status = submit_status;
        this.delivery_status = delivery_status;
        this.delivery_time = delivery_time;

    }

    public Mdr() {

    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getSource_connector() {
        return source_connector;
    }

    public void setSource_connector(String source_connector) {
        this.source_connector = source_connector;
    }

    public String getRouted_cid() {
        return routed_cid;
    }

    public void setRouted_cid(String routed_cid) {
        this.routed_cid = routed_cid;
    }

    public String getSource_addr() {
        return source_addr;
    }

    public void setSource_addr(String source_addr) {
        this.source_addr = source_addr;
    }

    public String getDestination_addr() {
        return destination_addr;
    }

    public void setDestination_addr(String destination_addr) {
        this.destination_addr = destination_addr;
    }

    public Integer getPdu_count() {
        return pdu_count;
    }

    public void setPdu_count(Integer pdu_count) {
        this.pdu_count = pdu_count;
    }

    public String getShort_message() {
        return short_message;
    }

    public void setShort_message(String short_message) {
        this.short_message = short_message;
    }

    public Blob getBinary_message() {
        return binary_message;
    }

    public void setBinary_message(Blob binary_message) {
        this.binary_message = binary_message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getTrials() {
        return trials;
    }

    public void setTrials(Integer trials) {
        this.trials = trials;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getStatus_at() {
        return status_at;
    }

    public void setStatus_at(Timestamp status_at) {
        this.status_at = status_at;
    }

    public Boolean getRegistered_delivery() {
        return registered_delivery;
    }

    public void setRegistered_delivery(Boolean registered_delivery) {
        this.registered_delivery = registered_delivery;
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

    public String getVendor_msgid() {
        return vendor_msgid;
    }

    public void setVendor_msgid(String vendor_msgid) {
        this.vendor_msgid = vendor_msgid;
    }

    public Float getClient_price() {
        return client_price;
    }

    public void setClient_price(Float client_price) {
        this.client_price = client_price;
    }

    public Float getVendor_price() {
        return vendor_price;
    }

    public void setVendor_price(Float vendor_price) {
        this.vendor_price = vendor_price;
    }

    public String getSubmit_status() {
        return submit_status;
    }

    public void setSubmit_status(String submit_status) {
        this.submit_status = submit_status;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public Long getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Long delivery_time) {
        this.delivery_time = delivery_time;
    }

    public Long getSoftswitch_id() {
        return softswitch_id;
    }

    public void setSoftswitch_id(Long softswitch_id) {
        this.softswitch_id = softswitch_id;
    }

    public Float getVendor_price_unconfirmed() {
        return vendor_price_unconfirmed;
    }

    public void setVendor_price_unconfirmed(Float vendor_price_unconfirmed) {
        this.vendor_price_unconfirmed = vendor_price_unconfirmed;
    }

    public Long getCdrId() {
        return cdrId;
    }

    public void setCdrId(Long cdrId) {
        this.cdrId = cdrId;
    }
}
