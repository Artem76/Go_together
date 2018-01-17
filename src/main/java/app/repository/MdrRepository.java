package app.repository;

import app.entity.Mdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by Олег on 09.07.2017.
 */
public interface MdrRepository extends JpaRepository<Mdr, String> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Mdr c SET c.delivery_status = :status WHERE c.msgid IN :msgid")
    void setStatuses(@Param("status") String status, @Param("msgid") ArrayList<String> msgid);

    @Query("SELECT c from Mdr c WHERE c.msgid IN :msgid")
    Mdr getMessageByMsgid(@Param("msgid") String msgid);

    @Query("UPDATE Mdr c SET c.submit_status = :submit_status, c.vendor_msgid = :vendor_msgid WHERE msgid = :msgid")
    void updateQueuedMessage(@Param("submit_status") String submit_status,
                             @Param("vendor_msgid") String vendor_msgid,
                             @Param("msgid") String msgid);

}
