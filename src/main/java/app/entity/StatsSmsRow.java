package app.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Олег on 10.07.2017.
 */

@Entity
@Table(name = "stats_sms", indexes = {
        @Index(columnList = "created_at, uid, routed_cid, mcc, mnc, status", name = "stats_idx")})
public class StatsSmsRow {

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

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 3)
    private String mnc;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "count")
    private long count;

    @Column(name = "softswitch_id")
    private long softswitch_id = 0;

    @Column(name = "delivery_time", length = 100)
    private Long delivery_time;

    public StatsSmsRow(String uid, String routed_cid, Timestamp created_at, String mcc, String mnc, String status, Long softswitch_id, Long delivery_time) {
        this.uid = uid;
        this.routed_cid = routed_cid;
        this.created_at = created_at;
        this.mcc = mcc;
        this.mnc = mnc;
        this.softswitch_id = softswitch_id;
        this.status = status;
        this.delivery_time = delivery_time;

    }

    public StatsSmsRow () {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getSoftswitch_id() {
        return softswitch_id;
    }

    public void setSoftswitch_id(long softswitch_id) {
        this.softswitch_id = softswitch_id;
    }

    public Long getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Long delivery_time) {
        this.delivery_time = delivery_time;
    }
}
