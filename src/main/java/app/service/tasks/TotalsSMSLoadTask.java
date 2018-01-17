package app.service.tasks;

import app.entity.TotalsCalculationScheduler;
import app.service.logger.LoggerService;
import app.service.totalsCalculationScheduler.TotalsCalculationSchedulerService;
import app.service.totalsSms.TotalsSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class TotalsSMSLoadTask {

    @Autowired
    TotalsSmsService totalsSmsService;

    @Autowired
    TotalsCalculationSchedulerService totalsCalculationSchedulerService;

    @Autowired
    LoggerService loggerService;

    public void runParallelStream() {

        loggerService.writeInfo("Totals update task started");

        List<TotalsCalculationScheduler> list = totalsCalculationSchedulerService.getListDatesOnly();
        for (TotalsCalculationScheduler currentScheduler : list) {
            totalsSmsService.recalculateTotalsByDates(currentScheduler.getDateStart(), currentScheduler.getDateEnd());
            totalsCalculationSchedulerService.delete(currentScheduler);
        }

        loggerService.writeInfo("Totals update task finished");

    }



}
