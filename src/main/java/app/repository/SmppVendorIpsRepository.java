package app.repository;

import app.entity.SmppVendorAccount;
import app.entity.SmppVendorIps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmppVendorIpsRepository extends JpaRepository<SmppVendorIps, Long> {
    @Query("SELECT c FROM SmppVendorIps c where c.systemId = :systemId")
    List<SmppVendorIps> findBySystemId(@Param("systemId") String systemId);

    @Query("SELECT c FROM SmppVendorIps c ORDER BY systemId ASC")
    List<SmppVendorIps> findAllSort();

    @Query("SELECT c FROM SmppVendorIps c where c.cid = :cid")
    SmppVendorIps findByCid(@Param("cid") String cid);

    @Query("SELECT c FROM SmppVendorIps c where c.cid = :cid AND c.id <> :id")
    List<SmppVendorIps> findByCidExceptId(@Param("cid") String cid,
                                          @Param("id") Long id);

    @Query("SELECT c FROM SmppVendorIps c where c.smppVendorAccount = :smppVendorAccount")
    List<SmppVendorIps> findByAccountId(@Param("smppVendorAccount") SmppVendorAccount smppVendorAccount);

    @Query("SELECT c FROM SmppVendorIps c where c.smppVendorAccount = :smppVendorAccount and c.allowed = TRUE")
    List<SmppVendorIps> findByAccountIdAllowed(@Param("smppVendorAccount") SmppVendorAccount smppVendorAccount);

}
