package app.entity;

import javax.persistence.*;

/**
 * Created by АРТЕМ on 07.11.2017.
 */
@Entity
@Table(name = "date_formats")
public class DateFormat {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "format")
    private String format;

    public DateFormat() {
    }

    public DateFormat(String format) {
        this.format = format;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
