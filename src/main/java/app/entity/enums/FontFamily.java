package app.entity.enums;

/**
 * Created by АРТЕМ on 05.10.2017.
 */
public enum FontFamily {
    Arial, Times_New_Roman, Verdana, Courier_New, Helvetica;

    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}
