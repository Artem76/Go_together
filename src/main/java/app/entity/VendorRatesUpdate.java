package app.entity;

/**
 * Created by АРТЕМ on 28.08.2017.
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vendor_rates_update")
public class VendorRatesUpdate {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "smppVendorIps_id")
    private SmppVendorIps smppVendorIps;

    @Column(name = "vendor_rates_histories")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "vendorRatesUpdate")
    private List<VendorRatesHistory> vendorRatesHistories = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    private EmailContent emailContent;

    public VendorRatesUpdate() {
    }

    public VendorRatesUpdate(CustomUser user, Date date, SmppVendorIps smppVendorIps, List<VendorRatesHistory> vendorRatesHistories) {
        this.user = user;
        this.date = date;
        this.smppVendorIps = smppVendorIps;
        this.vendorRatesHistories = vendorRatesHistories;
    }

    public VendorRatesUpdate(CustomUser user, Date date, SmppVendorIps smppVendorIps) {
        this.user = user;
        this.date = date;
        this.smppVendorIps = smppVendorIps;
    }

    public VendorRatesUpdate(CustomUser user, Date date, SmppVendorIps smppVendorIps, EmailContent emailContent) {
        this.user = user;
        this.date = date;
        this.smppVendorIps = smppVendorIps;
        this.emailContent = emailContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SmppVendorIps getSmppVendorIps() {
        return smppVendorIps;
    }

    public void setSmppVendorIps(SmppVendorIps smppVendorIps) {
        this.smppVendorIps = smppVendorIps;
    }

    public List<VendorRatesHistory> getVendorRatesHistories() {
        return vendorRatesHistories;
    }

    public void setVendorRatesHistories(List<VendorRatesHistory> vendorRatesHistories) {
        this.vendorRatesHistories = vendorRatesHistories;
    }

    public void addVendorRatesHistory(VendorRatesHistory vendorRatesHistory) {
        this.vendorRatesHistories.add(vendorRatesHistory);
    }

    public void deleteVendorRatesHistory(VendorRatesHistory vendorRatesHistory){
        this.vendorRatesHistories.remove(vendorRatesHistory);
    }

    public EmailContent getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(EmailContent emailContent) {
        this.emailContent = emailContent;
    }
}
