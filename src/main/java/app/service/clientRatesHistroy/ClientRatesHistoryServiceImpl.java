package app.service.clientRatesHistroy;

import app.entity.*;
import app.repository.ClientRatesHistoryRepository;
import app.service.clientRatesUpdate.ClientRatesUpdateService;
import app.service.refbook.RefbookService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Олег on 07.08.2017.
 */

@Service
public class ClientRatesHistoryServiceImpl implements ClientRatesHistoryService {

    @Autowired
    private ClientRatesHistoryRepository clientRatesHistoryRepository;

    @Autowired
    private RefbookService refbookService;

    @Autowired
    private SmppClientAccountService smppClientAccountService;

    @Autowired
    private ClientRatesUpdateService clientRatesUpdateService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Override
    public List<ClientRatesHistory> getClientRatesHistory(Date date, SmppClientAccount smppClientAccount, String mcc, String mnc) {
        return clientRatesHistoryRepository.getClientRatesHistory(date, smppClientAccount, mcc, mnc);
    }

    @Override
    public List<ClientRatesHistory> getRatesByUpdate(ClientRatesUpdate clientRatesUpdate) {
        List<ClientRatesHistory> currentList = null;
        currentList = clientRatesHistoryRepository.getRatesByUpdate(clientRatesUpdate);
        return currentList;
    }

