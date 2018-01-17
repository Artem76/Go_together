package app.service.clientRatesCurrent;

import app.entity.ClientRatesCurrent;
import app.entity.SmppClientAccount;
import app.repository.ClientRatesCurrentRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import app.service.smppClientAccount.SmppClientAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олег on 07.08.2017.
 */
@Service
public class ClientRatesCurrentServiceImpl implements ClientRatesCurrentService{

    @Autowired
    ClientRatesCurrentRepository clientRatesCurrentRepository;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SmppClientAccountService smppClientAccountService;

    @Override
    public ClientRatesCurrent getRate(SmppClientAccount smppClientAccount, String mcc, String mnc) {
        return clientRatesCurrentRepository.getRate(smppClientAccount, mcc, mnc);
    }

    @Override
    public void updateRate(SmppClientAccount smppClientAccount, String mcc, String mnc, Float rate) {
        ClientRatesCurrent currentRate = clientRatesCurrentRepository.getRate(smppClientAccount, mcc, mnc);
        if (currentRate==null) {
            currentRate = new ClientRatesCurrent(smppClientAccount, mcc, mnc, rate);
            clientRatesCurrentRepository.save(currentRate);
        } else {
            currentRate.setSmppClientAccount(smppClientAccount);
            currentRate.setMcc(mcc);
            currentRate.setMnc(mnc);
            currentRate.setRate(rate);
            clientRatesCurrentRepository.save(currentRate);
        }
    }

    @Override
    public void save(ClientRatesCurrent rate) {
        clientRatesCurrentRepository.save(rate);
        changesRegisteryService.registerChangesAllSoftswitches("clientratecurrent_" + rate.getId());
    }

    @Override
    public ClientRatesCurrent getById(Long id) {
        return clientRatesCurrentRepository.findOne(id);
    }

    @Override
    public List<ClientRatesCurrent> getCurrentRatesByAccountAndCountry(Long account, String mcc) {
        List<ClientRatesCurrent> list = new ArrayList<>();
        if(account == 0 && mcc.equals("0")) {
            list = entityManager.createQuery("SELECT c FROM ClientRatesCurrent c").getResultList();
        } else {
            if (account != 0 && !mcc.equals("0")) {
                SmppClientAccount clientAccount = smppClientAccountService.getSmppClientAccountById(account);
                list = entityManager.createQuery("SELECT c FROM ClientRatesCurrent c WHERE c.mcc = :mcc AND c.smppClientAccount = :clientAccount").setParameter("mcc", mcc).setParameter("clientAccount", clientAccount).getResultList();
            } else {
                if(account == 0) {
                    list = entityManager.createQuery("SELECT c FROM ClientRatesCurrent c WHERE c.mcc = :mcc").setParameter("mcc", mcc).getResultList();
                } else {
                    SmppClientAccount clientAccount = smppClientAccountService.getSmppClientAccountById(account);
                    list = entityManager.createQuery("SELECT c FROM ClientRatesCurrent c WHERE c.smppClientAccount = :clientAccount").setParameter("clientAccount", clientAccount).getResultList();
                }
            }

        }

        return list;
    }
}
