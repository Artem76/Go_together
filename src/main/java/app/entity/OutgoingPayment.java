package app.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Олег on 04.12.2017.
 */

@Entity
@Table(name = "sms_outgoing_payments")
public class OutgoingPayment {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "sum")
    private double sum;

    @Column(name = "date")
    private Date date;


    public OutgoingPayment(Customer customer, double sum, Date date) {
        this.customer = customer;
        this.sum = sum;
        this.date = date;
    }

    public OutgoingPayment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
