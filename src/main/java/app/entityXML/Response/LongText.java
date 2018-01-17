package app.entityXML.Response;

/**
 * Created by Олег on 30.11.2017.
 */
public class LongText {
    private Long id;

    private String text;

    public LongText() {

    }

    public LongText(Long id, String text) {
        this.id = id;
        this.text = text;
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
}
