package app.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
@Table(name = "smpp_client_accounts")
public class SmppClientAccount {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "system_type", length = 20)
    private String systemType;

    @Column(name = "submit_throughput")
    private Long submitThroughput = 0L;

    @Column(name = "dont_sync")
    private Boolean dontSync = false;

    @Column(name = "smpp_client_ips_list")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smppClientAccount")
    private List<SmppClientIps> smppClientIpsList = new ArrayList<>();

    @Column(name = "smpp_client_system_id")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smppClientAccount")
    private List<SmppClientSystemId> smppClientSystemIdList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "client_rates_updates")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "smppClientAccount")
    private List<ClientRatesUpdate> clientRatesUpdates = new ArrayList<>();

    @Column(name = "client_rates_histories")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "smppClientAccount")
    private List<ClientRatesHistory> clientRatesHistories = new ArrayList<>();

    @Column(name = "client_rates_currents")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "smppClientAccount")
    private List<ClientRatesCurrent> clientRatesCurrents = new ArrayList<>();

    public SmppClientAccount(String name, Customer customer) {
        this.name = name;
        this.customer = customer;
    }

    public SmppClientAccount() {
    }

    public SmppClientAccount(String name, String systemType, Long submitThroughput, Customer customer) {
        this.name = name;
        this.systemType = systemType;
        this.submitThroughput = submitThroughput;
        this.customer = customer;
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

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public Long getSubmitThroughput() {
        return submitThroughput;
    }

    public void setSubmitThroughput(Long submitThroughput) {
        this.submitThroughput = submitThroughput;
    }

    public List<SmppClientIps> getSmppClientIpsList() {
        return smppClientIpsList;
    }

    public void setSmppClientIpsList(List<SmppClientIps> smppClientIpsList) {
        this.smppClientIpsList = smppClientIpsList;
    }

    public List<SmppClientSystemId> getSmppClientSystemIdList() {
        return smppClientSystemIdList;
    }

    public void setSmppClientSystemId(List<SmppClientSystemId> smppClientSystemIdList) {
        this.smppClientSystemIdList = smppClientSystemIdList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSmppClientSystemIdList(List<SmppClientSystemId> smppClientSystemIdList) {
        this.smppClientSystemIdList = smppClientSystemIdList;
    }

    public void addSmppClientSystemId(SmppClientSystemId smppClientSystemId){
        this.smppClientSystemIdList.add(smppClientSystemId);
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

    public List<ClientRatesHistory> getClientRatesHistories() {
        return clientRatesHistories;
    }

    public void setClientRatesHistories(List<ClientRatesHistory> clientRatesHistories) {
        this.clientRatesHistories = clientRatesHistories;
    }

    public void addClientRatesHistory(ClientRatesHistory clientRatesHistory){
        this.clientRatesHistories.add(clientRatesHistory);
    }

    public void deleteClientRatesHistory(ClientRatesHistory clientRatesHistory){
        this.clientRatesHistories.remove(clientRatesHistory);
    }

    public List<ClientRatesCurrent> getClientRatesCurrents() {
        return clientRatesCurrents;
    }

    public void setClientRatesCurrents(List<ClientRatesCurrent> clientRatesCurrents) {
        this.clientRatesCurrents = clientRatesCurrents;
    }

    public void addClientRatesCurrent(ClientRatesCurrent clientRatesCurrent){
        this.clientRatesCurrents.add(clientRatesCurrent);
    }

    public Boolean getDontSync() {
        return dontSync;
    }

    public void setDontSync(Boolean dontSync) {
        this.dontSync = dontSync;
    }
}
