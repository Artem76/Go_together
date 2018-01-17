package app.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Олег on 20.12.2017.
 */

@Entity
@Table(name = "basic_tests")
public class BasicTest {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "result")
    private String result;

    @Column(name = "processed")
    private Boolean processed = false;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "source_addr")
    private String sourceAddr;

    @Column(name = "text")
    private String text;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "mnc")
    private String mnc;

    public BasicTest() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
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
}
