package app.service.vendorDialpeerChild;

import app.entity.SmppVendorIps;
import app.entity.VendorDialpeer;
import app.entity.VendorDialpeerChild;

import java.util.List;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
public interface VendorDialpeerChildService {
    List<VendorDialpeerChild> getVendorDialpeerChildAll();
    VendorDialpeerChild getVendorDialpeerChildById(Long id);
    List<VendorDialpeerChild> getVendorDialpeerChildByVendorDialpeer(VendorDialpeer vendorDialpeer);
    Long addVendorDialpeerChild(Integer weight, SmppVendorIps smppVendorIps, VendorDialpeer vendorDialpeer);
    Long updateVendorDialpeerChild(Long id, Integer weight, SmppVendorIps smppVendorIps);
    void deleteVendorDialpeerChild(Long id);
}
