package app.service.smsBillingTerms;

import app.entity.SmsBillingTerms;
import app.repository.SmsBillingTermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsBillingTermsServiceImpl implements SmsBillingTermsService{
    @Autowired
    SmsBillingTermsRepository smsBillingTermsRepository;

    @Override
    public SmsBillingTerms getSmsBillingTermsById(Long id) {
        return smsBillingTermsRepository.findOne(id);
    }

    @Override
    public SmsBillingTerms getSmsBillingTermsByName(String name) {
        return smsBillingTermsRepository.findByName(name);
    }

    @Override
    public List<SmsBillingTerms> getSmsBillingTermsAllSort() {
        return smsBillingTermsRepository.findAllSort();
    }

    @Override
    public Boolean addSmsBillingTerms(String name, Integer billingDays, Integer payDays) {
        if (name.equals("") || smsBillingTermsRepository.findByName(name) != null) {
            return false;
        }
        smsBillingTermsRepository.save(new SmsBillingTerms(name, billingDays, payDays));
        return true;
    }

    @Override
    public Boolean updateSmsBillingTerms(Long id, String name, Integer billingDays, Integer payDays) {
        if (!name.equals("") && smsBillingTermsRepository.findByNameExceptId(name, id).size() == 0){
            SmsBillingTerms smsBillingTerms = smsBillingTermsRepository.findOne(id);
            smsBillingTerms.setName(name);
            smsBillingTerms.setBillingDays(billingDays);
            smsBillingTerms.setPayDays(payDays);
            smsBillingTermsRepository.save(smsBillingTerms);
            return true;
        }
        return false;
    }
}
