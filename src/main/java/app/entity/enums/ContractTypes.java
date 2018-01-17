package app.entity.enums;

/**
 * Created by Олег on 11.12.2017.
 */
public enum ContractTypes {
    Bilateral, Unilateral, NotSelected;


    @Override
    public String toString() {
        String returnString = "";
        if (this == ContractTypes.NotSelected) {
            returnString = "Not selected";
        }
        if (this == ContractTypes.Bilateral) {
            returnString = "Bilateral";
        }
        if (this == ContractTypes.Unilateral) {
            returnString = "Unilateral";
        }
        return returnString;
    }
}