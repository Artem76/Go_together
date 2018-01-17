package app.service.vendorRatesUpdate;

import app.entity.VendorRatesHistory;
import app.entity.VendorRatesUpdate;
import app.repository.VendorRatesUpdateRepository;
import app.service.smppVendorIps.SmppVendorIpsService;
import app.service.vendorRatesHistory.VendorRatesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by АРТЕМ on 13.11.2017.
 */

@Service
public class VendorRatesUpdateServiceImpl implements VendorRatesUpdateService{
    @Autowired
    private VendorRatesUpdateRepository vendorRatesUpdateRepository;

    @Autowired
    private SmppVendorIpsService smppVendorIpsService;

    @Autowired
    private VendorRatesHistoryService vendorRatesHistoryService;

    @Override
    public Map<String, Object> getVendorRatesUpdateSortForPage(int page, int size){
        Pageable pageable = new PageRequest(page, size);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rates", getRatesUpdateMap(vendorRatesUpdateRepository.findAllForPage(pageable)));
        pageable = new PageRequest(page + 1, size);
        if (vendorRatesUpdateRepository.findAllForPage(pageable).size() == 0){
            resultMap.put("next", "false");
        }else {
            resultMap.put("next", "true");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getVendorRatesUpdateBySmppVendorIpsSortForPage(Long smppVendorIps_id, int page, int size){
        Pageable pageable = new PageRequest(page, size);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rates", getRatesUpdateMap(vendorRatesUpdateRepository.findBySmppVendorIpsForPage(smppVendorIpsService.getSmppVendorIpsById(smppVendorIps_id), pageable)));
        pageable = new PageRequest(page + 1, size);
        if (vendorRatesUpdateRepository.findBySmppVendorIpsForPage(smppVendorIpsService.getSmppVendorIpsById(smppVendorIps_id), pageable).size() == 0){
            resultMap.put("next", "false");
        }else {
            resultMap.put("next", "true");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getRateUpdateMapBySmppVendorIpsAndMccAndMncForPage(Long smppVendorIps_id, String mcc, String mnc, Integer page_index, Integer page_size) {
        Map<String, Object> resultMap = new HashMap<>();
        List<VendorRatesUpdate> vendorRatesUpdateList = new ArrayList<>();
        List<VendorRatesHistory> vendorRatesHistoryList = vendorRatesHistoryService.getRateHistoryListBySmppVendorIpsAndMccAndMnc(smppVendorIps_id, mcc, mnc, page_index, page_size);
        for (VendorRatesHistory vendorRatesHistory : vendorRatesHistoryList) {
            vendorRatesUpdateList.add(vendorRatesHistory.getVendorRatesUpdate());
        }
        resultMap.put("rates", getRatesUpdateMap(vendorRatesUpdateList));
        if (vendorRatesHistoryService.getRateHistoryListBySmppVendorIpsAndMccAndMnc(smppVendorIps_id, mcc, mnc, page_index + 1, page_size).size() == 0) {
            resultMap.put("next", "false");
        } else {
            resultMap.put("next", "true");
        }
        return resultMap;
    }

    private List<Map<String, String>> getRatesUpdateMap(List<VendorRatesUpdate> vendorRatesUpdateList){
        List<Map<String, String>> rates = new ArrayList<>();
        for (VendorRatesUpdate vendorRatesUpdate: vendorRatesUpdateList) {
            Map<String, String> rate = new HashMap<>();
            rate.put("id", String.valueOf(vendorRatesUpdate.getId()));
            rate.put("smppVendorIps", vendorRatesUpdate.getSmppVendorIps().getSmppVendorAccount().getCustomer().getName() + " - " + vendorRatesUpdate.getSmppVendorIps().getSmppVendorAccount().getName() + " - " + vendorRatesUpdate.getSmppVendorIps().getSystemId());
            rate.put("date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(vendorRatesUpdate.getDate()));
            rate.put("user", vendorRatesUpdate.getUser().getName());
            if (vendorRatesUpdate.getEmailContent() != null){
                rate.put("email", "<a href='/admin_full_email_content_view?id_email_content=" + vendorRatesUpdate.getEmailContent().getId() + "'>Incoming email from " + vendorRatesUpdate.getEmailContent().getFrom());
            } else {
                rate.put("email", "No email");
            }
            rates.add(rate);
        }
        return rates;
    }

    @Override
    public VendorRatesUpdate saveRateUpdate(VendorRatesUpdate currentUpdate) {
        return vendorRatesUpdateRepository.saveAndFlush(currentUpdate);
    }

    /*@Override
    public List<VendorRatesUpdate> getUpdatesListByDatesAndCustomer(Date startDate, Date endDate, Long customerAccount) {
        List<ClientRatesUpdate> updatesList = null;
        List<ClientRatesUpdate> returntList = new ArrayList<>();
        updatesList = clientRatesUpdateRepository.getRateUpdatesByDate(startDate, endDate);
        if (customerAccount != 0L) {
            for (ClientRatesUpdate currentUpdate : updatesList) {
                if (currentUpdate.getSmppClientAccount().getId() == customerAccount) {
                    returntList.add(currentUpdate);
                }
            }
        } else {
            returntList = updatesList;
        }
        return returntList;
    }*/

    @Override
    public VendorRatesUpdate getRnById(Long id) {
        return vendorRatesUpdateRepository.findOne(id);
    }
}
