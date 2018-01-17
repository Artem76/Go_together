package app.entityXML.customerSmppVendorAccount;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Олег on 25.08.2017.
 */
@XmlRootElement(name = "CustomerSmppVendorAccount")
public class CustomerSmppVendorAccount {
    private String customer_name;

    private String account_name;

    private String account_id;

    public CustomerSmppVendorAccount() {

    }

    public CustomerSmppVendorAccount(String customer_name, String account_name, String account_id) {
        this.account_id = account_id;
        this.customer_name = customer_name;
        this.account_name = account_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
}
