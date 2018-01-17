package app.service.routingSmsRule;

import app.entity.RoutingSmsRule;
import app.entity.enums.SmsRoutingLevel;
import app.entity.enums.VendorTypes;
import app.repository.RoutingSmsRuleRepository;
import app.service.changesRegistery.ChangesRegisteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Олег on 20.08.2017.
 */
@Service
public class RoutingSmsRuleServiceImpl implements RoutingSmsRuleService{
    @Autowired
    RoutingSmsRuleRepository routingSmsRuleRepository;

    @Autowired
    ChangesRegisteryService changesRegisteryService;

    @Override
    public List<RoutingSmsRule> getAllByLevelMccAndAccount(SmsRoutingLevel smsRoutingLevel, Long clientId, String mcc) {
        return routingSmsRuleRepository.findAllByLevelAndAccount(smsRoutingLevel, clientId, mcc);
    }

    @Override
    public String SaveRoutingRule(String id, SmsRoutingLevel level, Long account, String country, String network, Long vendor, String sourceFilter, String messageFilter, Long vendorFilter, Integer ruleOrder,
                                  Integer weight, Boolean filtered, Boolean replaceSource, Boolean replaceRegisteredDelivery, Boolean registeredDelivery, Boolean filteredContent, VendorTypes vendorType, VendorTypes vendorFilterType) {
        RoutingSmsRule currentRule = null;
        if (id.equals("-1")) {
            currentRule = new RoutingSmsRule();
            currentRule.setId(UUID.randomUUID().toString());
        } else {
            currentRule = routingSmsRuleRepository.getOne(id);
        }
        currentRule.setLevel(level);
        currentRule.setAccount(account);
        currentRule.setMcc(country);
        currentRule.setMnc(network);
        currentRule.setVendor(vendor);
        currentRule.setSourceFilter(sourceFilter);
        currentRule.setMessageFilter(messageFilter);
        currentRule.setVendorFilter(vendorFilter);
        currentRule.setRuleOrder(ruleOrder);
        currentRule.setWeight(weight);
        currentRule.setFiltered(filtered);
        currentRule.setReplaceSource(replaceSource);
        currentRule.setReplaceRegisteredDelivery(replaceRegisteredDelivery);
        currentRule.setRegisteredDelivery(registeredDelivery);
        currentRule.setFilteredContent(filteredContent);
        currentRule.setVendorType(vendorType);
        currentRule.setVendorFilterType(vendorFilterType);
        routingSmsRuleRepository.save(currentRule);
        changesRegisteryService.registerChangesAllSoftswitches("routingsmsrule_" + currentRule.getId());
        return currentRule.getId().toString();
    }

    @Override
    public void deleteRoutingRule(String id) {
        Long accountId = routingSmsRuleRepository.getOne(id).getAccount();
        routingSmsRuleRepository.delete(id);
        changesRegisteryService.registerChangesAllSoftswitches("routingsmsrule_" + id + "_DELETE_" + accountId);
    }

    @Override
    public RoutingSmsRule getRuleById(String id) {
        return routingSmsRuleRepository.getOne(id);
    }

    @Override
    public List<RoutingSmsRule> findAllByAccount(Long account) {
        return routingSmsRuleRepository.findAllByAccount(account);
    }
}
