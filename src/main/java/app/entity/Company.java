package app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Олег on 20.10.2017.
 */

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String address;

    private String registration_number;

    private String vat;

    private Long currentBankAccount_id;

    private String documentsPrefix;

    public Company(String name, String address, String registration_number, String vat, Long currentBankAccount_id, String documentsPrefix) {
        this.name = name;
        this.address = address;
        this.registration_number = registration_number;
        this.vat = vat;
        this.currentBankAccount_id = currentBankAccount_id;
        this.documentsPrefix = documentsPrefix;
    }

    public Company() {


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public Long getCurrentBankAccount_id() {
        return currentBankAccount_id;
    }

    public void setCurrentBankAccount_id(Long currentBankAccount_id) {
        this.currentBankAccount_id = currentBankAccount_id;
    }

    public String getDocumentsPrefix() {
        return documentsPrefix;
    }

    public void setDocumentsPrefix(String documentsPrefix) {
        this.documentsPrefix = documentsPrefix;
    }
}
