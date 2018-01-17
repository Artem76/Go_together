package app.service.smppClientSystemId;

import app.entity.ChangesRegistery;
import app.entity.RoutingSmsRule;
import app.entity.SmppClientAccount;
import app.entity.SmppClientSystemId;
import app.repository.SmppClientSystemIdRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import app.service.routingSmsRule.RoutingSmsRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Олег on 29.07.2017.
 */

@Service
public class SmppClientSystemIdServiceImpl implements SmppClientSystemIdService {
    @Autowired
    SmppClientSystemIdRepository smppClientSystemIdRepository;

    @Autowired
    RoutingSmsRuleService routingSmsRuleService;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Override
    public boolean isSystemId(Long id, String systemId) {
        return smppClientSystemIdRepository.findSmppClientSystemIdBySystemIdExceptId(systemId, id).size() != 0;
    }

    @Override
    public boolean isUid(Long id, String uid) {
        return smppClientSystemIdRepository.findSmppClientSystemIdByUidExceptId(uid, id).size() != 0;
    }

    @Override
    public SmppClientSystemId getSmppClientSystemIdByUid(String uid) {
        return smppClientSystemIdRepository.findSmppClientSystemIdByUid(uid);
    }

    @Override
    @Transactional
    public SmppClientSystemId addSmppCleintSystemId(String systemId, String password, String uid, SmppClientAccount smppClientAccount) {
        if (systemId.equals("") || password.equals("")) return null;
        if (!uid.equals("") && smppClientSystemIdRepository.findSmppClientSystemIdByUid(uid) != null) return null;
        if (smppClientSystemIdRepository.findSmppClientSystemIdBySystemId(systemId) != null) return null;
        SmppClientSystemId smppClientSystemId = new SmppClientSystemId(systemId, password, uid, smppClientAccount);
        smppClientSystemIdRepository.save(smppClientSystemId);

        List<RoutingSmsRule> currentRules = routingSmsRuleService.findAllByAccount(smppClientAccount.getId());
        for (RoutingSmsRule currentRule : currentRules) {
            changesRegisteryService.registerChangesAllSoftswitches("routingsmsrule_" + currentRule.getId().toString());
        }

        return smppClientSystemId;
    }

    @Override
    @Transactional
    public SmppClientSystemId updateSmppCleintSystemId(Long id, String systemId, String password, String uid) {
        if (systemId.equals("") || password.equals("")) return null;
        if (!uid.equals("") && smppClientSystemIdRepository.findSmppClientSystemIdByUidExceptId(uid, id).size() > 0) return null;
        if (smppClientSystemIdRepository.findSmppClientSystemIdBySystemIdExceptId(systemId, id).size() > 0) return null;
        SmppClientSystemId smppClientSystemId = smppClientSystemIdRepository.findOne(id);
        smppClientSystemId.setSystemId(systemId);
        smppClientSystemId.setPassword(password);
        smppClientSystemId.setUid(uid);
        smppClientSystemIdRepository.save(smppClientSystemId);
        return smppClientSystemId;
    }

    @Override
    @Transactional
    public List<SmppClientSystemId> findSmppClientSystemIdsByAccount(SmppClientAccount smppClientAccount) {
        return smppClientSystemIdRepository.findSmppClientSystemIdsByAccount(smppClientAccount);
    }

    @Override
    @Transactional
    public void updateUid(String uid, SmppClientSystemId smppClientSystemId) {
        smppClientSystemId.setUid(uid);
        smppClientSystemIdRepository.save(smppClientSystemId);
    }

    @Override
    public void deleteSmppCleintSystemId(Long id) {
        SmppClientSystemId smppClientSystemId = smppClientSystemIdRepository.findOne(id);
        smppClientSystemId.setSmppClientAccount(null);
        smppClientSystemIdRepository.delete(smppClientSystemId);
    }
}
