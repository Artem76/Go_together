package app.entityXML.RateReports;

import java.util.Date;

/**
 * Created by Олег on 30.11.2017.
 */
public class ActualRatesReportRow {
    private String systemId;

    private String country;

    private String network;

    private String mcc;

    private String mnc;

    private float rate;

    private Date effectiveDate;

    private String rateFuture;

    private String manager;


    public ActualRatesReportRow(String systemId, String country, String network, String mcc, String mnc, float rate, Date effectiveDate, String rateFuture, String manager) {
        this.systemId = systemId;
        this.country = country;
        this.network = network;
        this.mcc = mcc;
        this.mnc = mnc;

        this.rate = rate;
        this.effectiveDate = effectiveDate;
        this.rateFuture = rateFuture;
        this.manager = manager;
    }

    public ActualRatesReportRow() {

    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String sysytemId) {
        this.systemId = sysytemId;
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getRateFuture() {
        return rateFuture;
    }

    public void setRateFuture(String rateFuture) {
        this.rateFuture = rateFuture;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
