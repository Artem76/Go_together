package app.repository;

import app.entity.Soa;
import app.entity.enums.DebitCredit;
import app.entity.enums.FinanceEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 03.11.2017.
 */
public interface SoaRepository extends JpaRepository<Soa, Long> {

    @Query("SELECT c FROM Soa c WHERE c.startDate = :startDate AND c.endDate = :endDate AND c.customer_id = :customerId AND c.billingTermsId = :billingTermsId AND c.debitCredit = :debitCredit AND c.event = :event")
    Soa getByPeriod(@Param("startDate") Date startDate,
                    @Param("endDate") Date endDate,
                    @Param("customerId") Long customerId,
                    @Param("billingTermsId") Long billingTermsId,
                    @Param("debitCredit")DebitCredit debitCredit,
                    @Param("event") FinanceEvents event);

    @Query("SELECT c FROM Soa c WHERE c.startDate >= :startDate AND c.endDate <= :endDate AND c.customer_id = :customerId ORDER BY c.startDate, c.endDate, c.payDate, c.event")
    List<Soa> getSoaListByFilters(@Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate,
                                  @Param("customerId") Long customerId);

    @Query("SELECT c FROM Soa c WHERE c.closed = true AND c.event = :event AND c.customer_id = :customerId")
    List<Soa> getClosedSoaByCustomerAndEvent(@Param("customerId") Long customerId,
                                             @Param("event") FinanceEvents even);
}
