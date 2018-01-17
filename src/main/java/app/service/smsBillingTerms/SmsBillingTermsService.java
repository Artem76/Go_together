package app.service.smsBillingTerms;

import app.entity.SmsBillingTerms;

import java.util.List;

public interface SmsBillingTermsService {
    SmsBillingTerms getSmsBillingTermsById(Long id);
    SmsBillingTerms getSmsBillingTermsByName(String name);
    List<SmsBillingTerms> getSmsBillingTermsAllSort();
    Boolean addSmsBillingTerms(String name, Integer billingDays, Integer payDays);
    Boolean updateSmsBillingTerms(Long id, String name, Integer billingDays, Integer payDays);
}
