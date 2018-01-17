package app.service.smppVendorAccount;

import app.entity.Customer;
import app.entity.SmppVendorAccount;
import app.entity.enums.BindType;
import app.entity.enums.DataCoding;
import app.entity.enums.NPI;
import app.entity.enums.TON;
import app.entityXML.vendorAccountWithPrices.VendorAccountWithPrices;

import java.util.List;

public interface SmppVendorAccountService {
    boolean isName(Long id, String name, Customer customer);
    boolean isTag(Long id, Integer tag);
    List<SmppVendorAccount> getSmppVendorAccountAll();
    SmppVendorAccount getSmppVendorAccountById(Long id);
    SmppVendorAccount getSmppVendorAccountByName(String name);
    List<SmppVendorAccount> getSmppVendorAccountByCustomerSort(Customer customer);
    List<SmppVendorAccount> getSmppVendorAccountAllSort();
    SmppVendorAccount addSmppVendorAccount(String name, Boolean dont_create_dp, Customer customer, Integer tag, Boolean ripf, Integer con_fail_delay, Integer dlr_expiry, DataCoding coding, Integer elink_interval, Integer bind_to, Boolean con_fail_retry, NPI bind_npi, TON bind_ton, NPI dst_npi, TON dst_ton, NPI src_npi, TON src_ton, Integer res_to, Integer def_msg_id, Integer priority, Boolean con_loss_retry, Integer con_loss_delay, Integer requeue_delay, Integer trx_to, Boolean ssl, BindType bind, boolean dontSync);
    SmppVendorAccount updateSmppVendorAccount(Long id, String name, Boolean dont_create_dp, Customer customer, Integer tag, Boolean ripf, Integer con_fail_delay, Integer dlr_expiry, DataCoding coding, Integer elink_interval, Integer bind_to, Boolean con_fail_retry, NPI bind_npi, TON bind_ton, NPI dst_npi, TON dst_ton, NPI src_npi, TON src_ton, Integer res_to, Integer def_msg_id, Integer priority, Boolean con_loss_retry, Integer con_loss_delay, Integer requeue_delay, Integer trx_to, Boolean ssl, BindType bind, boolean dontSync);
    Integer getLastVendorTag();
    void UpdateTag(SmppVendorAccount account, Integer newTag);
    SmppVendorAccount getAccountGyTag(Integer tag);


}
