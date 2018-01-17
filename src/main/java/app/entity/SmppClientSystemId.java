package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by АРТЕМ on 28.07.2017.
 */

@Entity
@Table(name = "smpp_client_system_id")
public class SmppClientSystemId {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "system_id", length = 20)
    private String systemId;

    @NotNull
    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "uid", length = 15)
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppClientAccount_id", nullable = false)
    private SmppClientAccount smppClientAccount;

    public SmppClientSystemId() {
    }

    public SmppClientSystemId(String systemId, String password, String uid, SmppClientAccount smppClientAccount) {
        this.systemId = systemId;
        this.password = password;
        this.uid = uid;
        this.smppClientAccount = smppClientAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public SmppClientAccount getSmppClientAccount() {
        return smppClientAccount;
    }

    public void setSmppClientAccount(SmppClientAccount smppClientAccount) {
        this.smppClientAccount = smppClientAccount;
    }
}
