package app.service.outgoingPayment;

import app.entity.Customer;
import app.entity.IncomingPayment;
import app.entity.OutgoingPayment;
import app.entity.Soa;
import app.entity.enums.DebitCredit;
import app.entity.enums.FinanceEvents;
import app.repository.OutgoingPaymentRepository;
import app.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Олег on 04.12.2017.
 */

@Service
public class OutgoingPaymentServiceImpl implements OutgoingPaymentService {
    @Autowired
    private OutgoingPaymentRepository outgoingPaymentRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    app.service.soa.SoaService soaService;

    @Override
    public Map<String,Object> getPaymentMapSortForPage(Long customerId, String dateStart, String dateEnd, Integer pageIndex, Integer pageSize){
        Map<String,Object> result = new HashMap<>();
        try{
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(dateStart);
            Date end = format.parse(dateEnd);
            List<OutgoingPayment> outgoingPaymentList = null;
            boolean hasNextPage = false;
            if (customerId == 0){
                outgoingPaymentList = outgoingPaymentRepository.findAllByDateBetween(start, end, pageable);
                pageable = new PageRequest(pageIndex + 1, pageSize);
                hasNextPage = outgoingPaymentRepository.findAllByDateBetween(start, end, pageable).size() > 0;
            }else {
                outgoingPaymentList = outgoingPaymentRepository.findAllByDateBetweenAndCustomer(start, end, customerService.getCustomerById(customerId), pageable);
                pageable = new PageRequest(pageIndex + 1, pageSize);
                hasNextPage = outgoingPaymentRepository.findAllByDateBetweenAndCustomer(start, end, customerService.getCustomerById(customerId), pageable).size() > 0;
            }
            List<Map<String, String>> mapList = new ArrayList<>();
            for (OutgoingPayment outgoingPayment: outgoingPaymentList) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(outgoingPayment.getId()));
                map.put("customerId", String.valueOf(outgoingPayment.getCustomer().getId()));
                map.put("customer", outgoingPayment.getCustomer().getName());
                map.put("sum", String.valueOf(outgoingPayment.getSum()));
                map.put("date", format.format(outgoingPayment.getDate()));
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
    public void savePayment(Long customer_id, Long payment_id, Date date, Double sum){
        OutgoingPayment payment = null;
        Customer customer = customerService.getCustomerById(customer_id);
        if (payment_id == null || payment_id == 0){
            payment = new OutgoingPayment(customer, sum, date);
        }else {
            payment = outgoingPaymentRepository.findOne(payment_id);
            payment.setCustomer(customer);
            payment.setSum(sum);
            payment.setDate(date);
        }
        outgoingPaymentRepository.save(payment);

        addSoaInformation(payment);

    }

    private void addSoaInformation(OutgoingPayment payment) {
        List<Soa> soaList = entityManager.createQuery("SELECT c FROM Soa c WHERE c.event = :event AND c.eventOwnerId = :ownerId").setParameter("event", FinanceEvents.Outgoing_Payment)
                .setParameter("ownerId", payment.getId()).getResultList();

        Soa currentSoa = null;
        if (soaList.size() > 0) {
            currentSoa = soaList.get(0);
            currentSoa.setSum(payment.getSum());
            currentSoa.setFinalSum(payment.getSum());
            currentSoa.setCustomer_id(payment.getCustomer().getId());
            currentSoa.setStartDate(payment.getDate());
            currentSoa.setEndDate(payment.getDate());
            currentSoa.setBillingPeriodName("Outgoing payment");
            currentSoa.setBillingTermsId(payment.getCustomer().getSmsBillingTerms().getId());
        } else {
            currentSoa = new Soa();
            currentSoa.setFinalSum(payment.getSum());
            currentSoa.setSum(payment.getSum());
            currentSoa.setEventOwnerId(payment.getId());
            currentSoa.setEvent(FinanceEvents.Outgoing_Payment);
            currentSoa.setDebitCredit(DebitCredit.Debit);
            currentSoa.setCustomer_id(payment.getCustomer().getId());
            currentSoa.setStartDate(payment.getDate());
            currentSoa.setEndDate(payment.getDate());
            currentSoa.setBillingPeriodName("Outgoing payment");
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
