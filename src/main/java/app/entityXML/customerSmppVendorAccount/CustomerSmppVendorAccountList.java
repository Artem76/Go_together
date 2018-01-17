package app.entityXML.customerSmppVendorAccount;

import app.entity.VendorDialpeer;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Олег on 25.08.2017.
 */

@XmlRootElement(name = "CustomerSmppVendorAccountList")
public class CustomerSmppVendorAccountList {
    List<CustomerSmppVendorAccount> customerSmppVendorAccountList;

    public CustomerSmppVendorAccountList(List<CustomerSmppVendorAccount> customerSmppVendorAccountList) {
        this.customerSmppVendorAccountList = customerSmppVendorAccountList;
    }

    public List<CustomerSmppVendorAccount> getCustomerSmppVendorAccountList() {
        return customerSmppVendorAccountList;
    }

    public void setCustomerSmppVendorAccountList(List<CustomerSmppVendorAccount> customerSmppVendorAccountList) {
        this.customerSmppVendorAccountList = customerSmppVendorAccountList;
    }
}
