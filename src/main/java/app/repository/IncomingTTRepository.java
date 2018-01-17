package app.repository;

import app.entity.IncomingTT;
import app.entity.enums.TTStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 29.08.2017.
 */
public interface IncomingTTRepository extends JpaRepository<IncomingTT, Long> {
    @Query("SELECT c FROM IncomingTT c WHERE c.status = :status")
    List<IncomingTT> getTTListByStatus(@Param("status")TTStatuses status);

    @Query("SELECT c FROM IncomingTT c WHERE c.date BETWEEN :dateStart and :dateEnd")
    List<IncomingTT> getTTListByDateInterval(@Param("dateStart") Date dateStart,
                                             @Param("dateEnd") Date dateEnd);

}
