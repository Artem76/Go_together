package app.service.smppVendorIps;

import app.entity.SmppVendorAccount;
import app.entity.SmppVendorIps;
import app.repository.SmppVendorIpsRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmppVendorIpsServiceImpl implements SmppVendorIpsService{
    @Autowired
    SmppVendorIpsRepository smppVendorIpsRepository;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Override
    public boolean isCid(Long id, String cid) {
        return smppVendorIpsRepository.findByCidExceptId(cid, id).size() != 0;
    }

    @Override
    public List<SmppVendorIps> getSmppVendorIpsAll() {
        return smppVendorIpsRepository.findAll();
    }

    @Override
    public SmppVendorIps getSmppVendorIpsById(Long id) {
        return smppVendorIpsRepository.findOne(id);
    }

    @Override
    public SmppVendorIps getSmppVendorIpsByCid(String cid) {
        return smppVendorIpsRepository.findByCid(cid);
    }

    @Override
    public List<SmppVendorIps> getSmppVendorIpsBySystemId(String systemId) {
        return smppVendorIpsRepository.findBySystemId(systemId);
    }

    @Override
    public List<SmppVendorIps> getSmppVendorIpsAllSort() {
        return smppVendorIpsRepository.findAllSort();
    }


    @Override
    public List<SmppVendorIps> findByAccountId(SmppVendorAccount account) {
        return smppVendorIpsRepository.findByAccountId(account);
    }

    @Override
    public  List<SmppVendorIps> findByAccountIdAllowed(SmppVendorAccount account) {
        return  smppVendorIpsRepository.findByAccountIdAllowed(account);
    }

    @Override
    @Transactional
    public SmppVendorIps addSmppVendorIps(SmppVendorIps smppVendorIps) {
        smppVendorIpsRepository.save(smppVendorIps);
        changesRegisteryService.registerChangesAllSoftswitches("smppvendoraccount" + "_" + smppVendorIps.getSmppVendorAccount().getId());
        return smppVendorIps;
    }

    @Override
    @Transactional
    public SmppVendorIps updateSmppVendorIps(Long id, String ip, Integer port, String systemId, String password, String systemType, Long submitThroughput, String cid, Boolean allowed, SmppVendorAccount smppVendorAccount, Long softswitchId) {
        SmppVendorIps smppVendorIps = smppVendorIpsRepository.getOne(id);
        smppVendorIps.setIp(ip);
        smppVendorIps.setPort(port);
        smppVendorIps.setSystemId(systemId);
        smppVendorIps.setPassword(password);
        smppVendorIps.setSystemType(systemType);
        smppVendorIps.setSubmitThroughput(submitThroughput);
        smppVendorIps.setCid(cid);
        smppVendorIps.setAllowed(allowed);
        smppVendorIps.setSmppVendorAccount(smppVendorAccount);
        smppVendorIps.setSoftswitchId(softswitchId);
        smppVendorIpsRepository.save(smppVendorIps);
        changesRegisteryService.registerChangesAllSoftswitches("smppvendoraccount" + "_" + smppVendorIps.getSmppVendorAccount().getId());
        return smppVendorIps;
    }

    public  void updateSmppVendorIpsCid(SmppVendorIps smppVendorIps, String cid) {
        smppVendorIps.setCid(cid);
        smppVendorIpsRepository.save(smppVendorIps);
    }

    @Override
    @Transactional
    public void deleteSmppVendorIps(Long id) {
        SmppVendorIps smppVendorIps = smppVendorIpsRepository.findOne(id);
        smppVendorIps.setSmppVendorAccount(null);
        smppVendorIpsRepository.delete(smppVendorIps);
    }
}
