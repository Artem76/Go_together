package app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Олег on 08.11.2017.
 */

@Entity
@Table(name = "sms_incoming_invoices")
public class IncomingInvoice {
    @Id
    @GeneratedValue
    private long id;

    private Date date;

    private Date startDate;

    private Date endDate;

    private Date payDate;

    private Long customer_id;

    private Long billingPeriodId;

    private Double invoiceSum;

    private Double invoiceConfirmedSum;

    private Boolean confirmed;

    public IncomingInvoice() {

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getBillingPeriodId() {
        return billingPeriodId;
    }

    public void setBillingPeriodId(Long billingPeriodId) {
        this.billingPeriodId = billingPeriodId;
    }

    public Double getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(Double invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Double getInvoiceConfirmedSum() {
        return invoiceConfirmedSum;
    }

    public void setInvoiceConfirmedSum(Double invoiceConfirmedSum) {
        this.invoiceConfirmedSum = invoiceConfirmedSum;
    }
}
