package app.service.vendorRatesHistory;

import app.entity.*;
import app.repository.VendorRatesHistoryRepository;
import app.service.refbook.RefbookService;
import app.service.smppVendorIps.SmppVendorIpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Created by Олег on 07.08.2017.
 */
@Service
public class VendorRatesHistoryServiceImpl implements VendorRatesHistoryService {

    @Autowired
    private VendorRatesHistoryRepository vendorRatesHistoryRepository;

    @Autowired
    private SmppVendorIpsService smppVendorIpsService;

    @Autowired
    private RefbookService refbookService;

    @Autowired
    EntityManager entityManager;

    @Override
    public List<VendorRatesHistory> getRateHistoryListBySmppVendorIpsAndMccAndMnc(Long smppVendorIps_id, String mcc, String mnc, Integer page_index, Integer page_size) {
        Pageable pageable = new PageRequest(page_index, page_size);
        List<VendorRatesHistory> vendorRatesHistoryList = null;
        if (smppVendorIps_id == 0l) {
            vendorRatesHistoryList = vendorRatesHistoryRepository.getRatesByMccAndMnc(mcc, mnc, pageable);
        } else {
            vendorRatesHistoryList = vendorRatesHistoryRepository.getRatesBySmppVendorIpsAndMccAndMnc(smppVendorIpsService.getSmppVendorIpsById(smppVendorIps_id), mcc, mnc, pageable);
        }
        return vendorRatesHistoryList;
    }

    @Override
    public List<Map<String, String>> getRateListMapByUpdate(VendorRatesUpdate vendorRatesUpdate) {
        List<VendorRatesHistory> currentList = vendorRatesHistoryRepository.getRatesByUpdate(vendorRatesUpdate);
        List<Map<String, String>> rateListMap = new ArrayList<>();
        for (VendorRatesHistory crh : currentList) {
            Map<String, String> rateMap = new HashMap<>();
            rateMap.put("mcc", crh.getMcc());
            rateMap.put("mnc", crh.getMnc());
            rateMap.put("date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(crh.getDate()));
            rateMap.put("rate", crh.getRate().toString());

            Refbook refbook = refbookService.getRootRefbookByMcc(crh.getMcc());
            if (refbook != null) {
                rateMap.put("country", refbook.getCountry());
            } else {
                rateMap.put("country", "Not found");
            }
            List<Refbook> refbookList = refbookService.getRefbookByMccAndMnc(crh.getMcc(), crh.getMnc());
            if (refbookList.size() > 0) {
                rateMap.put("network", refbookList.get(0).getNetwork());
            } else {
                rateMap.put("network", "Not found");
            }
            rateListMap.add(rateMap);
        }
        return rateListMap;
    }

    @Override
    public boolean hasVendorRatesHistoryByDateSmppVendorIpsMccMncExceptUpdate(Date date, SmppVendorIps smppVendorIps, String mcc, String mnc, VendorRatesUpdate vendorRatesUpdate) {
        return vendorRatesHistoryRepository.findAllBySmppVendorIpsAndDateAndMccAndMncExceptUpdate(date, smppVendorIps, mcc, mnc, vendorRatesUpdate).size() > 0;
    }

    @Override
    @Transactional
    public void deleteVendorRatesHistory(VendorRatesHistory vendorRatesHistory) {
        vendorRatesHistory.setVendorRatesUpdate(null);
        vendorRatesHistory.setSmppVendorIps(null);
        vendorRatesHistoryRepository.save(vendorRatesHistory);
        vendorRatesHistoryRepository.delete(vendorRatesHistory);
    }

    @Override
    public VendorRatesHistory save(VendorRatesHistory vendorRatesHistory) {
        return vendorRatesHistoryRepository.saveAndFlush(vendorRatesHistory);
    }

    @Override
    public float getRateByMccAndMnc(String cid, Date date, String mcc, String mnc) {
        float returnValue = 0;
        SmppVendorIps currentIps = smppVendorIpsService.getSmppVendorIpsByCid(cid);
        if (currentIps != null) {
            SmppVendorAccount currentAccount = currentIps.getSmppVendorAccount();
            if (currentAccount != null) {
                Query query = entityManager.createQuery("FROM VendorRatesHistory c WHERE c.smppVendorIps = :account AND c.mcc = :mcc AND c.mnc = :mnc AND c.date <= :date ORDER BY c.date DESC");
                query.setParameter("account", currentAccount);
                query.setParameter("mcc", mcc);
                query.setParameter("mnc", mnc);
                query.setParameter("date", date);

                List<ClientRatesHistory> list = query.getResultList();

                if (list.size() > 0) {
                    returnValue = list.get(0).getRate();
                } else {
                    query = entityManager.createQuery("FROM VendorRatesHistory c WHERE c.smppVendorIps = :account AND c.mcc = :mcc AND c.mnc = 'Flt' AND c.date <= :date ORDER BY c.date DESC");
                    query.setParameter("account", currentAccount);
                    query.setParameter("mcc", mcc);
                    query.setParameter("date", date);

                    list = query.getResultList();
                    if (list.size() > 0) {
                        returnValue = list.get(0).getRate();
                    }

                }

            }
        }

        return returnValue;
    }

    @Override
    public List<VendorRatesHistory> getRatesToActulize() {
        Date currentdate = new Date();
        return vendorRatesHistoryRepository.getRatesToActulize(currentdate);
    }

    @Override
    public Boolean haveNewerUpdates(SmppVendorIps account, String mcc, String mnc, Date date) {
        Boolean returnValue = false;
        List<VendorRatesHistory> list = vendorRatesHistoryRepository.getRatesAfterDate(account, mcc, mnc, date);
        if (list.size() >0 ) {
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public VendorRatesHistory getNextRateAfterDate(SmppVendorIps account, String mcc, String mnc, Date date) {
        VendorRatesHistory returnValue = null;

        List<VendorRatesHistory> list = vendorRatesHistoryRepository.getRatesAfterDate(account, mcc, mnc, date);
        if (list.size() > 0) {
            returnValue = list.get(0);
        }

        return returnValue;
    }

    @Override
    public List<VendorRatesHistory> getHistoryByAccountAndMcc(SmppVendorIps smppVendorIps, String mcc) {
        return vendorRatesHistoryRepository.fingVendorRateHistoryByAccountAndMccSort(smppVendorIps, mcc);
    }
}
