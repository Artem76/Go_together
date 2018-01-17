package app.service.smppClientIps;


import app.entity.SmppClientAccount;
import app.entity.SmppClientIps;

import java.util.List;

public interface SmppClientIpsService {
    List<SmppClientIps> getSmppClientIpsAll();
    List<SmppClientIps> getSmppClientIpsAllSort();
    SmppClientIps getSmppClientIpsById(Long id);
    SmppClientIps addSmppClientIps(SmppClientIps smppClientIps);
    SmppClientIps updateSmppClientIps(Long id, String ip,Boolean allowed, SmppClientAccount smppClientAccount);
    void deleteSmppClientIps(Long id);
}
