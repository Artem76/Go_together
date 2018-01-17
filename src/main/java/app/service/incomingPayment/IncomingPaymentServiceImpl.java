package app.service.incomingPayment;

import app.entity.Customer;
import app.entity.IncomingPayment;
import app.entity.Soa;
import app.entity.enums.DebitCredit;
import app.entity.enums.FinanceEvents;
import app.repository.IncomingPaymentRepository;
import app.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Олег on 04.12.2017.
 */

@Service
public class IncomingPaymentServiceImpl implements IncomingPaymentService {
    @Autowired
    private IncomingPaymentRepository incomingPaymentRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    app.service.soa.SoaService soaService;

    @Autowired
    EntityManager entityManager;

    @Override
    public Map<String,Object> getPaymentMapSortForPage(Long customerId, String dateStart, String dateEnd, Integer pageIndex, Integer pageSize){
        Map<String,Object> result = new HashMap<>();
        try{
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(dateStart);
            Date end = format.parse(dateEnd);
            List<IncomingPayment> incomingPaymentSet = null;
            boolean hasNextPage = false;
            if (customerId == 0){
                incomingPaymentSet = incomingPaymentRepository.findAllByDateBetween(start, end, pageable);
                pageable = new PageRequest(pageIndex + 1, pageSize);
                hasNextPage = incomingPaymentRepository.findAllByDateBetween(start, end, pageable).size() > 0;
            }else {
                incomingPaymentSet = incomingPaymentRepository.findAllByDateBetweenAndCustomer(start, end, customerService.getCustomerById(customerId), pageable);
                pageable = new PageRequest(pageIndex + 1, pageSize);
                hasNextPage = incomingPaymentRepository.findAllByDateBetweenAndCustomer(start, end, customerService.getCustomerById(customerId), pageable).size() > 0;
            }
            List<Map<String, String>> mapList = new ArrayList<>();
            for (IncomingPayment incomingPayment: incomingPaymentSet) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(incomingPayment.getId()));
                map.put("customerId", String.valueOf(incomingPayment.getCustomer().getId()));
                map.put("customer", incomingPayment.getCustomer().getName());
                map.put("sum", String.valueOf(incomingPayment.getSum()));
                map.put("date", format.format(incomingPayment.getDate()));
                map.put("confirmed", String.valueOf(incomingPayment.getConfirmed()));
                mapList.add(map);
            }
            result.put("payments", mapList);
            result.put("next", String.valueOf(hasNextPage));
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public void savePayment(Boolean confirmed, Long customer_id, Long payment_id, Date date, Double sum){
        IncomingPayment payment = null;
        Customer customer = customerService.getCustomerById(customer_id);
        if (payment_id == null || payment_id == 0){
            payment = new IncomingPayment(customer, sum, date, confirmed);
        }else {
            payment = incomingPaymentRepository.findOne(payment_id);
            payment.setCustomer(customer);
            payment.setSum(sum);
            payment.setDate(date);
            payment.setConfirmed(confirmed);
        }
        incomingPaymentRepository.save(payment);

        if (payment.getConfirmed()) {
            addSoaInformation(payment);
        } else {
            removeSoaInformation(payment);
        }

    }


    private void addSoaInformation(IncomingPayment payment) {
        List<Soa> soaList = entityManager.createQuery("SELECT c FROM Soa c WHERE c.event = :event AND c.eventOwnerId = :ownerId").setParameter("event", FinanceEvents.Incoming_Payment)
                .setParameter("ownerId", payment.getId()).getResultList();

        Soa currentSoa = null;
        if (soaList.size() > 0) {
            currentSoa = soaList.get(0);
            currentSoa.setSum(payment.getSum());
            currentSoa.setFinalSum(payment.getSum());
            currentSoa.setCustomer_id(payment.getCustomer().getId());
            currentSoa.setStartDate(payment.getDate());
            currentSoa.setEndDate(payment.getDate());
            currentSoa.setBillingPeriodName("Incoming payment");
            currentSoa.setBillingTermsId(payment.getCustomer().getSmsBillingTerms().getId());
        } else {
            currentSoa = new Soa();
            currentSoa.setFinalSum(payment.getSum());
            currentSoa.setSum(payment.getSum());
            currentSoa.setEventOwnerId(payment.getId());
            currentSoa.setEvent(FinanceEvents.Incoming_Payment);
            currentSoa.setDebitCredit(DebitCredit.Credit);
            currentSoa.setCustomer_id(payment.getCustomer().getId());
            currentSoa.setStartDate(payment.getDate());
            currentSoa.setEndDate(payment.getDate());
            currentSoa.setBillingPeriodName("Incoming payment");
            currentSoa.setBillingTermsId(payment.getCustomer().getSmsBillingTerms().getId());
        }
        soaService.save(currentSoa);
    }

    private void removeSoaInformation(IncomingPayment payment) {
        List<Soa> soaList = entityManager.createQuery("SELECT c FROM Soa c WHERE c.event = :event AND c.eventOwnerId = :ownerId").setParameter("event", FinanceEvents.Incoming_Payment)
                .setParameter("ownerId", payment.getId()).getResultList();
        if (soaList.size() > 0) {
            soaService.delete(soaList.get(0));
        }
    }
}
