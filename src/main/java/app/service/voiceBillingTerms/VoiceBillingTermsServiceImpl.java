package app.service.voiceBillingTerms;

import app.entity.SmsBillingTerms;
import app.entity.VoiceBillingTerms;
import app.repository.VoiceBillingTermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoiceBillingTermsServiceImpl implements VoiceBillingTermsService{
    @Autowired
    VoiceBillingTermsRepository voiceBillingTermsRepository;

    @Override
    public VoiceBillingTerms getVoiceBillingTermsById(Long id) {
        return voiceBillingTermsRepository.findOne(id);
    }

    @Override
    public VoiceBillingTerms getVoiceBillingTermsByName(String name) {
        return voiceBillingTermsRepository.findByName(name);
    }

    @Override
    public List<VoiceBillingTerms> getVoiceBillingTermsAllSort() {
        return voiceBillingTermsRepository.findAllSort();
    }

    @Override
    public Boolean addVoiceBillingTerms(String name, Integer billingDays, Integer payDays) {
        if (name.equals("") || voiceBillingTermsRepository.findByName(name) != null) {
            return false;
        }
        voiceBillingTermsRepository.save(new VoiceBillingTerms(name, billingDays, payDays));
        return true;
    }

    @Override
    public Boolean updateVoiceBillingTerms(Long id, String name, Integer billingDays, Integer payDays) {
        if (!name.equals("") && voiceBillingTermsRepository.findByNameExceptId(name, id).size() == 0){
            VoiceBillingTerms voiceBillingTerms = voiceBillingTermsRepository.findOne(id);
            voiceBillingTerms.setName(name);
            voiceBillingTerms.setBillingDays(billingDays);
            voiceBillingTerms.setPayDays(payDays);
            voiceBillingTermsRepository.save(voiceBillingTerms);
            return true;
        }
        return false;
    }
}
