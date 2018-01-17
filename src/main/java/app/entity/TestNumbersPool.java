package app.entity;

/**
 * Created by Олег on 10.12.2017.
 */

import javax.persistence.*;

@Entity
@Table(name = "test_numbers_pool")
public class TestNumbersPool {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "mnc")
    private String mnc;

    @Column(name = "invalid")
    private Boolean invalid;

    public TestNumbersPool() {

    }

    public TestNumbersPool(String number, String mcc, String mnc, Boolean invalid) {
        this.number = number;
        this.mcc = mcc;
        this.mnc = mnc;
        this.invalid = invalid;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }
}
