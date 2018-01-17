package app.service.vendorRatesUpdate;

import app.entity.VendorRatesUpdate;

import java.util.Map;

/**
 * Created by АРТЕМ on 13.11.2017.
 */
public interface VendorRatesUpdateService {
    Map<String, Object> getVendorRatesUpdateSortForPage(int page, int size);

    Map<String, Object> getVendorRatesUpdateBySmppVendorIpsSortForPage(Long smppVendorIps_id, int page, int size);

    Map<String, Object> getRateUpdateMapBySmppVendorIpsAndMccAndMncForPage(Long smppVendorIps_id, String mcc, String mnc, Integer page_index, Integer page_size);

    VendorRatesUpdate saveRateUpdate(VendorRatesUpdate currentUpdate);

    VendorRatesUpdate getRnById(Long id);
}
