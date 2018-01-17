package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "refbook")
public class Refbook {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 3)
    private String mnc;

    @Column(name = "dialcode", length = 12)
    private String dialCode;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "network", length = 100)
    private String network;

    @Column(name = "min_lenght")
    private Integer minLength = 0;

    @Column(name = "max_lenght")
    private Integer maxLength = 0;

    @Column(name = "state")
    private String state;

    @Column(name = "synchronized_softswitch")
    private Boolean synchronized_softswitch = false;

    public Refbook(String mcc, String mnc, String dialCode, String country, String network, Integer minLength, Integer maxLength, String state) {
        this.mcc = mcc;
        this.mnc = mnc;
        this.dialCode = dialCode;
        this.country = country;
        this.network = network;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.state = state;
    }

    public Refbook() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getSynchronized_softswitch() {
        return synchronized_softswitch;
    }

    public void setSynchronized_softswitch(Boolean synchronized_softswitch) {
        this.synchronized_softswitch = synchronized_softswitch;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
