package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 31.07.2017.
 */

@Entity
@Table(name = "changes_registery")
public class ChangesRegistery {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "softswitch_id")
    private Long softswitch_id;

    @Column(name = "values", length = 500)
    private String values;

    public ChangesRegistery() {

    }

    public ChangesRegistery(Long softswitch_id, String values) {
        this.softswitch_id = softswitch_id;
        this.values = values;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoftswitch_id() {
        return softswitch_id;
    }

    public void setSoftswitch_id(Long softswitch_id) {
        this.softswitch_id = softswitch_id;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
