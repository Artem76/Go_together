package app.service.vendorDialpeerChild;

import app.entity.SmppVendorIps;
import app.entity.VendorDialpeer;
import app.entity.VendorDialpeerChild;
import app.repository.VendorDialpeerChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
@Service
public class VendorDialpeerChildServiceImpl implements VendorDialpeerChildService {
    @Autowired
    private VendorDialpeerChildRepository vendorDialpeerChildRepository;

    @Override
    public List<VendorDialpeerChild> getVendorDialpeerChildAll() {
        return vendorDialpeerChildRepository.findAll();
    }

    @Override
    public VendorDialpeerChild getVendorDialpeerChildById(Long id) {
        return vendorDialpeerChildRepository.findOne(id);
    }

    @Override
    public List<VendorDialpeerChild> getVendorDialpeerChildByVendorDialpeer(VendorDialpeer vendorDialpeer) {
        return vendorDialpeerChildRepository.findByVendorDialpeer(vendorDialpeer);
    }

    @Override
    @Transactional
    public Long addVendorDialpeerChild(Integer weight, SmppVendorIps smppVendorIps, VendorDialpeer vendorDialpeer) {
        if(weight == null || smppVendorIps == null || vendorDialpeer == null) return  null;
        VendorDialpeerChild vendorDialpeerChild = new VendorDialpeerChild(smppVendorIps, weight, vendorDialpeer);
        vendorDialpeerChildRepository.save(vendorDialpeerChild);
        return vendorDialpeerChild.getId();
    }

    @Override
    @Transactional
    public Long updateVendorDialpeerChild(Long id, Integer weight, SmppVendorIps smppVendorIps) {
        if(weight == null || smppVendorIps == null) return  null;
        VendorDialpeerChild vendorDialpeerChild = vendorDialpeerChildRepository.findOne(id);
        vendorDialpeerChild.setWeight(weight);
        vendorDialpeerChild.setSmppVendorIps(smppVendorIps);
        vendorDialpeerChildRepository.save(vendorDialpeerChild);
        return vendorDialpeerChild.getId();
    }

    @Override
    @Transactional
    public void deleteVendorDialpeerChild(Long id) {
        VendorDialpeerChild vendorDialpeerChild = vendorDialpeerChildRepository.findOne(id);
        vendorDialpeerChild.setSmppVendorIps(null);
        vendorDialpeerChild.setVendorDialpeer(null);
        vendorDialpeerChildRepository.save(vendorDialpeerChild);
        vendorDialpeerChildRepository.delete(vendorDialpeerChild);

    }
}
