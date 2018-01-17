package app.service.vendorRatesCurrent;

import app.entity.SmppVendorIps;
import app.entity.VendorRatesCurrent;

import java.util.List;

/**
 * Created by Олег on 07.08.2017.
 */
public interface VendorRatesCurrentService {
    VendorRatesCurrent getRate(SmppVendorIps cid, String mcc, String mnc);
    void save(VendorRatesCurrent rate);
    VendorRatesCurrent getById(Long id);
    List<VendorRatesCurrent> getCurrentRatesByAccountAndCountry(Long account, String mcc);
//    void updateRate(String cid, String mcc, String mnc, Float rate);
}
