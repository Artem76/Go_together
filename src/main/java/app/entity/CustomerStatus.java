package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "customer_statuses")
public class CustomerStatus {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "invisible")
    private Boolean invisible = false;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "customers")
    private List<Customer> customers;

    public CustomerStatus() {
    }

    public CustomerStatus(String name, Boolean invisible) {
        this.name = name;
        this.invisible = invisible;
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

    public Boolean getInvisible() {
        return invisible;
    }

    public void setInvisible(Boolean invisible) {
        this.invisible = invisible;
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
