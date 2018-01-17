package app.service.smppVendorAccountList;

import app.entityXML.customerSmppVendorAccount.CustomerSmppVendorAccount;
import app.entityXML.customerSmppVendorAccount.CustomerSmppVendorAccountList;

import java.util.List;

/**
 * Created by Олег on 25.08.2017.
 */
public interface CustomerSmppVendorAccountListService {
    CustomerSmppVendorAccountList getFullAccountList();

}
