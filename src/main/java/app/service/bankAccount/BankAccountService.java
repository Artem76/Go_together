package app.service.bankAccount;

import app.entity.BankAccount;

import java.util.List;

/**
 * Created by Олег on 20.10.2017.
 */
public interface BankAccountService  {
    void save(BankAccount bankAccount);
    List<BankAccount> getAll();
    BankAccount getById(Long id);

}
