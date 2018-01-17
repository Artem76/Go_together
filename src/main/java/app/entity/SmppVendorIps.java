package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "smpp_vendor_ips")
public class SmppVendorIps{
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "ip", length = 15)
    private String ip;

    @Column(name = "port")
    private Integer port;

    @Column(name = "system_id", length = 20)
    private String systemId;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "system_type", length = 20)
    private String systemType = "";

    @Column(name = "submit_throughput")
    private Long submitThroughput = 0L;

    @Column(name = "cid", length = 100)
    private String cid = "";

    @Column(name = "allowed")
    private Boolean allowed = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppVendorAccount_id")
    private SmppVendorAccount smppVendorAccount;

    @Column(name = "vendor_dialpeer_child_list")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smppVendorIps")
    private List<VendorDialpeerChild> vendorDialpeerChildList;

    @Column(name = "synchronized_softswitch")
    private Boolean synchronized_softswitch = false;

    @Column(name = "softswitch_id")
    private Long softswitchId;

    @Column(name = "vendor_rates_updates")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "smppVendorIps")
    private List<VendorRatesUpdate> vendorRatesUpdates = new ArrayList<>();

    @Column(name = "vendor_rates_histories")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "smppVendorIps")
    private List<VendorRatesHistory> vendorRatesHistories = new ArrayList<>();

    @Column(name = "vendor_rates_currents")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "smppVendorIps")
    private List<VendorRatesCurrent> vendorRatesCurrents = new ArrayList<>();

    public SmppVendorIps(String ip, Integer port, String systemId, String password, Boolean allowed, SmppVendorAccount smppVendorAccount) {
        this.ip = ip;
        this.port = port;
        this.systemId = systemId;
        this.password = password;
        this.allowed = allowed;
        this.smppVendorAccount = smppVendorAccount;
    }

    public SmppVendorIps(String ip, Integer port, String systemId, String password, String systemType, Long submitThroughput, Boolean allowed, SmppVendorAccount smppVendorAccount) {
        this.ip = ip;
        this.port = port;
        this.systemId = systemId;
        this.password = password;
        this.systemType = systemType;
        this.submitThroughput = submitThroughput;
        this.allowed = allowed;
        this.smppVendorAccount = smppVendorAccount;
    }

    public SmppVendorIps(String ip, Integer port, String systemId, String password, String systemType, Long submitThroughput, String cid, Boolean allowed, SmppVendorAccount smppVendorAccount, Long softswitchId) {
        this.ip = ip;
        this.port = port;
        this.systemId = systemId;
        this.password = password;
        this.systemType = systemType;
        this.submitThroughput = submitThroughput;
        this.cid = cid;
        this.allowed = allowed;
        this.smppVendorAccount = smppVendorAccount;
        this.softswitchId = softswitchId;
    }

    public SmppVendorIps() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getSubmitThroughput() {
        return submitThroughput;
    }

    public void setSubmitThroughput(Long submitThroughput) {
        this.submitThroughput = submitThroughput;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public SmppVendorAccount getSmppVendorAccount() {
        return smppVendorAccount;
    }

    public void setSmppVendorAccount(SmppVendorAccount smppVendorAccount) {
        this.smppVendorAccount = smppVendorAccount;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public Boolean getSynchronized_softswitch() {
        return synchronized_softswitch;
    }

    public void setSynchronized_softswitch(Boolean synchronized_softswitch) {
        this.synchronized_softswitch = synchronized_softswitch;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<VendorDialpeerChild> getVendorDialpeerChildList() {
        return vendorDialpeerChildList;
    }

    public void setVendorDialpeerChildList(List<VendorDialpeerChild> vendorDialpeerChildList) {
        this.vendorDialpeerChildList = vendorDialpeerChildList;
    }

    public Long getSoftswitchId() {
        return softswitchId;
    }

    public void setSoftswitchId(Long softswitchId) {
        this.softswitchId = softswitchId;
    }

    public List<VendorRatesUpdate> getVendorRatesUpdates() {
        return vendorRatesUpdates;
    }

    public void setVendorRatesUpdates(List<VendorRatesUpdate> vendorRatesUpdates) {
        this.vendorRatesUpdates = vendorRatesUpdates;
    }

    public void addVendorRatesUpdates(VendorRatesUpdate vendorRatesUpdate) {
        this.vendorRatesUpdates.add(vendorRatesUpdate);
    }

    public List<VendorRatesHistory> getVendorRatesHistories() {
        return vendorRatesHistories;
    }

    public void setVendorRatesHistories(List<VendorRatesHistory> vendorRatesHistories) {
        this.vendorRatesHistories = vendorRatesHistories;
    }

    public void addVendorRatesHistory(VendorRatesHistory vendorRatesHistory){
        this.vendorRatesHistories.add(vendorRatesHistory);
    }

    public List<VendorRatesCurrent> getVendorRatesCurrents() {
        return vendorRatesCurrents;
    }

    public void setVendorRatesCurrents(List<VendorRatesCurrent> vendorRatesCurrents) {
        this.vendorRatesCurrents = vendorRatesCurrents;
    }

    public void addVendorRatesCurrent(VendorRatesCurrent vendorRatesCurrent){
        this.vendorRatesCurrents.add(vendorRatesCurrent);
    }
}
