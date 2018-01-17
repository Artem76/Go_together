package app.entityXML.IndexReport;

/**
 * Created by Олег on 15.10.2017.
 */
public class ConsumptionRow {

    private String customer;

    private String account;

    private  float Sum;

    public ConsumptionRow(String customer, String account, float sum) {
        this.customer = customer;
        this.account = account;
        Sum = sum;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public float getSum() {
        return Sum;
    }

    public void setSum(float sum) {
        Sum = sum;
    }
}
