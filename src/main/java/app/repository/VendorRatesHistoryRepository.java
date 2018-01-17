package app.repository;

import app.entity.SmppVendorIps;
import app.entity.VendorRatesHistory;
import app.entity.VendorRatesUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 07.08.2017.
 */
public interface VendorRatesHistoryRepository extends JpaRepository<VendorRatesHistory, Long> {
    @Query("SELECT c FROM VendorRatesHistory c WHERE c.date >= :date AND c.smppVendorIps = :smppVendorIps AND c.mcc = :mcc AND c.mnc = :mnc ORDER BY c.date DESC")
    List<VendorRatesHistory> getVendorRatesHistory(@Param("date") Date date,
                                                   @Param("smppVendorIps") SmppVendorIps smppVendorIps,
                                                   @Param("mcc") String mcc,
                                                   @Param("mnc") String mnc);

    @Query("SELECT c FROM VendorRatesHistory c WHERE c.vendorRatesUpdate = :vendorRatesUpdate")
    List<VendorRatesHistory> getRatesByUpdate(@Param("vendorRatesUpdate") VendorRatesUpdate vendorRatesUpdate);

    @Query("SELECT c FROM VendorRatesHistory c WHERE c.smppVendorIps = :smppVendorIps AND c.mcc = :mcc AND c.mnc = :mnc order by c.date DESC")
    List<VendorRatesHistory> getRatesBySmppVendorIpsAndMccAndMnc(@Param("smppVendorIps") SmppVendorIps smppVendorIps,
                                                                     @Param("mcc") String mcc,
                                                                     @Param("mnc") String mnc,
                                                                     Pageable pageable);

    @Query("SELECT c FROM VendorRatesHistory c WHERE c.mcc = :mcc AND c.mnc = :mnc order by c.date DESC")
    List<VendorRatesHistory> getRatesByMccAndMnc(@Param("mcc") String mcc,
                                                 @Param("mnc") String mnc,
                                                 Pageable pageable);

    @Query("SELECT c FROM VendorRatesHistory c WHERE c.date = :date AND c.smppVendorIps = :smppVendorIps AND c.mcc = :mcc AND c.mnc = :mnc AND c.vendorRatesUpdate <> :vendorRatesUpdate")
    List<VendorRatesHistory> findAllBySmppVendorIpsAndDateAndMccAndMncExceptUpdate(@Param("date") Date date,
                                                                                       @Param("smppVendorIps") SmppVendorIps smppVendorIps,
                                                                                       @Param("mcc") String mcc,
                                                                                       @Param("mnc") String mnc,
                                                                                       @Param("vendorRatesUpdate") VendorRatesUpdate vendorRatesUpdate);

    @Query("SELECT c FROM VendorRatesHistory c WHERE c.smppVendorIps = :smppVendorIps AND c.mcc = :mcc ORDER BY c.mcc, c.mnc, c.date DESC")
    List<VendorRatesHistory> fingVendorRateHistoryByAccountAndMccSort(@Param("smppVendorIps") SmppVendorIps smppVendorIps,
                                                                      @Param("mcc") String mcc);

    @Query("SELECT c FROM VendorRatesHistory c WHERE c.actualized = FALSE AND c.date <= :currentDate ORDER BY c.mcc, c.mnc, c.date DESC")
    List<VendorRatesHistory> getRatesToActulize(@Param("currentDate") Date currentDate);

    @Query("SELECT c FROM VendorRatesHistory c WHERE c.smppVendorIps = :account AND c.date > :date AND c.mcc = :mcc AND c.mnc = :mnc")
    List<VendorRatesHistory> getRatesAfterDate(@Param("account") SmppVendorIps account,
                                               @Param("mcc") String mcc,
                                               @Param("mnc") String mnc,
                                               @Param("date") Date date);
}
