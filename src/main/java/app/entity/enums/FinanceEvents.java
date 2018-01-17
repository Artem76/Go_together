package app.entity.enums;

/**
 * Created by Олег on 03.11.2017.
 */
public enum FinanceEvents {
    Current_Traffic, Incoming_Invoice, Outgoing_Invoice, Incoming_Payment, Outgoing_Payment;

    @Override
    public String toString() {
        String returnString = "";
        if (this.equals(FinanceEvents.Current_Traffic)) {
            returnString = "Current period traffic";
        }
        if (this.equals(FinanceEvents.Incoming_Invoice)) {
            returnString = "Incoming unconfirmed invoice";
        }
        if (this.equals(FinanceEvents.Outgoing_Invoice)) {
            returnString = "Outgoing unconfirmed invoice";
        }
        if (this.equals(FinanceEvents.Incoming_Payment)) {
            returnString = "Incoming payment";
        }
        if (this.equals(FinanceEvents.Outgoing_Payment)) {
            returnString = "Outgoing payment";
        }
        return returnString;
    }
}
