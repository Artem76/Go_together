package app.service.smppClientAccount;

import app.entity.Customer;
import app.entity.SmppClientAccount;

import java.util.List;

public interface SmppClientAccountService {
    boolean isName(Long id, String name, Customer c);
    List<SmppClientAccount> getSmppClientAccountAll();
    SmppClientAccount getSmppClientAccountById(Long id);
    SmppClientAccount getSmppClientAccountByName(String name);
    List<SmppClientAccount> getSmppClientAccountByCustomerSort(Customer customer);
    List<SmppClientAccount> getSmppClientAccountAllSort();
    SmppClientAccount addSmppClientAccount(String name, String systemType, Long submitThroughput, Customer customer, boolean dontSync);
    SmppClientAccount updateSmppClientAccount(Long id, String name, String systemType, Long submitThroughput, Customer customer, boolean dontSync);

    void save(SmppClientAccount smppClientAccount);
}
