package app.repository;

import app.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Олег on 20.10.2017.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query("SELECT c FROM BankAccount c")
    List<BankAccount> getAll();

}
