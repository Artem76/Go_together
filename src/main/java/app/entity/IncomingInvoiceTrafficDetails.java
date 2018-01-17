package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 08.11.2017.
 */
@Entity
@Table(name = "sms_incoming_invoice_traffic")
public class IncomingInvoiceTrafficDetails {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "cid")
    private String cid;

    @Column(name = "invoice_id")
    private Long invoice_id;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "mnc")
    private String mnc;

    @Column(name = "count")
    private Long count;

    @Column(name = "sum")
    private Double sum;

    public IncomingInvoiceTrafficDetails() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Long getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Long invoice_id) {
        this.invoice_id = invoice_id;
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
}
