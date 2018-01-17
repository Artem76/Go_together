package app.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Олег on 07.08.2017.
 */

@Entity
@Table(name = "vendor_rates_history", indexes = {
        @Index(columnList = "date, smppVendorIps_id, mcc, mnc, rate", name = "vendor_rates_history_idx")})
public class VendorRatesHistory {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppVendorIps_id")
    private SmppVendorIps smppVendorIps;

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 3)
    private String mnc;

    @Column(name = "rate")
    private Float rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorRatesUpdate_id")
    private VendorRatesUpdate vendorRatesUpdate;

    @Column(name = "actualized")
    private Boolean actualized = false;

    public VendorRatesHistory() {

    }

    public VendorRatesHistory(Date date, SmppVendorIps smppVendorIps, String mcc, String mnc, Float rate, VendorRatesUpdate vendorRatesUpdate) {
        this.date = date;
        this.smppVendorIps = smppVendorIps;
        this.mcc = mcc;
        this.mnc = mnc;
        this.rate = rate;
        this.vendorRatesUpdate = vendorRatesUpdate;
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

    public SmppVendorIps getSmppVendorIps() {
        return smppVendorIps;
    }

    public void setSmppVendorIps(SmppVendorIps smppVendorIps) {
        this.smppVendorIps = smppVendorIps;
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

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public VendorRatesUpdate getVendorRatesUpdate() {
        return vendorRatesUpdate;
    }

    public void setVendorRatesUpdate(VendorRatesUpdate vendorRatesUpdate) {
        this.vendorRatesUpdate = vendorRatesUpdate;
    }

    public Boolean getActualized() {
        return actualized;
    }

    public void setActualized(Boolean actualized) {
        this.actualized = actualized;
    }
}
