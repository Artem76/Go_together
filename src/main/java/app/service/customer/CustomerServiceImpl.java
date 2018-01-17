package app.service.customer;

import app.entity.*;
import app.entity.enums.ContractTypes;
import app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getCustomerAllSort() {
        return customerRepository.findAllSort();
    }

    @Override
    public List<Customer> getCustomerByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    @Override
    public List<Customer> getCustomerExceptStatus(CustomerStatus status) {
        return customerRepository.findExceptStatus(status);
    }

    @Override
    public boolean hasCustomer(String name, Long id){
        return customerRepository.findByNameExceptId(name, id).size() != 0;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        if (customer.getName().equals("") || customerRepository.findByName(customer.getName()) != null) {
            return null;
        }
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer updateCustomer(Long id, String name, String address, String fullName, String signerName, String signerTitle, String phone, String email, String website, String registrationNumber, String vat, CustomUser manager, CustomerStatus status, SmsBillingTerms smsBillingTerms, VoiceBillingTerms voiceBillingTerms, String smsSupportEmail, String smsInvoiceEmail, String smsRateModeEmail, String smsDisputeEmail, String voiceSupportEmail, String voiceInvoiceEmail, String voiceRateModeEmail, String voiceDisputeEmail, Long company_id, String dateFormat, String fakeName, ContractTypes contractType, Double creditLimit, Double minimalPayment) {
        if (!name.equals("") && customerRepository.findByNameExceptId(name, id).size() == 0){
            Customer customer = customerRepository.findOne(id);
            customer.setName(name);
            customer.setAddress(address);
            customer.setFullName(fullName);
            customer.setSignerName(signerName);
            customer.setSignerTitle(signerTitle);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setWebsite(website);
            customer.setRegistrationNumber(registrationNumber);
            customer.setVat(vat);
            customer.setManager(manager);
            customer.setStatus(status);
            customer.setSmsBillingTerms(smsBillingTerms);
            customer.setVoiceBillingTerms(voiceBillingTerms);
            customer.setSmsSupportEmail(smsSupportEmail);
            customer.setSmsInvoiceEmail(smsInvoiceEmail);
            customer.setSmsRateModeEmail(smsRateModeEmail);
            customer.setSmsDisputeEmail(smsDisputeEmail);
            customer.setVoiceSupportEmail(voiceSupportEmail);
            customer.setVoiceInvoiceEmail(voiceInvoiceEmail);
            customer.setVoiceRateModeEmail(voiceRateModeEmail);
            customer.setVoiceDisputeEmail(voiceDisputeEmail);
            customer.setCompanyId(company_id);
            customer.setDateFormat(dateFormat);
            customer.setFakeName(fakeName);
            customer.setContractType(contractType);
            customer.setCreditLimit(creditLimit);
            customer.setMinimalPayment(minimalPayment);
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Override
    @Transactional
    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    @Override
    public List<Map> getMapCustomerAttachment(Long id_customer){
        List<Map> result = new ArrayList<>();
        for (EmailAttachment emailAttachment: customerRepository.findOne(id_customer).getEmailAttachmentList()) {
            Map<String, String> row = new HashMap<>();
            row.put("id", String.valueOf(emailAttachment.getId()));
            row.put("name", emailAttachment.getFileName());
            result.add(row);
        }
        return result;
    }
}
