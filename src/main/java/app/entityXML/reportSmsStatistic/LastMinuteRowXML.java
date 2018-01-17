package app.entityXML.reportSmsStatistic;

/**
 * Created by Олег on 12.10.2017.
 */
public class LastMinuteRowXML {
    private String customerAccount;

    private  String network;

    private  String vendorAccount;

    private Long count;

    private float clientSum;

    private  float vendorSum;

    private  float profit;

    public LastMinuteRowXML(String customerAccount, String network, String vendorAccount, float clientSum, float vendorSum, float profit, Long count) {
        this.customerAccount = customerAccount;
        this.network = network;
        this.vendorAccount = vendorAccount;
        this.clientSum = clientSum;
        this.vendorSum = vendorSum;
        this.profit = profit;
        this.count = count;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getVendorAccount() {
        return vendorAccount;
    }

    public void setVendorAccount(String vendorAccount) {
        this.vendorAccount = vendorAccount;
    }

    public float getClientSum() {
        return clientSum;
    }

    public void setClientSum(float clientSum) {
        this.clientSum = clientSum;
    }

    public float getVendorSum() {
        return vendorSum;
    }

    public void setVendorSum(float vendorSum) {
        this.vendorSum = vendorSum;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
