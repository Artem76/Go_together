package app.service.smppClientIps;

import app.entity.SmppClientAccount;
import app.entity.SmppClientIps;
import app.repository.SmppClientIpsRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmppClientIpsServiceImpl implements SmppClientIpsService{
    @Autowired
    SmppClientIpsRepository smppClientIpsRepository;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Override
    public List<SmppClientIps> getSmppClientIpsAll() {
        return smppClientIpsRepository.findAll();
    }

    @Override
    public List<SmppClientIps> getSmppClientIpsAllSort() {
        return smppClientIpsRepository.findAllSort();
    }

    @Override
    public SmppClientIps getSmppClientIpsById(Long id) {
        return smppClientIpsRepository.findOne(id);
    }

    @Override
    @Transactional
    public SmppClientIps addSmppClientIps(SmppClientIps smppClientIps) {
        smppClientIpsRepository.save(smppClientIps);
        changesRegisteryService.registerChangesAllSoftswitches("smppclientaccount" + "_" + smppClientIps.getSmppClientAccount().getId());
        return smppClientIps;
    }

    @Override
    @Transactional
    public SmppClientIps updateSmppClientIps(Long id, String ip, Boolean allowed, SmppClientAccount smppClientAccount) {
        SmppClientIps smppClientIps = smppClientIpsRepository.getOne(id);
        smppClientIps.setIp(ip);
        smppClientIps.setAllowed(allowed);
        smppClientIps.setSmppClientAccount(smppClientAccount);
        smppClientIpsRepository.save(smppClientIps);
        changesRegisteryService.registerChangesAllSoftswitches("smppclientaccount" + "_" + smppClientIps.getSmppClientAccount().getId());
        return smppClientIps;
    }

    @Override
    public void deleteSmppClientIps(Long id) {
        SmppClientIps smppClientIps = smppClientIpsRepository.findOne(id);
        smppClientIps.setSmppClientAccount(null);
        smppClientIpsRepository.delete(smppClientIps);
    }
}
