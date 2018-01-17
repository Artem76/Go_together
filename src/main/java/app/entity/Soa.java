package app.entity;

import app.entity.enums.DebitCredit;
import app.entity.enums.FinanceEvents;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Олег on 02.11.2017.
 */

@Entity
@Table(name = "soa")
public class Soa {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "customer_id")
    private Long customer_id;

    @Column(name = "billing_terms_id")
    private Long billingTermsId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "pay_date")
    private Date payDate;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "sum_additional")
    private Double sumAdditional;

    @Column(name = "final_sum")
    private Double finalSum;

    @Column(name = "count")
    private Long count;

    @Column(name = "debit_credit")
    private DebitCredit debitCredit;

    @Column(name = "event")
    private FinanceEvents event;

    @Column(name = "event_owner_id")
    private Long eventOwnerId;

    @Column(name = "closed")
    private Boolean closed = false;

    @Column(name = "billing_period_name")
    private String billingPeriodName;

    public Soa() {

    }

    public Soa(Long customer_id, Long billingTermsId, Date startDate, Date endDate, Date payDate, Double sum, Long count, FinanceEvents event, DebitCredit debitCredit) {
        this.customer_id = customer_id;
        this.billingTermsId = billingTermsId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payDate = payDate;
        this.sum = sum;
        this.count = count;
        this.event = event;
        this.debitCredit = debitCredit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getBillingTermsId() {
        return billingTermsId;
    }

    public void setBillingTermsId(Long billingTermsId) {
        this.billingTermsId = billingTermsId;
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

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public DebitCredit getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(DebitCredit debitCredit) {
        this.debitCredit = debitCredit;
    }

    public FinanceEvents getEvent() {
        return event;
    }

    public void setEvent(FinanceEvents event) {
        this.event = event;
    }

    public Long getEventOwnerId() {
        return eventOwnerId;
    }

    public void setEventOwnerId(Long eventOwnerId) {
        this.eventOwnerId = eventOwnerId;
    }

    public Double getFinalSum() {
        return finalSum;
    }

    public void setFinalSum(Double finalSum) {
        this.finalSum = finalSum;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Double getSumAdditional() {
        return sumAdditional;
    }

    public void setSumAdditional(Double sumAdditional) {
        this.sumAdditional = sumAdditional;
    }

    public String getBillingPeriodName() {
        return billingPeriodName;
    }

    public void setBillingPeriodName(String billingPeriodName) {
        this.billingPeriodName = billingPeriodName;
    }
}
