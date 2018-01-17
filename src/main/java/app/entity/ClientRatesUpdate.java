package app.entity;

/**
 * Created by АРТЕМ on 28.08.2017.
 */

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "client_rates_update")
public class ClientRatesUpdate {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @Column(name = "date")
    private Date date;

    @Column(name = "text_email", columnDefinition = "text")
    private String textEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smppClientAccount_id")
    private SmppClientAccount smppClientAccount;

    @Column(name = "client_rates_histories")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clientRatesUpdate")
    private Set<ClientRatesHistory> clientRatesHistories = new HashSet<>();

    @Column(name = "email_contents")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "clientRatesUpdate")
    private Set<EmailContent> emailContentList = new HashSet<>();

    public ClientRatesUpdate() {
    }

    public ClientRatesUpdate(CustomUser user, Date date, SmppClientAccount smppClientAccount, Set<ClientRatesHistory> clientRatesHistories) {
        this.user = user;
        this.date = date;
        this.smppClientAccount = smppClientAccount;
        this.clientRatesHistories = clientRatesHistories;
    }

    public ClientRatesUpdate(CustomUser user, Date date, SmppClientAccount smppClientAccount) {
        this.user = user;
        this.date = date;
        this.smppClientAccount = smppClientAccount;
    }

    public ClientRatesUpdate(CustomUser user, Date date, String textEmail, SmppClientAccount smppClientAccount) {
        this.user = user;
        this.date = date;
        this.textEmail = textEmail;
        this.smppClientAccount = smppClientAccount;
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

    public SmppClientAccount getSmppClientAccount() {
        return smppClientAccount;
    }

    public void setSmppClientAccount(SmppClientAccount smppClientAccount) {
        this.smppClientAccount = smppClientAccount;
    }

    public Set<ClientRatesHistory> getClientRatesHistories() {
        return clientRatesHistories;
    }

    public void setClientRatesHistories(Set<ClientRatesHistory> clientRatesHistories) {
        this.clientRatesHistories = clientRatesHistories;
    }

    public void addClientRatesHistory(ClientRatesHistory clientRatesHistory) {
        this.clientRatesHistories.add(clientRatesHistory);
    }

    public void deleteClientRatesHistory(ClientRatesHistory clientRatesHistory){
        this.clientRatesHistories.remove(clientRatesHistory);
    }

    public Set<EmailContent> getEmailContentList() {
        return emailContentList;
    }

    public void setEmailContentList(Set<EmailContent> emailContentList) {
        this.emailContentList = emailContentList;
    }

    public String getTextEmail() {
        return textEmail;
    }

    public void setTextEmail(String textEmail) {
        this.textEmail = textEmail;
    }
}
