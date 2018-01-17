package app.service.vendorRatesCurrent;

import app.entity.SmppClientAccount;
import app.entity.SmppVendorAccount;
import app.entity.SmppVendorIps;
import app.entity.VendorRatesCurrent;
import app.repository.VendorRatesCurrentRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.smppVendorIps.SmppVendorIpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олег on 07.08.2017.
 */

@Service
public class VendorRatesCurrentServiceImpl implements VendorRatesCurrentService{

    @Autowired
    VendorRatesCurrentRepository vendorRatesCurrentRepository;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SmppVendorAccountService smppVendorAccountService;

    @Autowired
    SmppVendorIpsService smppVendorIpsService;

    @Override
    public VendorRatesCurrent getRate(SmppVendorIps cid, String mcc, String mnc) {
        return vendorRatesCurrentRepository.getRate(cid, mcc, mnc);
    }

    @Override
    public void save(VendorRatesCurrent rate) {
        vendorRatesCurrentRepository.save(rate);
        changesRegisteryService.registerChangesAllSoftswitches("vendorratecurrent_" + rate.getId());
    }

    @Override
    public VendorRatesCurrent getById(Long id) {
        return vendorRatesCurrentRepository.findOne(id);
    }

    @Override
    public List<VendorRatesCurrent> getCurrentRatesByAccountAndCountry(Long account, String mcc) {
        List<VendorRatesCurrent> list = new ArrayList<>();
        List<SmppVendorIps> ipsList = new ArrayList<>();

        if (account != 0) {
            SmppVendorAccount vendorAccount = smppVendorAccountService.getSmppVendorAccountById(account);
            ipsList = vendorAccount.getSmppVendorIpsList();
        } else {
            ipsList = smppVendorIpsService.getSmppVendorIpsAllSort();
        }

        if(account == 0 && mcc.equals("0")) {
            list = entityManager.createQuery("SELECT c FROM VendorRatesCurrent c WHERE c.smppVendorIps IN (:ipsList)").setParameter("ipsList", ipsList).getResultList();
        } else {
            if (account != 0 && !mcc.equals("0")) {
                list = entityManager.createQuery("SELECT c FROM VendorRatesCurrent c WHERE c.smppVendorIps IN (:ipsList) AND c.mcc = :mcc").setParameter("ipsList", ipsList).setParameter("mcc", mcc).getResultList();
            } else {
                if(account == 0) {
                    list = entityManager.createQuery("SELECT c FROM VendorRatesCurrent c WHERE c.smppVendorIps IN (:ipsList) AND c.mcc = :mcc").setParameter("ipsList", ipsList).setParameter("mcc", mcc).getResultList();
                } else {
                    list = entityManager.createQuery("SELECT c FROM VendorRatesCurrent c WHERE c.smppVendorIps IN (:ipsList)").setParameter("ipsList", ipsList).getResultList();
                }
            }
        }

        return list;
    }

}
