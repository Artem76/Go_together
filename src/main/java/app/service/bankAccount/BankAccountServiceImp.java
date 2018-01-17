package app.service.bankAccount;

import app.entity.BankAccount;
import app.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 20.10.2017.
 */
@Service
public class BankAccountServiceImp implements BankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Override
    public void save(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getAll() {
        return bankAccountRepository.getAll();
    }

    @Override
    public BankAccount getById(Long id) {
        return bankAccountRepository.findOne(id);
    }


}
