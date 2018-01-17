package app.service.vendorDialpeer;

import app.entity.VendorDialpeer;

import java.util.List;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
public interface VendorDialpeerService {
    List<VendorDialpeer> getVendorDialpeersAll();
    VendorDialpeer getVendorDialpeerById(Long id);
    VendorDialpeer addVendorDialpeer(String name);
    VendorDialpeer updateVendorDialpeer(Long id, String name);
    void deleteVendorDialpeer(Long id);
    void UpdateTag(VendorDialpeer dialpeer, Integer newTag);
}
