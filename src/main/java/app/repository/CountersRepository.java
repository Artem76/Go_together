package app.repository;

import app.entity.Counters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Олег on 11.12.2017.
 */
public interface CountersRepository extends JpaRepository<Counters, Long> {

    @Query("SELECT c FROM Counters c WHERE c.key = :key")
    Counters getCounterByKey(@Param("key") String key);

}
