package app.repository;

import app.entity.TotalsSmsRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 09.07.2017.
 */
public interface TotalsSmsRepository extends JpaRepository<TotalsSmsRow, Long> {

    @Query("SELECT c from TotalsSmsRow c WHERE c.created_at >= :date")
    List<TotalsSmsRow> findTotalsByDate(@Param("date") Date date);

    @Query("SELECT c from TotalsSmsRow c WHERE c.created_at = :date and c.mcc = :mcc and c.mnc = :mnc and c.uid = :uid and c.routed_cid = :routed_cid and c.softswitch_id = :softswitch_id")
    TotalsSmsRow getTotalsByKeyFields(@Param("date") Date date,
                                       @Param("mcc") String mcc,
                                       @Param("mnc") String mnc,
                                       @Param("uid") String uid,
                                       @Param("routed_cid") String routed_cid,
                                       @Param("softswitch_id") long softswitch_id);

    @Query("SELECT c FROM TotalsSmsRow  c WHERE c.created_at BETWEEN :startDate AND :endDate")
    List<TotalsSmsRow> getTotalsSmsByDate(@Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate);
}
