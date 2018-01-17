package app.service.voiceBillingTerms;

import app.entity.VoiceBillingTerms;

import java.util.List;

public interface VoiceBillingTermsService {
    VoiceBillingTerms getVoiceBillingTermsById(Long id);
    VoiceBillingTerms getVoiceBillingTermsByName(String name);
    List<VoiceBillingTerms> getVoiceBillingTermsAllSort();
    Boolean addVoiceBillingTerms(String name, Integer billingDays, Integer payDays);
    Boolean updateVoiceBillingTerms(Long id, String name, Integer billingDays, Integer payDays);
}
