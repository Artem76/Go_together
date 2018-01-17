package app.repository;

import app.entity.Customer;
import app.entity.SmppVendorAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmppVendorAccountRepository extends JpaRepository<SmppVendorAccount, Long> {
    @Query("SELECT c FROM SmppVendorAccount c where c.name = :name")
    SmppVendorAccount findByName(@Param("name") String name);

    @Query("SELECT c FROM SmppVendorAccount c where c.name = :name AND c.customer = :customer")
    SmppVendorAccount findByNameAndCustomer(@Param("name") String name,
                                            @Param("customer") Customer customer);

    @Query("SELECT c FROM SmppVendorAccount c where c.tag = :tag")
    SmppVendorAccount findByTag(@Param("tag") Integer tag);

    @Query("SELECT c FROM SmppVendorAccount c where c.customer = :customer ORDER BY name ASC")
    List<SmppVendorAccount> findByCustomerSort(@Param("customer") Customer customer);

    @Query("SELECT c FROM SmppVendorAccount c ORDER BY name ASC")
    List<SmppVendorAccount> findAllSort();

    @Query("SELECT c FROM SmppVendorAccount c where c.name = :name AND c.id <> :id AND c.customer = :customer")
    List<SmppVendorAccount> findByNameExceptId(@Param("name") String name,
                                               @Param("id") long id,
                                               @Param("customer") Customer customer);

    @Query("SELECT c FROM SmppVendorAccount c where c.tag = :tag AND c.id <> :id")
    List<SmppVendorAccount> findByTagExceptId(@Param("tag") Integer tag,
                                              @Param("id") long id);

    @Query("SELECT c FROM SmppVendorAccount c WHERE NOT (c.tag IS NULL) ORDER BY c.tag DESC")
    List<SmppVendorAccount> getLastVendorTagAccount();

    @Query("SELECT c FROM SmppVendorAccount c WHERE c.tag = :tag")
    SmppVendorAccount getAccountByTag(@Param("tag") Integer tag);

}
