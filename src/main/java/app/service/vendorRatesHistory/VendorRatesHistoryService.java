package app.service.vendorRatesHistory;

import app.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 07.08.2017.
 */
public interface VendorRatesHistoryService {
    List<VendorRatesHistory> getRateHistoryListBySmppVendorIpsAndMccAndMnc(Long smppVendorIps_id, String mcc, String mnc, Integer page_index, Integer page_size);

    List<Map<String, String>> getRateListMapByUpdate(VendorRatesUpdate vendorRatesUpdate);

    boolean hasVendorRatesHistoryByDateSmppVendorIpsMccMncExceptUpdate(Date date, SmppVendorIps smppVendorIps, String mcc, String mnc, VendorRatesUpdate vendorRatesUpdate);

    @Transactional
    void deleteVendorRatesHistory(VendorRatesHistory vendorRatesHistory);

    float getRateByMccAndMnc(String cid, Date date, String mcc, String mnc);

    List<VendorRatesHistory> getRatesToActulize();

    VendorRatesHistory save(VendorRatesHistory vendorRatesHistory);

    Boolean haveNewerUpdates(SmppVendorIps account, String mcc, String mnc, Date date);

    VendorRatesHistory getNextRateAfterDate(SmppVendorIps account, String mcc, String mnc, Date date);

    List<VendorRatesHistory> getHistoryByAccountAndMcc(SmppVendorIps smppVendorIps, String mcc);

//    List<VendorRatesHistory> getVendorRatesHistory(Date date, String cid, String mcc, String mnc);
}
