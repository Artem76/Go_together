package app.entityXML.outgoingInvoiceTrafficDetails;

import javax.persistence.Column;

/**
 * Created by Олег on 07.11.2017.
 */
public class OutgoingInvoiceTrafficDetailsRow {

    private String uid;

    private String mcc;

    private String country;

    private String network;

    private String mnc;

    private Long count;

    private Double sum;

    public OutgoingInvoiceTrafficDetailsRow() {

    }

    public OutgoingInvoiceTrafficDetailsRow(String mcc, String mnc, String country, String network, Long count, Double sum, String uid) {
        this.mcc = mcc;
        this.country = country;
        this.network = network;
        this.mnc = mnc;
        this.count = count;
        this.sum = sum;
        this.uid = uid;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
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

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
