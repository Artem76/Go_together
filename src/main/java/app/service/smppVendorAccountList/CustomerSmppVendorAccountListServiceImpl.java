package app.service.smppVendorAccountList;

import app.entity.SmppVendorAccount;
import app.entity.VendorDialpeer;
import app.entityXML.customerSmppVendorAccount.CustomerSmppVendorAccount;
import app.entityXML.customerSmppVendorAccount.CustomerSmppVendorAccountList;
import app.repository.SmppVendorAccountRepository;
import app.repository.VendorDialpeerRepository;
import app.service.vendorDialpeer.VendorDialpeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.peer.DialogPeer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олег on 25.08.2017.
 */

@Service
public class CustomerSmppVendorAccountListServiceImpl implements CustomerSmppVendorAccountListService {

    @Autowired
    SmppVendorAccountRepository smppVendorAccountRepository;

    @Autowired
    VendorDialpeerRepository vendorDialpeerRepository;

    @Override
    public CustomerSmppVendorAccountList getFullAccountList() {
        List<CustomerSmppVendorAccount> returnList = new ArrayList<CustomerSmppVendorAccount>();

        List<SmppVendorAccount> vendorAccountList = smppVendorAccountRepository.findAll();
        for (SmppVendorAccount currentAccount : vendorAccountList) {
            if (currentAccount.getDont_create_dp() != null) {
                if (currentAccount.getDont_create_dp()) {
                    continue;
                }
            }
            CustomerSmppVendorAccount newCustomerSmppVendorAccount = new CustomerSmppVendorAccount(currentAccount.getCustomer().getName(), currentAccount.getName(), Long.toString(currentAccount.getId()));
            returnList.add(newCustomerSmppVendorAccount);
        }
        List<VendorDialpeer> dpList = vendorDialpeerRepository.findAll();
        for (VendorDialpeer dp : dpList) {
            CustomerSmppVendorAccount newCustomerSmppVendorAccount = new CustomerSmppVendorAccount(dp.getName(), dp.getName(), "dp_" + dp.getId());
            returnList.add(newCustomerSmppVendorAccount);
        }
        CustomerSmppVendorAccountList newList = new CustomerSmppVendorAccountList(returnList);
        return newList;
    }

}
