package app.service.smppVendorIps;

import app.entity.SmppVendorAccount;
import app.entity.SmppVendorIps;

import java.util.List;

public interface SmppVendorIpsService {
    boolean isCid(Long id, String cid);
    List<SmppVendorIps> getSmppVendorIpsAll();
    SmppVendorIps getSmppVendorIpsById(Long id);
    SmppVendorIps getSmppVendorIpsByCid(String cid);
    List<SmppVendorIps> getSmppVendorIpsBySystemId(String systemId);
    List<SmppVendorIps> getSmppVendorIpsAllSort();
    List<SmppVendorIps> findByAccountId(SmppVendorAccount account);
    List<SmppVendorIps> findByAccountIdAllowed(SmppVendorAccount account);
    SmppVendorIps addSmppVendorIps(SmppVendorIps smppVendorIps);
    SmppVendorIps updateSmppVendorIps(Long id, String ip, Integer port, String systemId, String password, String systemType, Long submitThroughput, String cid, Boolean allowed, SmppVendorAccount smppVendorAccount, Long softswitchId);
    void updateSmppVendorIpsCid(SmppVendorIps smppVendorIps, String cid);
    void deleteSmppVendorIps(Long id);
}
