package app.service.clientRatesHistroy;

import app.entity.ClientRatesHistory;
import app.entity.ClientRatesUpdate;
import app.entity.Refbook;
import app.entity.SmppClientAccount;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 07.08.2017.
 */
public interface ClientRatesHistoryService {
    List<ClientRatesHistory> getClientRatesHistory(Date date, SmppClientAccount smppClientAccount, String mcc, String mnc);

    List<ClientRatesHistory> getRatesByUpdate(ClientRatesUpdate clientRatesUpdate);

    List<Map<String,String>> getRateListMapByUpdate(ClientRatesUpdate clientRatesUpdate);

    List<ClientRatesHistory> getRateHistoryListBySmppClientAccountAndMccAndMnc(Long smppClientAccount_id, String mcc, String mnc, Integer page_index, Integer page_size);

    boolean hasClientRatesHistoryByDateSmppClientAccountMccMncExceptUpdate(Date date, SmppClientAccount smppClientAccount, String mcc, String mnc, ClientRatesUpdate clientRatesUpdate);

    void deleteClientRatesHistory(ClientRatesHistory clientRatesHistory);

    ClientRatesHistory save(ClientRatesHistory clientRatesHistory);

    void save(List<ClientRatesHistory> clientRatesHistoryList);

    float getRateByMccAndMnc(String uid, Date date, String mcc, String mnc);

    Float getCurrentRate(Date date, SmppClientAccount smppClientAccount, String mcc, String mnc, Boolean applied);

    List<Refbook> getOpenedRefbooksByAccountAndMcc(Long id, String mcc, String mode);

    List<ClientRatesHistory> getRatesToActulize();

    Boolean haveNewerUpdates(SmppClientAccount account, String mcc, String mnc, Date date);

    ClientRatesHistory getNextRateAfterDate(SmppClientAccount account, String mcc, String mnc, Date date);

    List<ClientRatesHistory> getHistoryByAccountAndMcc(SmppClientAccount smppClientAccount, String mcc);
}
