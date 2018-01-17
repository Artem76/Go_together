package app.entity.enums;

/**
 * Created by Олег on 31.08.2017.
 */
public enum DataCoding {
    SMSC_Default, IA5_ASCII, Octet_unspecified, Latin1, Octet_unspecified_common, JIS, Cyrillic, ISO_8859_8, UCS2, Pictogram, ISO_2022_JP, Extended_Kanji_Jis, KS_C_5601;
    //0=SMSC Default, 1=IA5 ASCII, 2=Octet unspecified, 3=Latin1, 4=Octet unspecified common, 5=JIS, 6=Cyrillic, 7=ISO-8859-8, 8=UCS2, 9=Pictogram, 10=ISO-2022-JP, 13=Extended Kanji Jis, 14=KS C 5601

    public Integer getDataCodingCode() {
        Integer returnCode = 0;
        if (name().equals("SMSC_Default")) {
            returnCode = 0;
        }
        if (name().equals("IA5_ASCII")) {
            returnCode =  1;
        }
        if (name().equals("Octet_unspecified")) {
            returnCode =  2;
        }
        if (name().equals("Latin1")) {
            returnCode =  3;
        }
        if (name().equals("Octet_unspecified_common")) {
            returnCode =  4;
        }
        if (name().equals("JIS")) {
            returnCode =  5;
        }
        if (name().equals("Cyrillic")) {
            returnCode =  6;
        }
        if (name().equals("ISO_8859_8")) {
            returnCode =  7;
        }
        if (name().equals("UCS2")) {
            returnCode =  8;
        }
        if (name().equals("Pictogram")) {
            returnCode =  9;
        }
        if (name().equals("ISO_2022_JP")) {
            returnCode =  10;
        }
        if (name().equals("Extended_Kanji_Jis")) {
            returnCode =  13;
        }
        if (name().equals("KS_C_5601")) {
            returnCode =  14;
        }
        return returnCode;
    }

    @Override
    public String toString() {
        String returnString = "";
        if (name().equals("SMSC_Default")) {
            returnString = "(" + getDataCodingCode() + ") SMSC Default";
        }
        if (name().equals("IA5_ASCII")) {
            returnString = "(" + getDataCodingCode() + ") IA5 ASCII";
        }
        if (name().equals("Octet_unspecified")) {
            returnString = "(" + getDataCodingCode() + ") Octet unspecified";
        }
        if (name().equals("Latin1")) {
            returnString = "(" + getDataCodingCode() + ") Latin1";
        }
        if (name().equals("Octet_unspecified_common")) {
            returnString = "(" + getDataCodingCode() + ") Octet unspecified common";
        }
        if (name().equals("JIS")) {
            returnString = "(" + getDataCodingCode() + ") JIS";
        }
        if (name().equals("Cyrillic")) {
            returnString = "(" + getDataCodingCode() + ") Cyrillic";
        }
        if (name().equals("ISO_8859_8")) {
            returnString = "(" + getDataCodingCode() + ") ISO-8859-8";
        }
        if (name().equals("UCS2")) {
            returnString = "(" + getDataCodingCode() + ") UCS2";
        }
        if (name().equals("Pictogram")) {
            returnString = "(" + getDataCodingCode() + ") Pictogram";
        }
        if (name().equals("ISO_2022_JP")) {
            returnString = "(" + getDataCodingCode() + ") ISO-2022-JP";
        }
        if (name().equals("Extended_Kanji_Jis")) {
            returnString = "(" + getDataCodingCode() + ") Extended Kanji Jis";
        }
        if (name().equals("KS_C_5601")) {
            returnString = "(" + getDataCodingCode() + ") KS-C-5601";
        }
        return returnString;
    }
}
