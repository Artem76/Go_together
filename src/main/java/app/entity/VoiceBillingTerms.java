package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "voicebillingterms")
public class VoiceBillingTerms {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "billing_days")
    private Integer billingDays;

    @NotNull
    @Column(name = "pay_days")
    private Integer payDays;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "customers")
    private List<Customer> customers;

    public VoiceBillingTerms() {
    }

    public VoiceBillingTerms(String name, Integer billingDays, Integer payDays) {
        this.name = name;
        this.billingDays = billingDays;
        this.payDays = payDays;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBillingDays() {
        return billingDays;
    }

    public void setBillingDays(Integer billingDays) {
        this.billingDays = billingDays;
    }

    public Integer getPayDays() {
        return payDays;
    }

    public void setPayDays(Integer payDays) {
        this.payDays = payDays;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return name;
    }
}
