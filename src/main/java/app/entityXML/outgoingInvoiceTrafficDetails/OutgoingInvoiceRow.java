package app.entityXML.outgoingInvoiceTrafficDetails;

import java.util.Date;

/**
 * Created by Олег on 07.11.2017.
 */
public class OutgoingInvoiceRow {

    private Long id;

    private String invoiceNumber;

    private String customerName;

    private String date;

    private String billingPeriodName;

    private String startDate;

    private String endDate;

    private String payDate;

    private String sent_to;

    private Double sum;

    public OutgoingInvoiceRow(Long id, String invoiceNumber, String customerName, String date, String billingPeriodName, String startDate, String endDate, String payDate, String sent_to, Double sum) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.date = date;
        this.billingPeriodName = billingPeriodName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payDate = payDate;
        this.sent_to = sent_to;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillingPeriodName() {
        return billingPeriodName;
    }

    public void setBillingPeriodName(String billingPeriodName) {
        this.billingPeriodName = billingPeriodName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getSent_to() {
        return sent_to;
    }

    public void setSent_to(String sent_to) {
        this.sent_to = sent_to;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

}
