package app.service.soa;

import app.entity.Customer;
import app.entity.SmsBillingTerms;
import app.entity.Soa;
import app.entity.enums.DebitCredit;
import app.entity.enums.FinanceEvents;
import app.entityXML.soa.SoaReport;
import app.repository.SoaRepository;
import app.service.customer.CustomerService;
import app.service.smsBillingTerms.SmsBillingTermsService;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Олег on 03.11.2017.
 */
@Service
public class SoaServiceImpl implements app.service.soa.SoaService {

    @Autowired
    SoaRepository soaRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SmsBillingTermsService smsBillingTermsService;

    @Override
    public Soa getSoaByPeriod(Date startDate, Date endDate, Long customerId, Long billingTermsId, DebitCredit debitCredit, FinanceEvents event) {
        return soaRepository.getByPeriod(startDate, endDate, customerId, billingTermsId, debitCredit, event);
    }

    @Override
    public void save(Soa soa) {
        soaRepository.save(soa);
    }

    @Override
    public SoaReport getSoaReportByPreiodAndCustomer(Date startDate, Date endDate, Long customerId) {
        SoaReport returnReport = new SoaReport();

        List<Soa> returnList = new ArrayList<>();

        Customer currentCustomer = customerService.getCustomerById(customerId);
        if (currentCustomer == null) {
            return returnReport;
        }
        SmsBillingTerms currentBillingterms = currentCustomer.getSmsBillingTerms();
        if (currentBillingterms == null) {
            return returnReport;
        }
        Date reportStartDate = currentBillingterms.getBillingTermsStartDateByDate(startDate);
        Date reportEndDate = currentBillingterms.getBillingTermsEndDateBbyDate(endDate);

        List<Soa> soaList = soaRepository.getSoaListByFilters(reportStartDate, reportEndDate, customerId);
        Date lastStartDate = new Date(0);
        Date lastEnddate = new Date(0);
        Date lastPaydate = new Date(0);
        FinanceEvents lastEvent = null;

        for (Soa currentSoa : soaList) {
            currentSoa.setBillingPeriodName(smsBillingTermsService.getSmsBillingTermsById(currentSoa.getBillingTermsId()).getName());
            if (currentSoa.getEvent() == FinanceEvents.Current_Traffic && lastStartDate.equals(currentSoa.getStartDate())
                    && lastEnddate.equals(currentSoa.getEndDate()) && lastPaydate.equals(currentSoa.getPayDate()) && lastEvent == currentSoa.getEvent()) {

                Soa addedSoa = returnList.get(returnList.size() - 1);
                if (currentSoa.getDebitCredit() == DebitCredit.Debit) {
                    addedSoa.setSum(currentSoa.getSum());
                } else {
                    addedSoa.setSumAdditional(currentSoa.getSum());
                }
            } else {
                if (currentSoa.getEvent() == FinanceEvents.Current_Traffic) {
                    if (currentSoa.getDebitCredit() == DebitCredit.Credit) {
                        currentSoa.setSumAdditional(currentSoa.getSum());
                        currentSoa.setSum(0.00);

                    } else {
                        currentSoa.setSumAdditional(0.00);
                        currentSoa.setSum(currentSoa.getSum());
                    }
                    returnList.add(currentSoa);
                } else {
                    returnList.add(currentSoa);
                }
            }
            lastStartDate = currentSoa.getStartDate();
            lastEnddate = currentSoa.getEndDate();
            lastPaydate = currentSoa.getPayDate();
            lastEvent = currentSoa.getEvent();
        }

        returnReport.setList(returnList);

        List<Long> customerIds = new ArrayList<>();
        customerIds.add(customerId);

        Map<Long, Double> customerBalanceMap = getBalanceOnDate(reportStartDate, customerIds);

        if (customerBalanceMap.size() != 0) {
            returnReport.setStartBalance(customerBalanceMap.get(customerId));
        } else {
            returnReport.setStartBalance(0.00);
        }

        return returnReport;
    }

    @Override
    public Map<Long, Double> getBalanceOnDate(Date date, List<Long> customerIds) {
        Double balance = 0.0;

        Map<Long, Double> returnMap = new HashMap<>();

        Session session = entityManager.unwrap(Session.class);
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        ConnectionProvider cp = sfi.getConnectionProvider();

        try {
            Connection conn = cp.getConnection();
            Statement statement = conn.createStatement();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = formatter.format(date);

            String customerIdCondition = "";

            Integer i = 1;
            for (Long currentId : customerIds) {
                customerIdCondition = customerIdCondition + "" + currentId + "";
                if (i != customerIds.size()) {
                    customerIdCondition = customerIdCondition + ", ";
                }
                i++;
            }

            String dateCondition = "";
            if (!date.equals(new Date(0))) {
                dateCondition = "AND start_date < '" + format +"' ";
            }

            ResultSet resultSet = statement.executeQuery("SELECT customer_id, SUM(sums) AS balance FROM (SELECT CASE WHEN debit_credit = 0 THEN final_sum ELSE -1 * final_sum END AS sums, customer_id FROM soa WHERE customer_id IN (" + customerIdCondition +")" + dateCondition + ") AS Data GROUP BY data.customer_id");
            while (resultSet.next()) {
                returnMap.put(resultSet.getLong("customer_id"), resultSet.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnMap;
    }

    @Override
    public List<Soa> getClosedSoaByCustomerAndEvent(Long customerId, FinanceEvents event) {
        return soaRepository.getClosedSoaByCustomerAndEvent(customerId, event);
    }

    @Override
    public Soa getSoaById(Long id) {
        return soaRepository.findOne(id);
    }

    public void delete(Soa soa) {
        soaRepository.delete(soa);
    }
}
