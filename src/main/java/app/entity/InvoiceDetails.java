package app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Олег on 20.10.2017.
 */

@Entity
@Table(name = "invoice_details")
public class InvoiceDetails {

    @Id
    @GeneratedValue
    private long id;

    private Date start_period;

    private Date end_period;

    private Long billingTerms_id;

    private String mcc;

    private String mnc;

    private Long count;

    private float price;

    private double sum;


    public InvoiceDetails(Date start_period, Date end_period, Long billingTerms_id, String mcc, String mnc, Long count, float price, double sum) {
        this.start_period = start_period;
        this.end_period = end_period;
        this.billingTerms_id = billingTerms_id;
        this.mcc = mcc;
        this.mnc = mnc;
        this.count = count;
        this.price = price;
        this.sum = sum;
    }

    public Date getStart_period() {
        return start_period;
    }

    public void setStart_period(Date start_period) {
        this.start_period = start_period;
    }

    public Date getEnd_period() {
        return end_period;
    }

    public void setEnd_period(Date end_period) {
        this.end_period = end_period;
    }

    public Long getBillingTerms_id() {
        return billingTerms_id;
    }

    public void setBillingTerms_id(Long billingTerms_id) {
        this.billingTerms_id = billingTerms_id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
