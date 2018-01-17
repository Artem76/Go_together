package app.entityXML.reportSmsStatistic;

/**
 * Created by Олег on 19.10.2017.
 */
public class MessagingFlowRow {
    private String customer;

    private String customerAccount;

    private String country;

    private String network;

    private String sourceAddress;

    private String destinationAddress;

    private String vendor;

    private String vendorAccount;

    private String shortMessage;

    public MessagingFlowRow(String customer, String customerAccount, String country, String network, String sourceAddress, String destinationAddress, String vendor, String vendorAccount, String shortMessage) {
        this.customer = customer;
        this.customerAccount = customerAccount;
        this.country = country;
        this.network = network;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.vendor = vendor;
        this.vendorAccount = vendorAccount;
        this.shortMessage = shortMessage;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendorAccount() {
        return vendorAccount;
    }

    public void setVendorAccount(String vendorAccount) {
        this.vendorAccount = vendorAccount;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }
}
