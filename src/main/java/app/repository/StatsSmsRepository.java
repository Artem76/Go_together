package app.repository;

import app.entity.StatsSmsRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Олег on 10.07.2017.
 */
public interface StatsSmsRepository extends JpaRepository<StatsSmsRow, Long> {

    @Query("SELECT c from StatsSmsRow c WHERE c.created_at = :date and c.mcc = :mcc and c.mnc = :mnc and c.uid = :uid and c.routed_cid = :routed_cid and c.status = :status and c.softswitch_id = :softswitch_id")
    StatsSmsRow getStatsByKeyFields(@Param("date") Date date,
                                    @Param("mcc") String mcc,
                                    @Param("mnc") String mnc,
                                    @Param("uid") String uid,
                                    @Param("routed_cid") String routed_cid,
                                    @Param("status") String status,
                                    @Param("softswitch_id") long softswitch_id);
}
