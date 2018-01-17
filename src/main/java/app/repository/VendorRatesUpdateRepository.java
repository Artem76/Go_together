package app.repository;

import app.entity.SmppVendorIps;
import app.entity.VendorRatesUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by АРТЕМ on 13.11.2017.
 */
public interface VendorRatesUpdateRepository extends JpaRepository<VendorRatesUpdate, Long> {
    @Query("SELECT c from ClientRatesUpdate c WHERE c.date BETWEEN :startDate AND :endDate")
    List<VendorRatesUpdate> getRateUpdatesByDate(@Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate);

    @Query("SELECT c FROM VendorRatesUpdate c order by c.date desc")
    List<VendorRatesUpdate> findAllForPage(Pageable pageable);

    @Query("SELECT c FROM VendorRatesUpdate c WHERE c.smppVendorIps=:smppVendorIps order by c.date desc")
    List<VendorRatesUpdate> findBySmppVendorIpsForPage(@Param("smppVendorIps") SmppVendorIps smppVendorIps,
                                                           Pageable pageable);

    @Query("SELECT c FROM VendorRatesUpdate c WHERE c.smppVendorIps=:smppVendorIps order by c.date desc")
    List<VendorRatesUpdate> findBySmppVendorIps(@Param("smppVendorIps") SmppVendorIps smppVendorIps);
}
