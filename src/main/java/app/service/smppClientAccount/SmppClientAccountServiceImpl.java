package app.service.smppClientAccount;

import app.entity.Customer;
import app.entity.SmppClientAccount;
import app.repository.SmppClientAccountRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmppClientAccountServiceImpl implements SmppClientAccountService{
    @Autowired
    SmppClientAccountRepository smppClientAccountRepository;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Override
    public boolean isName(Long id, String name, Customer customer) {
        return smppClientAccountRepository.findByNameExceptId(name, id, customer).size() != 0;
    }

    @Override
    public List<SmppClientAccount> getSmppClientAccountAll() {
        return smppClientAccountRepository.findAll();
    }

    @Override
    public SmppClientAccount getSmppClientAccountById(Long id) {
        return smppClientAccountRepository.findById(id);
    }

    @Override
    public SmppClientAccount getSmppClientAccountByName(String name) {
        return smppClientAccountRepository.findByName(name);
    }

    @Override
    public List<SmppClientAccount> getSmppClientAccountByCustomerSort(Customer customer) {
        return smppClientAccountRepository.findByCustomerSort(customer);
    }

    @Override
    public List<SmppClientAccount> getSmppClientAccountAllSort() {
        return smppClientAccountRepository.findAllSort();
    }

    @Override
    @Transactional
    public SmppClientAccount addSmppClientAccount(String name, String systemType, Long submitThroughput, Customer customer, boolean dontSync) {
        if (name.equals("") || smppClientAccountRepository.findByNameAndCustomer(name, customer) != null) {
            return null;
        }
        SmppClientAccount smppClientAccount = new SmppClientAccount(name, systemType, submitThroughput, customer);
        smppClientAccount.setDontSync(dontSync);
        smppClientAccountRepository.save(smppClientAccount);
        changesRegisteryService.registerChangesAllSoftswitches("smppclientaccount" + "_" + smppClientAccount.getId());
        return smppClientAccount;
    }

    @Override
    @Transactional
    public SmppClientAccount updateSmppClientAccount(Long id, String name, String systemType, Long submitThroughput, Customer customer, boolean dontSync) {
        if (!name.equals("") && smppClientAccountRepository.findByNameExceptId(name, id, customer).size() == 0){
            SmppClientAccount smppClientAccount = smppClientAccountRepository.findOne(id);
            smppClientAccount.setName(name);
            smppClientAccount.setSystemType(systemType);
            smppClientAccount.setSubmitThroughput(submitThroughput);
            smppClientAccount.setCustomer(customer);
            smppClientAccount.setDontSync(dontSync);
            smppClientAccountRepository.save(smppClientAccount);
            changesRegisteryService.registerChangesAllSoftswitches("smppclientaccount" + "_" + smppClientAccount.getId());
            return smppClientAccount;
        }
        return null;
    }

    @Override
    public void save(SmppClientAccount smppClientAccount){
        smppClientAccountRepository.saveAndFlush(smppClientAccount);
    }
}
