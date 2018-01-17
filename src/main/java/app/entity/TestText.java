package app.entity;

import javax.persistence.*;

/**
 * Created by Олег on 20.12.2017.
 */

@Entity
@Table(name = "test_texts")
public class TestText {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "source_addr")
    private String sourceAddr;

    public TestText() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceAddr() {
        return sourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        this.sourceAddr = sourceAddr;
    }
}
