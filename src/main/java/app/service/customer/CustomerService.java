package app.service.customer;

import app.entity.*;
import app.entity.enums.ContractTypes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<Customer> getCustomerAll();
    List<Customer> getCustomerAllSort();
    List<Customer> getCustomerByStatus(CustomerStatus status);
    List<Customer> getCustomerExceptStatus(CustomerStatus status);

    boolean hasCustomer(String name, Long id);

    Customer getCustomerById(Long id);
    Customer getCustomerByName(String name);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Long id, String name, String address, String fullName, String signerName, String signerTitle, String phone, String email, String website, String registrationNumber, String vat, CustomUser manager, CustomerStatus status, SmsBillingTerms smsBillingTerms, VoiceBillingTerms voiceBillingTerms, String smsSupportEmail, String smsInvoiceEmail, String smsRateModeEmail, String smsDisputeEmail, String voiceSupportEmail, String voiceInvoiceEmail, String voiceRateModeEmail, String voiceDisputeEmail, Long company_id, String dateFormat, String fakeName, ContractTypes contractType, Double creditLimit, Double minimalPayment);

    @Transactional
    Customer save(Customer customer);

    List<Map> getMapCustomerAttachment(Long id_customer);
}
