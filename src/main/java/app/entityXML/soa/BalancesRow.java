package app.entityXML.soa;

/**
 * Created by Олег on 06.11.2017.
 */
public class BalancesRow {

    private String customerName;

    private Double balance;

    public BalancesRow() {

    }

    public BalancesRow(String customerName, Double balance) {
        this.customerName = customerName;
        this.balance = balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
