package app.service.totalsCalculationScheduler;

import app.entity.TotalsCalculationScheduler;
import app.repository.TotalsCalculationSchedulerRepository;
import app.service.totalsSms.TotalsSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 13.12.2017.
 */

@Service
public class TotalsCalculationSchedulerServiceImpl implements TotalsCalculationSchedulerService{

    @Autowired
    TotalsCalculationSchedulerRepository totalsCalculationSchedulerRepository;

    @Override
    public void updateTotalsScheduler(Date startDate, Date endDate, Long customerId) {
        List<TotalsCalculationScheduler> list = new ArrayList<>();
        if (customerId == null) {
            list = totalsCalculationSchedulerRepository.getListByDates(startDate, endDate);
        } else {
            list = totalsCalculationSchedulerRepository.getListByDatesAndCustomer(startDate, endDate, customerId);
        }

        if (list.size() == 0) {
            TotalsCalculationScheduler curretScheduler = new TotalsCalculationScheduler();
            curretScheduler.setDateStart(startDate);
            curretScheduler.setDateEnd(endDate);
            if (customerId != null) {
                curretScheduler.setCustomer_id(customerId);
            }
            totalsCalculationSchedulerRepository.save(curretScheduler);

        }
    }

    @Override
    public List<TotalsCalculationScheduler> getListDatesOnly() {
        return totalsCalculationSchedulerRepository.getListDatesOnly();
    }

    @Override
    public List<TotalsCalculationScheduler> getListFilledCustomer() {
        return totalsCalculationSchedulerRepository.getListFilledCustomerOnly();
    }

    @Override
    public void delete(TotalsCalculationScheduler totalsCalculationScheduler) {
        totalsCalculationSchedulerRepository.delete(totalsCalculationScheduler);
    }

}
