package app.entityXML.mdrList;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 * Created by АРТЕМ on 09.08.2017.
 */
@XmlRootElement(name = "MdrXml")
public class MdrXml {
    private String source_connector;
    private String msgid;
    private Timestamp created_at;
    private String mcc;
    private String mnc;
    private String uid;
    private String routed_cid;
    private String vendor_msgid;
    private String source_addr;
    private String destination_addr;
    private Integer pdu_count;
    private Integer trials;
    private Timestamp status_at;
    private Boolean registered_delivery;
    private Float client_price;
    private Float vendor_price;
    private String submit_status;
    private String delivery_status;
    private Long delivery_time;
    private String short_message;

    public MdrXml() {
    }

    public MdrXml(String source_connector, String msgid, Timestamp created_at, String mcc, String mnc, String uid, String routed_cid, String vendor_msgid, String source_addr, String destination_addr, Integer pdu_count, Integer trials, Timestamp status_at, Boolean registered_delivery, Float client_price, Float vendor_price, String submit_status, String delivery_status, Long delivery_time, String short_message) {
        this.source_connector = source_connector;
        this.msgid = msgid;
        this.created_at = created_at;
        this.mcc = mcc;
        this.mnc = mnc;
        this.uid = uid;
        this.routed_cid = routed_cid;
        this.vendor_msgid = vendor_msgid;
        this.source_addr = source_addr;
        this.destination_addr = destination_addr;
        this.pdu_count = pdu_count;
        this.trials = trials;
        this.status_at = status_at;
        this.registered_delivery = registered_delivery;
        this.client_price = client_price;
        this.vendor_price = vendor_price;
        this.submit_status = submit_status;
        this.delivery_status = delivery_status;
        this.delivery_time = delivery_time;
        this.short_message = short_message;
    }

    public String getSource_connector() {
        return source_connector;
    }

    public void setSource_connector(String source_connector) {
        this.source_connector = source_connector;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRouted_cid() {
        return routed_cid;
    }

    public void setRouted_cid(String routed_cid) {
        this.routed_cid = routed_cid;
    }

    public String getVendor_msgid() {
        return vendor_msgid;
    }

    public void setVendor_msgid(String vendor_msgid) {
        this.vendor_msgid = vendor_msgid;
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

    public Integer getTrials() {
        return trials;
    }

    public void setTrials(Integer trials) {
        this.trials = trials;
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

    public String getShort_message() {
        return short_message;
    }

    public void setShort_message(String short_message) {
        this.short_message = short_message;
    }
}
