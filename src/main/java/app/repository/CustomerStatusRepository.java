package app.repository;

import app.entity.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, Long>{
    @Query("SELECT c FROM CustomerStatus c where c.name = :name")
    CustomerStatus findByName(@Param("name") String name);

    @Query("SELECT c FROM CustomerStatus c ORDER BY name ASC")
    List<CustomerStatus> findAllSort();

    @Query("SELECT c FROM CustomerStatus c where c.invisible = :invisible ORDER BY name ASC ")
    List<CustomerStatus> findByInvisibleSort(@Param("invisible") Boolean invisible);

    @Query("SELECT c FROM CustomerStatus c where c.name = :name AND c.id <> :id")
    List<CustomerStatus> findByNameExceptId(@Param("name") String name,
                                         @Param("id") long id);
}
