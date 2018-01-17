package app.service.smppClientSystemId;

import app.entity.SmppClientAccount;
import app.entity.SmppClientSystemId;
import app.entity.SmppVendorAccount;

import java.util.List;

/**
 * Created by Олег on 29.07.2017.
 */
public interface SmppClientSystemIdService {
    boolean isSystemId(Long id, String systemId);
    boolean isUid(Long id, String uid);
    SmppClientSystemId getSmppClientSystemIdByUid(String uid);
    SmppClientSystemId addSmppCleintSystemId(String systemId, String password, String uid, SmppClientAccount smppClientAccount);
    SmppClientSystemId updateSmppCleintSystemId(Long id, String systemId, String password, String uid);
    List<SmppClientSystemId> findSmppClientSystemIdsByAccount(SmppClientAccount smppClientAccount);
    void updateUid(String uid, SmppClientSystemId smppClientSystemId);
    void deleteSmppCleintSystemId(Long id);
}
