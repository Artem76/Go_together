package app.repository;

import app.entity.SmppClientAccount;
import app.entity.SmppClientSystemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Олег on 29.07.2017.
 */
public interface SmppClientSystemIdRepository extends JpaRepository<SmppClientSystemId, Long>{
    @Query("SELECT c FROM SmppClientSystemId c where c.uid = :uid")
    SmppClientSystemId findSmppClientSystemIdByUid(@Param("uid") String uid);

    @Query("SELECT c FROM SmppClientSystemId c where c.systemId = :systemId")
    SmppClientSystemId findSmppClientSystemIdBySystemId(@Param("systemId") String systemId);

    @Query("SELECT c FROM SmppClientSystemId c where c.uid = :uid AND c.id <> :id")
    List<SmppClientSystemId> findSmppClientSystemIdByUidExceptId(@Param("uid") String uid,
                                                                @Param("id") Long id);

    @Query("SELECT c FROM SmppClientSystemId c where c.systemId = :systemId AND c.id <> :id")
    List<SmppClientSystemId> findSmppClientSystemIdBySystemIdExceptId(@Param("systemId") String systemId,
                                                                 @Param("id") Long id);

    @Query("SELECT c FROM SmppClientSystemId c WHERE c.smppClientAccount = :smppClientAccount")
    List<SmppClientSystemId> findSmppClientSystemIdsByAccount(@Param("smppClientAccount") SmppClientAccount smppClientAccount);
}
