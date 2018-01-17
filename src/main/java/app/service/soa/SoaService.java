package app.service.soa;

import app.entity.Soa;
import app.entity.enums.DebitCredit;
import app.entity.enums.FinanceEvents;
import app.entityXML.soa.SoaReport;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 03.11.2017.
 */
public interface SoaService {
    Soa getSoaByPeriod(Date startDate, Date endDate, Long customerId, Long billingTermsId, DebitCredit debitCredit, FinanceEvents event);
    void save(Soa soa);
    SoaReport getSoaReportByPreiodAndCustomer(Date StartDate, Date endDate, Long customerId);
    Map<Long, Double> getBalanceOnDate(Date date, List<Long> customerId);
    List<Soa> getClosedSoaByCustomerAndEvent(Long customerId, FinanceEvents event);
    Soa getSoaById(Long Id);
    void delete(Soa soa);
}
