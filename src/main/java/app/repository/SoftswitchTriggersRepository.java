package app.repository;

import app.entity.SoftswitchTriggers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Олег on 05.09.2017.
 */
public interface SoftswitchTriggersRepository extends JpaRepository<SoftswitchTriggers, Long> {
    @Query("SELECT c FROM SoftswitchTriggers c WHERE c.softswitch_id = :softswitch_id AND c.key = :key")
    SoftswitchTriggers getTriggersBySoftswitchIdAndKey(@Param("softswitch_id") Long softswitch_id,
                                                       @Param("key") String key);

}
