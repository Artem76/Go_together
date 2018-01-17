package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
@Entity
@Table(name = "vendor_dialpeer_child")
public class VendorDialpeerChild {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppVendorIps_id")
    private SmppVendorIps smppVendorIps;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorDialpeer_id")
    private VendorDialpeer vendorDialpeer;

    public VendorDialpeerChild() {
    }

    public VendorDialpeerChild(SmppVendorIps smppVendorIps, Integer weight, VendorDialpeer vendorDialpeer) {
        this.smppVendorIps = smppVendorIps;
        this.weight = weight;
        this.vendorDialpeer = vendorDialpeer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SmppVendorIps getSmppVendorIps() {
        return smppVendorIps;
    }

    public void setSmppVendorIps(SmppVendorIps smppVendorIps) {
        this.smppVendorIps = smppVendorIps;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public VendorDialpeer getVendorDialpeer() {
        return vendorDialpeer;
    }

    public void setVendorDialpeer(VendorDialpeer vendorDialpeer) {
        this.vendorDialpeer = vendorDialpeer;
    }
}
