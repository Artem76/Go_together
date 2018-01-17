package app.repository;

import app.entity.ClientRatesUpdate;
import app.entity.SmppClientAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 23.09.2017.
 */
public interface ClientRatesUpdateRepository extends JpaRepository<ClientRatesUpdate, Long> {
    @Query("SELECT c from ClientRatesUpdate c WHERE c.date BETWEEN :startDate AND :endDate")
    List<ClientRatesUpdate> getRateUpdatesByDate(@Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate);

    @Query("SELECT c FROM ClientRatesUpdate c order by c.date desc")
    List<ClientRatesUpdate> findAllForPage(Pageable pageable);

    @Query("SELECT c FROM ClientRatesUpdate c WHERE c.smppClientAccount=:smppClientAccount order by c.date desc")
    List<ClientRatesUpdate> findBySmppClientAccountForPage(@Param("smppClientAccount") SmppClientAccount smppClientAccount,
                                                           Pageable pageable);

    @Query("SELECT c FROM ClientRatesUpdate c WHERE c.smppClientAccount=:smppClientAccount order by c.date desc")
    List<ClientRatesUpdate> findBySmppClientAccount(@Param("smppClientAccount") SmppClientAccount smppClientAccount);
}
