package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 07.08.2017.
 */

@Entity
@Table(name = "vendor_rates_current", indexes = {
        @Index(columnList = "smppVendorIps_id, mcc, mnc, rate", name = "vendor_rates_current_idx")})
public class VendorRatesCurrent {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppVendorIps_id")
    private SmppVendorIps smppVendorIps;

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 3)
    private String mnc;

    @Column(name = "rate")
    private Float rate;

    public VendorRatesCurrent() {

    }

    public VendorRatesCurrent(SmppVendorIps smppVendorIps, String mcc, String mnc, Float rate) {
        this.smppVendorIps = smppVendorIps;
        this.mcc = mcc;
        this.mnc = mnc;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
