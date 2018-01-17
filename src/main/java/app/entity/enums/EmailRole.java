package app.entity.enums;

/**
 * Created by АРТЕМ on 11.09.2017.
 */
public enum EmailRole {
    NOC_SMS, NOC_VOIP, Ratemode_SMS, Ratemode_VOIP, Invoice_SMS, User;

    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}

