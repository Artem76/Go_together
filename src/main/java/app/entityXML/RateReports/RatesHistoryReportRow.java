package app.entityXML.RateReports;

import java.util.Date;

/**
 * Created by Олег on 02.12.2017.
 */
public class RatesHistoryReportRow {

    private String country;

    private String network;

    private String mcc;

    private String mnc;

    private Date effectiveDate;

    private float rate;

    private Long updateId;

    public RatesHistoryReportRow(String country, String network, String mcc, String mnc, Date effectiveDate, float rate, Long updateId) {
        this.country = country;
        this.network = network;
        this.mcc = mcc;
        this.mnc = mnc;
        this.effectiveDate = effectiveDate;
        this.rate = rate;
        this.updateId = updateId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
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

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }
}
