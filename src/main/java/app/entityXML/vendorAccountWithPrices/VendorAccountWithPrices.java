package app.entityXML.vendorAccountWithPrices;

/**
 * Created by Олег on 20.12.2017.
 */
public class VendorAccountWithPrices {

    private String accountName;

    private String accountId;

    private float price;

    public VendorAccountWithPrices(String accountName, String accountId, float price) {
        this.accountName = accountName;
        this.accountId = accountId;
        this.price = price;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
