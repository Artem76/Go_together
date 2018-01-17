package app.entity;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Олег on 20.10.2017.
 */

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue
    private long id;

    private String holder;

    private String account_number;

    private String name;

    private String address;

    private String iban;

    private String swift;


    public BankAccount(String holder, String account_number, String name, String address, String iban, String swift) {
        this.holder = holder;
        this.account_number = account_number;
        this.name = name;
        this.address = address;
        this.iban = iban;
        this.swift = swift;
    }

    public BankAccount() {


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }
}
