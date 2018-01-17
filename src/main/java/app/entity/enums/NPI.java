package app.entity.enums;

/**
 * Created by Олег on 31.08.2017.
 */
public enum NPI {
    Unknown, ISDN, Data, Telex, Land_mobile, National, Private, Ermes, Internet, WAP_Client_ID;
    //0=Unknown, 1=ISDN, 3=Data, 4=Telex, 6=Land mobile, 8=National, 9=Private, 10=Ermes, 14=Internet, 18=WAP Client ID

    public Integer getNPICode() {
        Integer returnCode = 0;
        if (name().equals("Unknown")) {
            returnCode = 0;
        }
        if (name().equals("ISDN")) {
            returnCode = 1;
        }
        if (name().equals("Data")) {
            returnCode = 3;
        }
        if (name().equals("Telex")) {
            returnCode = 4;
        }
        if (name().equals("Land_mobile")) {
            returnCode = 6;
        }
        if (name().equals("National")) {
            returnCode = 8;
        }
        if (name().equals("Private")) {
            returnCode = 9;
        }
        if (name().equals("Ermes")) {
            returnCode = 10;
        }
        if (name().equals("Internet")) {
            returnCode = 14;
        }
        if (name().equals("WAP_Client_ID")) {
            returnCode = 18;
        }
        return returnCode;
    }

    @Override
    public String toString() {
        String returnString = "";
        if (name().equals("Unknown") ||
                name().equals("ISDN") ||
                name().equals("Data") ||
                name().equals("Telex") ||
                name().equals("National") ||
                name().equals("Private") ||
                name().equals("Ermes") ||
                name().equals("Internet")) {
            returnString = "(" + getNPICode() + ") " + name();
        }
        if (name().equals("Land_mobile")) {
            returnString = "(" + getNPICode() + ") Land mobile";
        }
        if (name().equals("WAP_Client_ID")) {
            returnString = "(" + getNPICode() + ") WAP Client ID";
        }
        return returnString;
    }
}
