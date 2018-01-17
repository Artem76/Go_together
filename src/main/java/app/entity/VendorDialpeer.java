package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
@Entity
@Table(name = "vendor_dialpeer")
public class VendorDialpeer {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vendor_dialper_child_list")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendorDialpeer")
    private List<VendorDialpeerChild> vendorDialpeerChildList;

    @Column(name = "tag")
    private Integer tag;

    public VendorDialpeer() {
    }

    public VendorDialpeer(String name) {
        this.name = name;
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

    public List<VendorDialpeerChild> getVendorDialpeerChildList() {
        return vendorDialpeerChildList;
    }

    public void setVendorDialpeerChildList(List<VendorDialpeerChild> vendorDialpeerChildList) {
        this.vendorDialpeerChildList = vendorDialpeerChildList;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
