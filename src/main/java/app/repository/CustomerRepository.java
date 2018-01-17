package app.repository;

import app.entity.Customer;
import app.entity.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c ORDER BY name ASC")
    List<Customer> findAllSort();

    @Query("SELECT c FROM Customer c where c.status = :status ORDER BY c.name")
    List<Customer> findByStatus(@Param("status") CustomerStatus status);

    @Query("SELECT c FROM Customer c where c.status <> :status ORDER BY c.name")
    List<Customer> findExceptStatus(@Param("status") CustomerStatus status);

    @Query("SELECT c FROM Customer c where c.name = :name")
    Customer findByName(@Param("name") String name);

    @Query("SELECT c FROM Customer c where c.name = :name AND c.id <> :id")
    List<Customer> findByNameExceptId(@Param("name") String name,
                                            @Param("id") long id);
}
