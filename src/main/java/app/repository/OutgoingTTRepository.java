package app.repository;

import app.entity.OutgoingTT;
import app.entity.enums.TTStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 31.08.2017.
 */
public interface OutgoingTTRepository extends JpaRepository<OutgoingTT, Long> {
    @Query("SELECT c FROM OutgoingTT c WHERE c.status = :status")
    List<OutgoingTT> getTTListByStatus(@Param("status")TTStatuses status);

    @Query("SELECT c FROM OutgoingTT c WHERE c.date BETWEEN :dateStart and :dateEnd")
    List<OutgoingTT> getTTListByDateInterval(@Param("dateStart") Date dateStart,
                                             @Param("dateEnd") Date dateEnd);
}
