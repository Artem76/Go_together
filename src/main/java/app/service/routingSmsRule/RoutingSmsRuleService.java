package app.service.routingSmsRule;

import app.entity.RoutingSmsRule;
import app.entity.enums.SmsRoutingLevel;
import app.entity.enums.VendorTypes;

import java.util.List;

/**
 * Created by Олег on 20.08.2017.
 */
public interface RoutingSmsRuleService {
    List<RoutingSmsRule> getAllByLevelMccAndAccount(SmsRoutingLevel smsRoutingLevel, Long clientId, String mcc);
    String SaveRoutingRule(String id, SmsRoutingLevel level, Long account, String country, String network, Long vendor, String sourceFilter, String messageFilter, Long vendorFilter, Integer ruleOrder,
                           Integer weight, Boolean filtered, Boolean replaceSource, Boolean replaceRegisteredDelivery, Boolean registeredDelivery, Boolean filteredContent, VendorTypes vendorType, VendorTypes vendorFilterType);
    void deleteRoutingRule(String id);
    RoutingSmsRule getRuleById(String id);
    List<RoutingSmsRule> findAllByAccount(Long account);
}
