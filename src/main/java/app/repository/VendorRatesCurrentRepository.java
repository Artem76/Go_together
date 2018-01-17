package app.repository;

import app.entity.SmppClientAccount;
import app.entity.SmppVendorAccount;
import app.entity.SmppVendorIps;
import app.entity.VendorRatesCurrent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Олег on 07.08.2017.
 */
public interface VendorRatesCurrentRepository extends JpaRepository<VendorRatesCurrent, Long> {
    @Query("SELECT c FROM VendorRatesCurrent c WHERE c.smppVendorIps = :smppVendorIps AND c.mcc = :mcc AND c.mnc = :mnc")
    VendorRatesCurrent getRate(@Param("smppVendorIps") SmppVendorIps smppVendorIps,
                               @Param("mcc") String mcc,
                               @Param("mnc") String mnc);
}
