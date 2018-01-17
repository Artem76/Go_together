package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 09.12.2017.
 */

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "additional_field")
    private String additionalField;

    public Settings(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Settings() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(String additionalField) {
        this.additionalField = additionalField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
