package app.repository;


import app.entity.Customer;
import app.entity.SmppClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmppClientAccountRepository extends JpaRepository<SmppClientAccount, Long>{
    @Query("SELECT c FROM SmppClientAccount c where c.name = :name")
    SmppClientAccount findByName(@Param("name") String name);

    @Query("SELECT c FROM SmppClientAccount c where c.name = :name AND c.customer = :customer")
    SmppClientAccount findByNameAndCustomer(@Param("name") String name,
                                            @Param("customer") Customer customer);

    @Query("SELECT c FROM SmppClientAccount c where c.customer = :customer ORDER BY name ASC")
    List<SmppClientAccount> findByCustomerSort(@Param("customer") Customer customer);

    @Query("SELECT c FROM SmppClientAccount c ORDER BY name ASC")
    List<SmppClientAccount> findAllSort();

    @Query("SELECT c FROM SmppClientAccount c where c.name = :name AND c.id <> :id AND c.customer = :customer")
    List<SmppClientAccount> findByNameExceptId(@Param("name") String name,
                                               @Param("id") long id,
                                               @Param("customer") Customer customer);

    @Query("SELECT c FROM SmppClientAccount c WHERE c.id = :id")
    SmppClientAccount findById(@Param("id") Long id);

}
