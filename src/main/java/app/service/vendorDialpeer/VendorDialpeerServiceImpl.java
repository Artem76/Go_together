package app.service.vendorDialpeer;

import app.entity.SmppVendorAccount;
import app.entity.VendorDialpeer;
import app.entity.VendorDialpeerChild;
import app.repository.VendorDialpeerRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import app.service.vendorDialpeerChild.VendorDialpeerChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
@Service
public class VendorDialpeerServiceImpl implements VendorDialpeerService {
    @Autowired
    private VendorDialpeerRepository vendorDialpeerRepository;

    @Autowired
    private VendorDialpeerChildService vendorDialpeerChildService;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Override
    public List<VendorDialpeer> getVendorDialpeersAll() {
        return vendorDialpeerRepository.findAll();
    }

    @Override
    public VendorDialpeer getVendorDialpeerById(Long id) {
        return vendorDialpeerRepository.findOne(id);
    }

    @Override
    @Transactional
    public VendorDialpeer addVendorDialpeer(String name) {
        if (name == null || name.equals("")) return null;
        VendorDialpeer vendorDialpeer = new VendorDialpeer(name);
        vendorDialpeerRepository.save(vendorDialpeer);
        changesRegisteryService.registerChangesAllSoftswitches("dialpeer" + "_" + vendorDialpeer.getId());
        return vendorDialpeer;
    }

    @Override
    @Transactional
    public VendorDialpeer updateVendorDialpeer(Long id, String name) {
        if (name == null || name.equals("")) return null;
        VendorDialpeer vendorDialpeer = vendorDialpeerRepository.findOne(id);
        vendorDialpeer.setName(name);
        vendorDialpeerRepository.save(vendorDialpeer);
        changesRegisteryService.registerChangesAllSoftswitches("dialpeer" + "_" + vendorDialpeer.getId());

        return vendorDialpeer;
    }

    @Override
    @Transactional
    public void deleteVendorDialpeer(Long id) {
        VendorDialpeer vendorDialpeer = vendorDialpeerRepository.findOne(id);
        List<VendorDialpeerChild> vendorDialpeerChildList = vendorDialpeer.getVendorDialpeerChildList();
        for (VendorDialpeerChild vendorDialpeerChild : vendorDialpeerChildList) {
            vendorDialpeerChildService.deleteVendorDialpeerChild(vendorDialpeerChild.getId());
        }
        vendorDialpeerRepository.delete(vendorDialpeer);
    }

    @Override
    public void UpdateTag(VendorDialpeer dialpeer, Integer newTag) {
        dialpeer.setTag(newTag);
        vendorDialpeerRepository.save(dialpeer);
    }
}
