package app.repository;

import app.entity.SmsBillingTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmsBillingTermsRepository extends JpaRepository<SmsBillingTerms, Long>{
    @Query("SELECT c FROM SmsBillingTerms c where c.name = :name")
    SmsBillingTerms findByName(@Param("name") String name);

    @Query("SELECT c FROM SmsBillingTerms c ORDER BY name ASC")
    List<SmsBillingTerms> findAllSort();

    @Query("SELECT c FROM SmsBillingTerms c where c.name = :name AND c.id <> :id")
    List<SmsBillingTerms> findByNameExceptId(@Param("name") String name,
                                            @Param("id") long id);
}
