package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "smpp_client_ips")
public class SmppClientIps {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "ip", length = 15)
    private String ip;

    @NotNull
    @Column(name = "allowed")
    private Boolean allowed = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppClientAccount_id", nullable = false)
    private SmppClientAccount smppClientAccount;

    public SmppClientIps(String ip, Boolean allowed, SmppClientAccount smppClientAccount) {
        this.ip = ip;
        this.allowed = allowed;
        this.smppClientAccount = smppClientAccount;
    }

    public SmppClientIps() {
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

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public SmppClientAccount getSmppClientAccount() {
        return smppClientAccount;
    }

    public void setSmppClientAccount(SmppClientAccount smppClientAccount) {
        this.smppClientAccount = smppClientAccount;
    }
}
