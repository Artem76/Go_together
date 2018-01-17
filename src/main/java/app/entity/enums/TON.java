package app.entity.enums;

/**
 * Created by Олег on 31.08.2017.
 */
public enum TON {
    Unknown, International, National, Network_specific, Subscriber_number, Alphanumeric, Abbreviated;
    //0=Unknown, 1=International, 2=National, 3=Network specific, 4=Subscriber number, 5=Alphanumeric, 6=Abbreviated

    public Integer getTONCode() {
        Integer returnCode = 0;
        if (name().equals("Unknown")) {
            returnCode = 0;
        }
        if (name().equals("International")) {
            returnCode = 1;
        }
        if (name().equals("National")) {
            returnCode = 2;
        }
        if (name().equals("Network_specific")) {
            returnCode = 3;
        }
        if (name().equals("Subscriber_number")) {
            returnCode = 4;
        }
        if (name().equals("Alphanumeric")) {
            returnCode = 5;
        }
        if (name().equals("Abbreviated")) {
            returnCode = 6;
        }
        return returnCode;
    }

    @Override
    public String toString() {
        String returnString = "";
        if (name().equals("Unknown") ||
                name().equals("International") ||
                name().equals("National") ||
                name().equals("Alphanumeric") ||
                name().equals("Abbreviated")) {
            returnString = "(" + getTONCode() + ") " + name();
        }
        if (name().equals("Subscriber_number")) {
            returnString = "(" + getTONCode() + ") Subscriber number";
        }
        if (name().equals("Network_specific")) {
            returnString = "(" + getTONCode() + ") Network specific";
        }
        return returnString;
    }
}
