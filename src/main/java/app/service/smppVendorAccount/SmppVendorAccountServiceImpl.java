package app.service.smppVendorAccount;

import app.entity.*;
import app.entity.enums.BindType;
import app.entity.enums.DataCoding;
import app.entity.enums.NPI;
import app.entity.enums.TON;
import app.entityXML.vendorAccountWithPrices.VendorAccountWithPrices;
import app.repository.SmppVendorAccountRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import app.service.clientRatesCurrent.ClientRatesCurrentService;
import app.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class SmppVendorAccountServiceImpl implements SmppVendorAccountService{
    @Autowired
    SmppVendorAccountRepository smppVendorAccountRepository;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ClientRatesCurrentService clientRatesCurrentService;

    @Override
    public boolean isName(Long id, String name, Customer customer) {
        return smppVendorAccountRepository.findByNameExceptId(name, id, customer).size() != 0;
    }

    @Override
    public boolean isTag(Long id, Integer tag) {
        return smppVendorAccountRepository.findByTagExceptId(tag, id).size() != 0;
    }

    @Override
    public List<SmppVendorAccount> getSmppVendorAccountAll() {
        return smppVendorAccountRepository.findAll();
    }

    @Override
    public SmppVendorAccount getSmppVendorAccountById(Long id) {
        return smppVendorAccountRepository.findOne(id);
    }

    @Override
    public SmppVendorAccount getSmppVendorAccountByName(String name) {
        return smppVendorAccountRepository.findByName(name);
    }

    @Override
    public List<SmppVendorAccount> getSmppVendorAccountByCustomerSort(Customer customer) {
        return smppVendorAccountRepository.findByCustomerSort(customer);
    }

    public Integer getLastVendorTag() {
        Integer returnValue = 1000;
        List<SmppVendorAccount> listVendorTags = entityManager.createQuery("SELECT c FROM SmppVendorAccount c WHERE NOT (c.tag is NULL) ORDER BY c.tag DESC").setMaxResults(1).getResultList();
        List<VendorDialpeer> listDialpeerTags = entityManager.createQuery("SELECT c FROM VendorDialpeer c WHERE NOT (c.tag is NULL) ORDER BY c.tag DESC").setMaxResults(1).getResultList();
        Integer vendorTag = 0;
        Integer dialpeerTag = 0;

        if (listVendorTags.size() > 0) {
            vendorTag = listVendorTags.get(0).getTag();
        }
        if (listDialpeerTags.size() > 0) {
            dialpeerTag = listDialpeerTags.get(0).getTag();
        }

        if (vendorTag != 0 || dialpeerTag != 0) {
            if (vendorTag > dialpeerTag) {
                returnValue = vendorTag;
            } else {
                returnValue = dialpeerTag;
            }
        }

        return returnValue;
    }

    @Override
    public List<SmppVendorAccount> getSmppVendorAccountAllSort() {
        return smppVendorAccountRepository.findAllSort();
    }

    @Override
    @Transactional
    public SmppVendorAccount addSmppVendorAccount(String name, Boolean dont_create_dp, Customer customer, Integer tag, Boolean ripf, Integer con_fail_delay, Integer dlr_expiry, DataCoding coding, Integer elink_interval, Integer bind_to, Boolean con_fail_retry, NPI bind_npi, TON bind_ton, NPI dst_npi, TON dst_ton, NPI src_npi, TON src_ton, Integer res_to, Integer def_msg_id, Integer priority, Boolean con_loss_retry, Integer con_loss_delay, Integer requeue_delay, Integer trx_to, Boolean ssl, BindType bind, boolean dontSync) {
        if (name.equals("") || smppVendorAccountRepository.findByNameAndCustomer(name, customer) != null) {
            return null;
        }
        if (tag != null && smppVendorAccountRepository.findByTag(tag) != null){
            return null;
        }
        SmppVendorAccount smppVendorAccount = new SmppVendorAccount(name, dont_create_dp, customer, tag, ripf, con_fail_delay, dlr_expiry, coding, elink_interval, bind_to, con_fail_retry, bind_npi, bind_ton, dst_npi, dst_ton, src_npi, src_ton, res_to, def_msg_id, priority, con_loss_retry, con_loss_delay, requeue_delay, trx_to, ssl, bind);
        smppVendorAccount.setDontSync(dontSync);
        smppVendorAccountRepository.save(smppVendorAccount);
        changesRegisteryService.registerChangesAllSoftswitches("smppvendoraccount" + "_" + smppVendorAccount.getId());
        return smppVendorAccount;
    }

    @Override
    @Transactional
    public SmppVendorAccount updateSmppVendorAccount(Long id,
                                                     String name,
                                                     Boolean dont_create_dp,
                                                     Customer customer,
                                                     Integer tag,
                                                     Boolean ripf,
                                                     Integer con_fail_delay,
                                                     Integer dlr_expiry,
                                                     DataCoding coding,
                                                     Integer elink_interval,
                                                     Integer bind_to,
                                                     Boolean con_fail_retry,
                                                     NPI bind_npi,
                                                     TON bind_ton,
                                                     NPI dst_npi,
                                                     TON dst_ton,
                                                     NPI src_npi,
                                                     TON src_ton,
                                                     Integer res_to,
                                                     Integer def_msg_id,
                                                     Integer priority,
                                                     Boolean con_loss_retry,
                                                     Integer con_loss_delay,
                                                     Integer requeue_delay,
                                                     Integer trx_to,
                                                     Boolean ssl,
                                                     BindType bind,
                                                     boolean dontSync) {
        if (!name.equals("") && smppVendorAccountRepository.findByNameExceptId(name, id, customer).size() == 0){
            if (tag == null || smppVendorAccountRepository.findByTagExceptId(tag, id).size() == 0){
                SmppVendorAccount smppVendorAccount = smppVendorAccountRepository.findOne(id);
                smppVendorAccount.setName(name);
                smppVendorAccount.setDont_create_dp(dont_create_dp);
                smppVendorAccount.setCustomer(customer);
                smppVendorAccount.setTag(tag);
                smppVendorAccount.setRipf(ripf);
                smppVendorAccount.setCon_fail_delay(con_fail_delay);
                smppVendorAccount.setDlr_expiry(dlr_expiry);
                smppVendorAccount.setCoding(coding);
                smppVendorAccount.setElink_interval(elink_interval);
                smppVendorAccount.setBind_to(bind_to);
                smppVendorAccount.setCon_fail_retry(con_fail_retry);
                smppVendorAccount.setBind_npi(bind_npi);
                smppVendorAccount.setBind_ton(bind_ton);
                smppVendorAccount.setDst_npi(dst_npi);
                smppVendorAccount.setDst_ton(dst_ton);
                smppVendorAccount.setSrc_npi(src_npi);
                smppVendorAccount.setSrc_ton(src_ton);
                smppVendorAccount.setRes_to(res_to);
                smppVendorAccount.setDef_msg_id(def_msg_id);
                smppVendorAccount.setPriority(priority);
                smppVendorAccount.setCon_loss_retry(con_loss_retry);
                smppVendorAccount.setCon_loss_delay(con_loss_delay);
                smppVendorAccount.setRequeue_delay(requeue_delay);
                smppVendorAccount.setTrx_to(trx_to);
                smppVendorAccount.setSsl(ssl);
                smppVendorAccount.setBind(bind);
                smppVendorAccount.setDontSync(dontSync);
                smppVendorAccountRepository.save(smppVendorAccount);
                changesRegisteryService.registerChangesAllSoftswitches("smppvendoraccount" + "_" + smppVendorAccount.getId());
                return smppVendorAccount;
            }
        }
        return null;
    }

    @Override
    public void UpdateTag(SmppVendorAccount account, Integer newTag) {
        account.setTag(newTag);
        smppVendorAccountRepository.save(account);
    }

    @Override
    public SmppVendorAccount getAccountGyTag(Integer tag) {
        SmppVendorAccount returnAcc = smppVendorAccountRepository.getAccountByTag(tag);
        return returnAcc;
    }

}
