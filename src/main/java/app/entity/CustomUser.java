package app.entity;

import app.entity.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class CustomUser{
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "login")
    private String login;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "customers")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Customer> customers;

    @Column(name = "client_rates_updates")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<ClientRatesUpdate> clientRatesUpdates = new ArrayList<>();

    @Column(name = "vendor_rates_updates")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<VendorRatesUpdate> vendorRatesUpdates = new ArrayList<>();

    public CustomUser(String name, String login, String password, String phone, String email, UserRole role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public CustomUser() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<ClientRatesUpdate> getClientRatesUpdates() {
        return clientRatesUpdates;
    }

    public void setClientRatesUpdates(List<ClientRatesUpdate> clientRatesUpdates) {
        this.clientRatesUpdates = clientRatesUpdates;
    }

    public void addClientRatesUpdate(ClientRatesUpdate clientRatesUpdate){
        this.clientRatesUpdates.add(clientRatesUpdate);
    }

    public List<VendorRatesUpdate> getVendorRatesUpdates() {
        return vendorRatesUpdates;
    }

    public void setVendorRatesUpdates(List<VendorRatesUpdate> vendorRatesUpdates) {
        this.vendorRatesUpdates = vendorRatesUpdates;
    }

    public void addVendorRatesUpdate(VendorRatesUpdate vendorRatesUpdate){
        this.vendorRatesUpdates.add(vendorRatesUpdate);
    }

    @Override
    public String toString() {
        return name;
    }
}
