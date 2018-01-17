package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Олег on 05.09.2017.
 */

@Entity
@Table(name = "softswitches_triggers")
public class SoftswitchTriggers {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "softswitch_id")
    private long softswitch_id;

    @NotNull
    @Column(name = "key")
    private String key;

    @NotNull
    @Column(name = "value")
    private Boolean value = false;

    public SoftswitchTriggers() {

    }

    public SoftswitchTriggers(long softswitch_id, String key, Boolean value) {
        this.softswitch_id = softswitch_id;
        this.key = key;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSoftswitch_id() {
        return softswitch_id;
    }

    public void setSoftswitch_id(long softswitch_id) {
        this.softswitch_id = softswitch_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
