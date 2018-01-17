package app.service.clientRatesUpdate;

import app.entity.ClientRatesUpdate;
import app.entity.CustomUser;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Олег on 23.09.2017.
 */
public interface ClientRatesUpdateService {
    Map<String,Object> getClientRatesUpdateSortForPage(int page, int size);

    Map<String,Object> getClientRatesUpdateBySmppClientAccountSortForPage(Long smppClientAccount_id, int page, int size);

    Map<String, Object> getRateUpdateMapBySmppClientAccountAndMccAndMncForPage(Long smppClientAccount_id, String mcc, String mnc, Integer page_index, Integer page_size);

    ClientRatesUpdate saveRateUpdate(ClientRatesUpdate currentUpdate);
    List<ClientRatesUpdate> getUpdatesListByDatesAndCustomer(Date startDate, Date endDate, Long customerAccount);
    ClientRatesUpdate getRnById(Long id);
}
