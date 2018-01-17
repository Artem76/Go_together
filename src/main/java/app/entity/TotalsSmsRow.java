package app.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Олег on 09.07.2017.
 */

@Entity
@Table(name = "totals_sms", indexes = {
        @Index(columnList = "created_at, uid, client_account_id, vendor_account_id, vendor_account_ip_id, routed_cid, mcc, mnc", name = "totals_idx")})
public class TotalsSmsRow {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "uid", length = 50)
    private String uid;

    @NotNull
    @Column(name = "routed_cid", length = 50)
    private String routed_cid;

    @NotNull
    @Column(name = "created_at")
    private Date created_at;

    @NotNull
    @Column(name = "mcc", length = 3)
    private String mcc;

    @NotNull
    @Column(name = "mnc", length = 3)
    private String mnc;

    @NotNull
    @Column(name = "client_price")
    private double client_price;

    @NotNull
    @Column(name = "vendor_price")
    private double vendor_price;

    @NotNull
    @Column(name = "attempts_count")
    private long attempts_count;

    @NotNull
    @Column(name = "attempts_success")
    private long attempts_success;

    @NotNull
    @Column(name = "registered_delivery")
    private long registered_delivery;

    @Column(name = "client_account_id")
    private long client_account_id = 0;

    @Column(name = "vendor_account_id")
    private long vendor_account_id = 0;

    @Column(name = "vendor_account_ip_id")
    private long vendor_account_ip_id = 0;

    @Column(name = "softswitch_id")
    private long softswitch_id = 0;


    public TotalsSmsRow(String uid, String routed_cid, Date created_at, String mcc, String mnc, double client_price, double vendor_price,
                        long attempts_count, long attempts_success, long registered_delivery, long client_account_id, long vendor_account_id, long vendor_account_ip_id, long softswitch_id) {
        this.uid = uid;
        this.routed_cid = routed_cid;
        this.created_at = created_at;
        this.vendor_price = vendor_price;
        this.client_price = client_price;
        this.mcc = mcc;
        this.mnc = mnc;
        this.attempts_count = attempts_count;
        this.attempts_success = attempts_success;
        this.registered_delivery = registered_delivery;
        this.client_account_id = client_account_id;
        this.vendor_account_id = vendor_account_id;
        this.vendor_account_ip_id = vendor_account_ip_id;
        this.softswitch_id = softswitch_id;

    }

    public TotalsSmsRow(String uid, String routed_cid, Timestamp created_at, String mcc, String mnc, double client_price, double vendor_price,
                        long attempts_count, long attempts_success, long registered_delivery) {
        this.uid = uid;
        this.routed_cid = routed_cid;
        this.created_at = created_at;
        this.vendor_price = vendor_price;
        this.client_price = client_price;
        this.mcc = mcc;
        this.mnc = mnc;
        this.attempts_count = attempts_count;
        this.attempts_success = attempts_success;
        this.registered_delivery = registered_delivery;

    }

    public TotalsSmsRow(String uid, String routed_cid, Timestamp created_at, String mcc, String mnc, double client_price, double vendor_price,
                        long attempts_count, long attempts_success, long registered_delivery, long softswitch_id) {
        this.uid = uid;
        this.routed_cid = routed_cid;
        this.created_at = created_at;
        this.vendor_price = vendor_price;
        this.client_price = client_price;
        this.mcc = mcc;
        this.mnc = mnc;
        this.attempts_count = attempts_count;
        this.attempts_success = attempts_success;
        this.registered_delivery = registered_delivery;
        this.softswitch_id = softswitch_id;

    }

    public TotalsSmsRow() {

    }

    public UUID getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getRoutedCid() {
        return routed_cid;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public String getMcc() {
        return mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public double getClientPrice() {
        return client_price;
    }

    public double getVendorPrice() {
        return vendor_price;
    }

    public long getAttemptsCount() {
        return attempts_count;
    }

    public long getAttemptsSuccess() {
        return attempts_success;
    }

    public long getRegisteredDelivery() {
        return registered_delivery;
    }

    public long getClientAccount_id() {
        return client_account_id;
    }

    public long getVendorAccount_id() {
        return vendor_account_id;
    }

    public long getVendorAccountIpId() {
        return vendor_account_ip_id;
    }

    public long getSoftswitchId() {
        return softswitch_id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRouted_cid(String routed_cid) {
        this.routed_cid = routed_cid;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public void setClient_price(double client_price) {
        this.client_price = client_price;
    }

    public void setVendor_price(double vendor_price) {
        this.vendor_price = vendor_price;
    }

    public void setAttempts_count(long attempts_count) {
        this.attempts_count = attempts_count;
    }

    public void setAttempts_success(long attempts_success) {
        this.attempts_success = attempts_success;
    }

    public void setRegistered_delivery(long registered_delivery) {
        this.registered_delivery = registered_delivery;
    }

    public void setClient_account_id(long client_account_id) {
        this.client_account_id = client_account_id;
    }

    public void setVendor_account_id(long vendor_account_id) {
        this.vendor_account_id = vendor_account_id;
    }

    public void setVendor_account_ip_id(long vendor_account_ip_id) {
        this.vendor_account_ip_id = vendor_account_ip_id;
    }

    public void setSoftswitch_id(long softswitch_id) {
        this.softswitch_id = softswitch_id;
    }
}
