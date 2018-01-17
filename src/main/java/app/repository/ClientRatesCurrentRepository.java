package app.repository;

import app.entity.ClientRatesCurrent;
import app.entity.SmppClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Олег on 07.08.2017.
 */
public interface ClientRatesCurrentRepository extends JpaRepository<ClientRatesCurrent, Long> {
    @Query("SELECT c FROM ClientRatesCurrent c WHERE c.smppClientAccount = :smppClientAccount AND c.mcc = :mcc AND c.mnc = :mnc")
    ClientRatesCurrent getRate(@Param("smppClientAccount") SmppClientAccount smppClientAccount,
                               @Param("mcc") String mcc,
                               @Param("mnc") String mnc);
}
