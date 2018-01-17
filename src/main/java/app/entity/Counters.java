package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 11.12.2017.
 */

@Entity
@Table(name = "counters")
public class Counters {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "key")
    private String key = "";

    @Column(name = "value")
    private Integer value = 0;

    public Counters() {

    }

    public Counters(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
