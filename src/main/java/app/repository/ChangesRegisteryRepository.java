package app.repository;

import app.entity.ChangesRegistery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Олег on 01.08.2017.
 */
public interface ChangesRegisteryRepository extends JpaRepository<ChangesRegistery, Long> {
    @Query("SELECT c FROM ChangesRegistery c WHERE c.softswitch_id = :softswitch_id AND c.values = :values")
    ChangesRegistery getChangesRowByKeyFields(@Param("softswitch_id") Long softswitch_id,
                                              @Param("values") String values);

    @Query("SELECT c FROM ChangesRegistery c WHERE c.softswitch_id = :softswitch_id")
    List<ChangesRegistery> getChangesBySoftswitch(@Param("softswitch_id") Long softswitch_id);
}
