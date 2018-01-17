package app.entity.enums;

/**
 * Created by Олег on 06.07.2017.
 */
public enum SoftswitchType {
    VoIP, SMS;

    @Override
    public String toString() {
        return name();
    }
}
