package app.service.clientRatesUpdate;

import app.entity.ClientRatesHistory;
import app.entity.ClientRatesUpdate;
import app.entity.EmailContent;
import app.repository.ClientRatesUpdateRepository;
import app.service.clientRatesHistroy.ClientRatesHistoryService;
import app.service.smppClientAccount.SmppClientAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Олег on 23.09.2017.
 */

@Service
public class ClientRatesUpdateServiceImpl implements ClientRatesUpdateService{
    @Autowired
    private ClientRatesUpdateRepository clientRatesUpdateRepository;

    @Autowired
    private SmppClientAccountService smppClientAccountService;

    @Autowired
    private ClientRatesHistoryService clientRatesHistoryService;

    @Override
    public Map<String, Object> getClientRatesUpdateSortForPage(int page, int size){
        Pageable pageable = new PageRequest(page, size);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rates", getRatesUpdateMap(clientRatesUpdateRepository.findAllForPage(pageable)));
        pageable = new PageRequest(page + 1, size);
        if (clientRatesUpdateRepository.findAllForPage(pageable).size() == 0){
            resultMap.put("next", "false");
        }else {
            resultMap.put("next", "true");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getClientRatesUpdateBySmppClientAccountSortForPage(Long smppClientAccount_id, int page, int size){
        Pageable pageable = new PageRequest(page, size);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rates", getRatesUpdateMap(clientRatesUpdateRepository.findBySmppClientAccountForPage(smppClientAccountService.getSmppClientAccountById(smppClientAccount_id), pageable)));
        pageable = new PageRequest(page + 1, size);
        if (clientRatesUpdateRepository.findBySmppClientAccountForPage(smppClientAccountService.getSmppClientAccountById(smppClientAccount_id), pageable).size() == 0){
            resultMap.put("next", "false");
        }else {
            resultMap.put("next", "true");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getRateUpdateMapBySmppClientAccountAndMccAndMncForPage(Long smppClientAccount_id, String mcc, String mnc, Integer page_index, Integer page_size) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ClientRatesUpdate> clientRatesUpdateList = new ArrayList<>();
        List<ClientRatesHistory> clientRatesHistoryList = clientRatesHistoryService.getRateHistoryListBySmppClientAccountAndMccAndMnc(smppClientAccount_id, mcc, mnc, page_index, page_size);
        for (ClientRatesHistory clientRatesHistory : clientRatesHistoryList) {
            clientRatesUpdateList.add(clientRatesHistory.getClientRatesUpdate());
        }
        resultMap.put("rates", getRatesUpdateMap(clientRatesUpdateList));
        if (clientRatesHistoryService.getRateHistoryListBySmppClientAccountAndMccAndMnc(smppClientAccount_id, mcc, mnc, page_index + 1, page_size).size() == 0) {
            resultMap.put("next", "false");
        } else {
            resultMap.put("next", "true");
        }
        return resultMap;
    }

    private List<Map<String, String>> getRatesUpdateMap(List<ClientRatesUpdate> clientRatesUpdateList){
        List<Map<String, String>> rates = new ArrayList<>();
        for (ClientRatesUpdate clientRatesUpdate: clientRatesUpdateList) {
            Map<String, String> rate = new HashMap<>();
            rate.put("id", String.valueOf(clientRatesUpdate.getId()));
            rate.put("smppClientAccount", clientRatesUpdate.getSmppClientAccount().getCustomer().getName() + " - " + clientRatesUpdate.getSmppClientAccount().getName());
            rate.put("date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(clientRatesUpdate.getDate()));
            rate.put("user", clientRatesUpdate.getUser().getName());
            StringBuilder sb = new StringBuilder();
            boolean flag = false;
            for (EmailContent emailContent: clientRatesUpdate.getEmailContentList()) {
                if (flag) sb.append("<br>");
                flag = true;
                sb.append("<a href='/admin_full_email_content_view?id_email_content=" + emailContent.getId() + "'>Outgoing email to " + emailContent.getTo() + " (" + emailContent.getSubject().replaceFirst("^.*\\(", "").replace(")", "") + ")</a>");
            }
            if (clientRatesUpdate.getEmailContentList().size() == 0) sb.append("No email");
            rate.put("emails", sb.toString());
            rates.add(rate);
        }
        return rates;
    }

    @Override
    public ClientRatesUpdate saveRateUpdate(ClientRatesUpdate currentUpdate) {
        return clientRatesUpdateRepository.save(currentUpdate);
    }

    @Override
    public List<ClientRatesUpdate> getUpdatesListByDatesAndCustomer(Date startDate, Date endDate, Long customerAccount) {
        List<ClientRatesUpdate> updatesList = null;
        List<ClientRatesUpdate> returntList = new ArrayList<>();
        updatesList = clientRatesUpdateRepository.getRateUpdatesByDate(startDate, endDate);
        if (customerAccount != 0L) {
            for (ClientRatesUpdate currentUpdate : updatesList) {
                if (currentUpdate.getSmppClientAccount().getId() == customerAccount) {
                    returntList.add(currentUpdate);
                }
            }
        } else {
            returntList = updatesList;
        }
        return returntList;
    }

    @Override
    public ClientRatesUpdate getRnById(Long id) {
        ClientRatesUpdate currentRN = null;
        currentRN = clientRatesUpdateRepository.getOne(id);
        return currentRN;
    }

}
