package app.entity.enums;

/**
 * Created by Олег on 12.10.2017.
 */
public enum MessageIssueType {
    Other, Fake_delivery, Undelivered_valid_number, Delay, Rejected_valid_number, Rejected_submit;

    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}
