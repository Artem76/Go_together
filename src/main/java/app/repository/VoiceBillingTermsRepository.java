package app.repository;

import app.entity.VoiceBillingTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoiceBillingTermsRepository extends JpaRepository<VoiceBillingTerms, Long>{
    @Query("SELECT c FROM VoiceBillingTerms c where c.name = :name")
    VoiceBillingTerms findByName(@Param("name") String name);

    @Query("SELECT c FROM VoiceBillingTerms c ORDER BY name ASC")
    List<VoiceBillingTerms> findAllSort();

    @Query("SELECT c FROM VoiceBillingTerms c where c.name = :name AND c.id <> :id")
    List<VoiceBillingTerms> findByNameExceptId(@Param("name") String name,
                                             @Param("id") long id);
}
