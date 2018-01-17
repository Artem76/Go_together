package app.repository;

import app.entity.ClientRatesHistory;
import app.entity.ClientRatesUpdate;
import app.entity.SmppClientAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 07.08.2017.
 */
public interface ClientRatesHistoryRepository extends JpaRepository<ClientRatesHistory, Long> {
    @Query("SELECT c FROM ClientRatesHistory c WHERE c.date >= :date AND c.smppClientAccount = :smppClientAccount AND c.mcc = :mcc AND c.mnc = :mnc ORDER BY c.date DESC")
    List<ClientRatesHistory> getClientRatesHistory(@Param("date") Date date,
                                                   @Param("smppClientAccount") SmppClientAccount smppClientAccount,
                                                   @Param("mcc") String mcc,
                                                   @Param("mnc") String mnc);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.clientRatesUpdate = :clientRatesUpdate")
    List<ClientRatesHistory> getRatesByUpdate(@Param("clientRatesUpdate") ClientRatesUpdate clientRatesUpdate);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.smppClientAccount = :smppClientAccount AND c.mcc = :mcc AND c.mnc = :mnc order by c.date DESC")
    List<ClientRatesHistory> getRatesBySmppClientAccountAndMccAndMnc(@Param("smppClientAccount") SmppClientAccount smppClientAccount,
                                                                     @Param("mcc") String mcc,
                                                                     @Param("mnc") String mnc,
                                                                     Pageable pageable);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.mcc = :mcc AND c.mnc = :mnc order by c.date DESC")
    List<ClientRatesHistory> getRatesByMccAndMnc(@Param("mcc") String mcc,
                                                 @Param("mnc") String mnc,
                                                 Pageable pageable);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.date = :date AND c.smppClientAccount = :smppClientAccount AND c.mcc = :mcc AND c.mnc = :mnc AND c.clientRatesUpdate <> :clientRatesUpdate")
    List<ClientRatesHistory> findAllBySmppClientAccountAndDateAndMccAndMncExceptUpdate(@Param("date") Date date,
                                                                                       @Param("smppClientAccount") SmppClientAccount smppClientAccount,
                                                                                       @Param("mcc") String mcc,
                                                                                       @Param("mnc") String mnc,
                                                                                       @Param("clientRatesUpdate") ClientRatesUpdate clientRatesUpdate);

    ClientRatesHistory findFirstByMccAndMncAndSmppClientAccountAndAppliedAndDateBeforeOrderByDateDesc(@Param("mcc") String mcc,
                                                                                                      @Param("mnc") String mnc,
                                                                                                      @Param("smppClientAccount") SmppClientAccount smppClientAccount,
                                                                                                      @Param("applied") Boolean applied,
                                                                                                      @Param("date") Date date);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.smppClientAccount = :smppClientAccount AND c.mcc = :mcc")
    List<ClientRatesHistory> fingClientRateHistoryByAccountAndMcc(@Param("smppClientAccount") SmppClientAccount smppClientAccount,
                                                                  @Param("mcc") String mcc);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.smppClientAccount = :smppClientAccount AND c.mcc = :mcc ORDER BY c.mcc, c.mnc, c.date DESC")
    List<ClientRatesHistory> fingClientRateHistoryByAccountAndMccSort(@Param("smppClientAccount") SmppClientAccount smppClientAccount,
                                                                  @Param("mcc") String mcc);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.actualized = FALSE AND c.date <= :currentDate AND c.applied = TRUE ORDER BY c.mcc, c.mnc, c.date DESC")
    List<ClientRatesHistory> getRatesToActulize(@Param("currentDate") Date currentDate);

    @Query("SELECT c FROM ClientRatesHistory c WHERE c.smppClientAccount = :account AND c.date > :date AND c.mcc = :mcc AND c.mnc = :mnc")
    List<ClientRatesHistory> getRatesAfterDate(@Param("account") SmppClientAccount account,
                                               @Param("mcc") String mcc,
                                               @Param("mnc") String mnc,
                                               @Param("date") Date date);
}
