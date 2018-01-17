package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 07.08.2017.
 */

@Entity
@Table(name = "client_rates_current", indexes = {
        @Index(columnList = "smppClientAccount_id, mcc, mnc, rate", name = "client_rates_current_idx")})
public class ClientRatesCurrent {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppClientAccount_id")
    private SmppClientAccount smppClientAccount;

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 3)
    private String mnc;

    @Column(name = "rate")
    private Float rate;

    public ClientRatesCurrent() {

    }

    public ClientRatesCurrent(SmppClientAccount smppClientAccount, String mcc, String mnc, Float rate) {
        this.smppClientAccount = smppClientAccount;
        this.mcc = mcc;
        this.mnc = mnc;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SmppClientAccount getSmppClientAccount() {
        return smppClientAccount;
    }

    public void setSmppClientAccount(SmppClientAccount smppClientAccount) {
        this.smppClientAccount = smppClientAccount;
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

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
