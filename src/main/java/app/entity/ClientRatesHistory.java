package app.entity;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;
import java.util.Date;

/**
 * Created by Олег on 07.08.2017.
 */

@Entity
@Table(name = "client_rates_history", indexes = {
        @Index(columnList = "date, smppClientAccount_id, mcc, mnc, rate", name = "client_rates_history_idx")})
public class ClientRatesHistory {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppClientAccount_id")
    private SmppClientAccount smppClientAccount;

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 3)
    private String mnc;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "applied")
    private Boolean applied = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientRatesUpdate_id")
    private ClientRatesUpdate clientRatesUpdate;

    @Column(name = "actualized")
    private Boolean actualized = false;

    public ClientRatesHistory() {

    }

    public ClientRatesHistory(Date date, SmppClientAccount smppClientAccount, String mcc, String mnc, Float rate, ClientRatesUpdate clientRatesUpdate) {
        this.date = date;
        this.smppClientAccount = smppClientAccount;
        this.mcc = mcc;
        this.mnc = mnc;
        this.rate = rate;
        this.clientRatesUpdate = clientRatesUpdate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public ClientRatesUpdate getClientRatesUpdate() {
        return clientRatesUpdate;
    }

    public void setClientRatesUpdate(ClientRatesUpdate clientRatesUpdate) {
        this.clientRatesUpdate = clientRatesUpdate;
    }

    public Boolean getApplied() {
        return applied;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

    public Boolean getActualized() {
        return actualized;
    }

    public void setActualized(Boolean actualized) {
        this.actualized = actualized;
    }
}
