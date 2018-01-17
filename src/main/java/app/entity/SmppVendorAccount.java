package app.entity;

import app.entity.enums.BindType;
import app.entity.enums.DataCoding;
import app.entity.enums.NPI;
import app.entity.enums.TON;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "smpp_vendor_accounts")
public class SmppVendorAccount {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private long id;

    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "dont_create_dp")
    private Boolean dont_create_dp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private Boolean dontSync = false;

    //////////////////////////////////////Addition settings
    @Column(name = "tag")
    private Integer tag;

    @Column(name = "ripf")
    private Boolean ripf = false; // Заголовок "Replace if present flag" Значения "Do not replace" - false, "Replace" - true

    @Column(name = "con_fail_delay")
    private Integer con_fail_delay = 10; //Заголовок "Reconnect delay (sec)". Просто инпут integer

    @Column(name = "dlr_expiry")
    private Integer dlr_expiry = 86400; //Заголовок "DLR expiry period (sec)". Просто инпут integer

    @Column(name = "coding")
    private DataCoding coding = DataCoding.SMSC_Default; //Заголовок "Default data coding". Select всё найдешь в енуме

    @Column(name = "elink_interval")
    private Integer elink_interval = 30; //Заголовок "Enquire link interval (sec)". Просто инпут integer

    @Column(name = "bind_to")
    private Integer bind_to = 30; //Заголовок "Bind request response timeout (sec)". Просто инпут integer

    @Column(name = "con_fail_retry")
    private Boolean con_fail_retry = true; //Заголовок "Reconnect on connection failure". Select Yes, No



    @Column(name = "bind_npi")
    private NPI bind_npi = NPI.ISDN; //Заголовок "Bind NPI". Select всё найдешь в енуме

    @Column(name = "bind_ton")
    private TON bind_ton = TON.Unknown; //Заголовок "Bind TON". Select всё найдешь в енуме

    @Column(name = "dst_npi")
    private NPI dst_npi = NPI.ISDN; //Заголовок "Destination NPI". Select всё найдешь в енуме

    @Column(name = "dst_ton")
    private TON dst_ton = TON.Unknown; //Заголовок "Destination TON". Select всё найдешь в енуме

    @Column(name = "src_npi")
    private NPI src_npi = NPI.ISDN; //Заголовок "Source NPI". Select всё найдешь в енуме

    @Column(name = "src_ton")
    private TON src_ton = TON.National; //Заголовок "Source TON". Select всё найдешь в енуме



    @Column(name = "res_to")
    private Integer res_to = 120; //Заголовок "PDU response timeout (sec)". Просто инпут integer

    @Column(name = "def_msg_id")
    private Integer def_msg_id = 0; //Заголовок "Pre-defined message SMSC index". Просто инпут integer

    @Column(name = "priority")
    private Integer priority = 0; //Заголовок "Message priority". select [0,1,2,3]

    @Column(name = "con_loss_retry")
    private Boolean con_loss_retry = true; //Заголовок "Reconnect on connection loss". select Yes No

    @Column(name = "con_loss_delay")
    private Integer con_loss_delay = 10; //Заголовок "Reconnect delay on connection loss". select

    @Column(name = "requeue_delay")
    private Integer requeue_delay = 120; //Заголовок "Requeue delay". input integer

    @Column(name = "trx_to")
    private Integer trx_to = 300; //Заголовок "trx_to". input integer

    @Column(name = "ssl")
    private Boolean ssl = false; //Заголовок "SSL". select Yes No

    @Column(name = "bind")
    private BindType bind = BindType.BIND_TRX; //Заголовок "Bind type". select


    ///////////////////////

    @Column(name = "smpp_vendor_ips_list")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "smppVendorAccount")
    private List<SmppVendorIps> smppVendorIpsList;

    public SmppVendorAccount() {
    }

    public SmppVendorAccount(String name, Customer customer, Boolean dont_create_dp) {
        this.name = name;
        this.customer = customer;
        this.dont_create_dp = dont_create_dp;
    }

    public SmppVendorAccount(String name, Integer tag, Customer customer, Boolean dont_create_dp) {
        this.name = name;
        this.tag = tag;
        this.customer = customer;
        this.dont_create_dp = dont_create_dp;
    }

    public SmppVendorAccount(String name, Boolean dont_create_dp, Customer customer, Integer tag, Boolean ripf, Integer con_fail_delay, Integer dlr_expiry, DataCoding coding, Integer elink_interval, Integer bind_to, Boolean con_fail_retry, NPI bind_npi, TON bind_ton, NPI dst_npi, TON dst_ton, NPI src_npi, TON src_ton, Integer res_to, Integer def_msg_id, Integer priority, Boolean con_loss_retry, Integer con_loss_delay, Integer requeue_delay, Integer trx_to, Boolean ssl, BindType bind) {
        this.name = name;
        this.dont_create_dp = dont_create_dp;
        this.customer = customer;
        this.tag = tag;
        this.ripf = ripf;
        this.con_fail_delay = con_fail_delay;
        this.dlr_expiry = dlr_expiry;
        this.coding = coding;
        this.elink_interval = elink_interval;
        this.bind_to = bind_to;
        this.con_fail_retry = con_fail_retry;
        this.bind_npi = bind_npi;
        this.bind_ton = bind_ton;
        this.dst_npi = dst_npi;
        this.dst_ton = dst_ton;
        this.src_npi = src_npi;
        this.src_ton = src_ton;
        this.res_to = res_to;
        this.def_msg_id = def_msg_id;
        this.priority = priority;
        this.con_loss_retry = con_loss_retry;
        this.con_loss_delay = con_loss_delay;
        this.requeue_delay = requeue_delay;
        this.trx_to = trx_to;
        this.ssl = ssl;
        this.bind = bind;
    }

    public Boolean getDont_create_dp() {
        return dont_create_dp;
    }

    public void setDont_create_dp(Boolean dont_create_dp) {
        this.dont_create_dp = dont_create_dp;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SmppVendorIps> getSmppVendorIpsList() {
        return smppVendorIpsList;
    }

    public void setSmppVendorIpsList(List<SmppVendorIps> smppVendorIpsList) {
        this.smppVendorIpsList = smppVendorIpsList;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Boolean getRipf() {
        return ripf;
    }

    public void setRipf(Boolean ripf) {
        this.ripf = ripf;
    }

    public Integer getCon_fail_delay() {
        return con_fail_delay;
    }

    public void setCon_fail_delay(Integer con_fail_delay) {
        this.con_fail_delay = con_fail_delay;
    }

    public Integer getDlr_expiry() {
        return dlr_expiry;
    }

    public void setDlr_expiry(Integer dlr_expiry) {
        this.dlr_expiry = dlr_expiry;
    }

    public DataCoding getCoding() {
        return coding;
    }

    public void setCoding(DataCoding coding) {
        this.coding = coding;
    }

    public Integer getElink_interval() {
        return elink_interval;
    }

    public void setElink_interval(Integer elink_interval) {
        this.elink_interval = elink_interval;
    }

    public Integer getBind_to() {
        return bind_to;
    }

    public void setBind_to(Integer bind_to) {
        this.bind_to = bind_to;
    }

    public Boolean getCon_fail_retry() {
        return con_fail_retry;
    }

    public void setCon_fail_retry(Boolean con_fail_retry) {
        this.con_fail_retry = con_fail_retry;
    }

    public NPI getBind_npi() {
        return bind_npi;
    }

    public void setBind_npi(NPI bind_npi) {
        this.bind_npi = bind_npi;
    }

    public TON getBind_ton() {
        return bind_ton;
    }

    public void setBind_ton(TON bind_ton) {
        this.bind_ton = bind_ton;
    }

    public NPI getDst_npi() {
        return dst_npi;
    }

    public void setDst_npi(NPI dst_npi) {
        this.dst_npi = dst_npi;
    }

    public TON getDst_ton() {
        return dst_ton;
    }

    public void setDst_ton(TON dst_ton) {
        this.dst_ton = dst_ton;
    }

    public NPI getSrc_npi() {
        return src_npi;
    }

    public void setSrc_npi(NPI src_npi) {
        this.src_npi = src_npi;
    }

    public TON getSrc_ton() {
        return src_ton;
    }

    public void setSrc_ton(TON src_ton) {
        this.src_ton = src_ton;
    }

    public Integer getRes_to() {
        return res_to;
    }

    public void setRes_to(Integer res_to) {
        this.res_to = res_to;
    }

    public Integer getDef_msg_id() {
        return def_msg_id;
    }

    public void setDef_msg_id(Integer def_msg_id) {
        this.def_msg_id = def_msg_id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getCon_loss_retry() {
        return con_loss_retry;
    }

    public void setCon_loss_retry(Boolean con_loss_retry) {
        this.con_loss_retry = con_loss_retry;
    }

    public Integer getCon_loss_delay() {
        return con_loss_delay;
    }

    public void setCon_loss_delay(Integer con_loss_delay) {
        this.con_loss_delay = con_loss_delay;
    }

    public Integer getRequeue_delay() {
        return requeue_delay;
    }

    public void setRequeue_delay(Integer requeue_delay) {
        this.requeue_delay = requeue_delay;
    }

    public Integer getTrx_to() {
        return trx_to;
    }

    public void setTrx_to(Integer trx_to) {
        this.trx_to = trx_to;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public BindType getBind() {
        return bind;
    }

    public void setBind(BindType bind) {
        this.bind = bind;
    }

    public Boolean getDontSync() {
        return dontSync;
    }

    public void setDontSync(Boolean dontSync) {
        this.dontSync = dontSync;
    }
}
