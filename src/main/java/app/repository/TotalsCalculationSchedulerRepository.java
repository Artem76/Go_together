package app.repository;

import app.entity.TotalsCalculationScheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 13.12.2017.
 */
public interface TotalsCalculationSchedulerRepository extends JpaRepository<TotalsCalculationScheduler, Long> {

    @Query("SELECT c FROM TotalsCalculationScheduler c WHERE c.dateStart = :dateStart AND c.dateEnd = :dateEnd AND c.customer_id = NULL")
    List<TotalsCalculationScheduler> getListByDates(@Param("dateStart") Date dateStart,
                                                    @Param("dateEnd") Date dateEnd);

    @Query("SELECT c FROM TotalsCalculationScheduler c WHERE c.dateStart = :dateStart AND c.dateEnd = :dateEnd AND c.customer_id = :customerId")
    List<TotalsCalculationScheduler> getListByDatesAndCustomer(@Param("dateStart") Date dateStart,
                                                               @Param("dateEnd") Date dateEnd,
                                                               @Param("customerId") Long customerId);

    @Query("SELECT c FROM TotalsCalculationScheduler c WHERE c.customer_id = NULL")
    List<TotalsCalculationScheduler> getListDatesOnly();

    @Query("SELECT c FROM TotalsCalculationScheduler c WHERE c.customer_id <> NULL")
    List<TotalsCalculationScheduler> getListFilledCustomerOnly();


}
