package app.service.clientRatesCurrent;

import app.entity.ClientRatesCurrent;
import app.entity.SmppClientAccount;

import java.util.List;

/**
 * Created by Олег on 07.08.2017.
 */
public interface ClientRatesCurrentService {
    ClientRatesCurrent getRate(SmppClientAccount smppClientAccount, String mcc, String mnc);
    void updateRate(SmppClientAccount smppClientAccount, String mcc, String mnc, Float rate);
    void save(ClientRatesCurrent rate);
    ClientRatesCurrent getById(Long id);
    List<ClientRatesCurrent> getCurrentRatesByAccountAndCountry(Long account, String mcc);
}