    @Override
    public List<Map<String, String>> getRateListMapByUpdate(ClientRatesUpdate clientRatesUpdate) {
        List<ClientRatesHistory> currentList = clientRatesHistoryRepository.getRatesByUpdate(clientRatesUpdate);
        List<Map<String, String>> rateListMap = new ArrayList<>();
        for (ClientRatesHistory crh : currentList) {
            Map<String, String> rateMap = new HashMap<>();
            rateMap.put("mcc", crh.getMcc());
            rateMap.put("mnc", crh.getMnc());
            rateMap.put("date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(crh.getDate()));
            rateMap.put("rate", crh.getRate().toString());

            Refbook refbook = refbookService.getRootRefbookByMcc(crh.getMcc());
            if (refbook != null) {
                rateMap.put("country", refbook.getCountry());
            } else {
                rateMap.put("country", "Not found");
            }
            List<Refbook> refbookList = refbookService.getRefbookByMccAndMnc(crh.getMcc(), crh.getMnc());
            if (refbookList.size() > 0) {
                rateMap.put("network", refbookList.get(0).getNetwork());
            } else {
                rateMap.put("network", "Not found");
            }
            rateListMap.add(rateMap);
        }
        return rateListMap;
    }

    @Override
    public List<ClientRatesHistory> getRateHistoryListBySmppClientAccountAndMccAndMnc(Long smppClientAccount_id, String mcc, String mnc, Integer page_index, Integer page_size) {
        Pageable pageable = new PageRequest(page_index, page_size);
        List<ClientRatesHistory> clientRatesHistoryList = null;
        if (smppClientAccount_id == 0l) {
            clientRatesHistoryList = clientRatesHistoryRepository.getRatesByMccAndMnc(mcc, mnc, pageable);
        } else {
            clientRatesHistoryList = clientRatesHistoryRepository.getRatesBySmppClientAccountAndMccAndMnc(smppClientAccountService.getSmppClientAccountById(smppClientAccount_id), mcc, mnc, pageable);
        }
        return clientRatesHistoryList;
    }

    @Override
    public boolean hasClientRatesHistoryByDateSmppClientAccountMccMncExceptUpdate(Date date, SmppClientAccount smppClientAccount, String mcc, String mnc, ClientRatesUpdate clientRatesUpdate) {
        return clientRatesHistoryRepository.findAllBySmppClientAccountAndDateAndMccAndMncExceptUpdate(date, smppClientAccount, mcc, mnc, clientRatesUpdate).size() > 0;
    }

    @Override
    @Transactional
    public void deleteClientRatesHistory(ClientRatesHistory clientRatesHistory) {
//        SmppClientAccount smppClientAccount = clientRatesHistory.getSmppClientAccount();
//        smppClientAccount.deleteClientRatesHistory(clientRatesHistory);
//        smppClientAccountService.save(smppClientAccount);
//        ClientRatesUpdate clientRatesUpdate = clientRatesHistory.getClientRatesUpdate();
//        clientRatesUpdate.deleteClientRatesHistory(clientRatesHistory);
//        clientRatesUpdateService.saveRateUpdate(clientRatesUpdate);
        clientRatesHistory.setClientRatesUpdate(null);
        clientRatesHistory.setSmppClientAccount(null);
        clientRatesHistoryRepository.save(clientRatesHistory);
        clientRatesHistoryRepository.delete(clientRatesHistory);
    }

    @Override
    public ClientRatesHistory save(ClientRatesHistory clientRatesHistory) {
        return clientRatesHistoryRepository.saveAndFlush(clientRatesHistory);
    }

    @Override
    public void save(List<ClientRatesHistory> clientRatesHistoryList) {
        clientRatesHistoryRepository.save(clientRatesHistoryList);
    }

    @Override
    public float getRateByMccAndMnc(String uid, Date date, String mcc, String mnc) {
        float returnValue = 0;
        SmppClientSystemId currentSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(uid);
        if (currentSystemId != null) {
            SmppClientAccount currentAccount = currentSystemId.getSmppClientAccount();
            if (currentAccount != null) {
                Query query = entityManager.createQuery("FROM ClientRatesHistory c WHERE c.smppClientAccount = :account AND c.mcc = :mcc AND c.mnc = :mnc AND c.date <= :date ORDER BY c.date DESC");
                query.setParameter("account", currentAccount);
                query.setParameter("mcc", mcc);
                query.setParameter("mnc", mnc);
                query.setParameter("date", date);

                List<ClientRatesHistory> list = query.getResultList();

                if (list.size() > 0) {
                    returnValue = list.get(0).getRate();
                } else {
                    query = entityManager.createQuery("FROM ClientRatesHistory c WHERE c.smppClientAccount = :account AND c.mcc = :mcc AND c.mnc = 'Flt' AND c.date <= :date ORDER BY c.date DESC");
                    query.setParameter("account", currentAccount);
                    query.setParameter("mcc", mcc);
                    query.setParameter("date", date);

                    list = query.getResultList();
                    if (list.size() > 0) {
                        returnValue = list.get(0).getRate();
                    }

                }

            }
        }

        return returnValue;
    }

    @Override
    public Float getCurrentRate(Date date, SmppClientAccount smppClientAccount, String mcc, String mnc, Boolean applied) {
        ClientRatesHistory clientRatesHistory = clientRatesHistoryRepository.findFirstByMccAndMncAndSmppClientAccountAndAppliedAndDateBeforeOrderByDateDesc(mcc, mnc, smppClientAccount, applied, date);
        if (clientRatesHistory == null) return null;
        return clientRatesHistory.getRate();
    }

    @Override
    public List<Refbook> getOpenedRefbooksByAccountAndMcc(Long id, String mcc, String mode) {
        List<Refbook> returnList = new ArrayList<>();

        if (mode.equals("opened")) {

            List<ClientRatesHistory> list = clientRatesHistoryRepository.fingClientRateHistoryByAccountAndMcc(smppClientAccountService.getSmppClientAccountById(id), mcc);
            for (ClientRatesHistory currentElement : list) {
                String mnc = currentElement.getMnc();
                if (mnc == null) {
                    mnc = "";
                }

                if (mnc.equals("")) {
                    Refbook newRefbook = refbookService.getRootRefbookByMcc(currentElement.getMcc());
                    if (newRefbook != null) {
                        newRefbook.setNetwork("FLAT");
                        returnList.add(newRefbook);
                    }
                } else {
                    List<Refbook> newRefbook = refbookService.getRefbookByMccAndMnc(currentElement.getMcc(), mnc);
                    if (newRefbook.size() > 0) {
                        Refbook currentRefbook = newRefbook.get(0);
                        if (currentRefbook.getCountry() == null) {
                            Refbook rootCountryRefbook = refbookService.getRootRefbookByMcc(currentRefbook.getMcc());
                            if (rootCountryRefbook != null) {
                                currentRefbook.setCountry(rootCountryRefbook.getCountry());
                            }
                        }
                        returnList.add(currentRefbook);
                    }
                }


            }
        } else {
            List<Refbook> list = refbookService.findAllRefbookByMcc(mcc);
            for (Refbook currentRefbook : list) {
                String mnc = currentRefbook.getMnc();

                if (mnc == null) {
                    currentRefbook.setNetwork("FLAT");
                } else {
                    if (mnc.equals("")) {
                        currentRefbook.setNetwork("FLAT");
                    }
                }
                if (currentRefbook.getCountry() == null) {
                    Refbook rootCountryRefbook = refbookService.getRootRefbookByMcc(currentRefbook.getMcc());
                    if (rootCountryRefbook != null) {
                        currentRefbook.setCountry(rootCountryRefbook.getCountry());
                    }
                }
                returnList.add(currentRefbook);
            }
        }

        return returnList;
    }

    @Override
    public List<ClientRatesHistory> getRatesToActulize() {
        Date currentdate = new Date();
        return clientRatesHistoryRepository.getRatesToActulize(currentdate);
    }

    @Override
    public Boolean haveNewerUpdates(SmppClientAccount account, String mcc, String mnc, Date date) {
        Boolean returnValue = false;
        List<ClientRatesHistory> list = clientRatesHistoryRepository.getRatesAfterDate(account, mcc, mnc, date);
        if (list.size() >0 ) {
            returnValue = true;
        }
        return returnValue;
    }

    @Override
    public ClientRatesHistory getNextRateAfterDate(SmppClientAccount account, String mcc, String mnc, Date date) {
        ClientRatesHistory returnValue = null;

        List<ClientRatesHistory> list = clientRatesHistoryRepository.getRatesAfterDate(account, mcc, mnc, date);
        if (list.size() > 0) {
            returnValue = list.get(0);
        }

        return returnValue;
    }

    @Override
    public List<ClientRatesHistory> getHistoryByAccountAndMcc(SmppClientAccount smppClientAccount, String mcc) {
        return clientRatesHistoryRepository.fingClientRateHistoryByAccountAndMccSort(smppClientAccount, mcc);
    }
}
