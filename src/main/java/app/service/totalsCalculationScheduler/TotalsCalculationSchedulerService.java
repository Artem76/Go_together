package app.service.totalsCalculationScheduler;

import app.entity.TotalsCalculationScheduler;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 13.12.2017.
 */
public interface TotalsCalculationSchedulerService {

    void updateTotalsScheduler(Date startDate, Date endDate, Long customerId);
    List<TotalsCalculationScheduler> getListDatesOnly();
    List<TotalsCalculationScheduler> getListFilledCustomer();
    void delete(TotalsCalculationScheduler totalsCalculationScheduler);

}
