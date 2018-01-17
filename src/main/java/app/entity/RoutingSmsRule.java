package app.entity;


import app.entity.enums.SmsRoutingLevel;
import app.entity.enums.VendorTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * Created by Олег on 19.08.2017.
 */

@Entity
@Table(name = "routing_sms_rules")
public class RoutingSmsRule {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "routing_level")
    private SmsRoutingLevel level;

    @Column(name = "account")
    private Long account;

    @Column(name = "mcc", length = 3)
    private String mcc;

    @Column(name = "mnc", length = 3)
    private String mnc;

    @Column(name = "vendor")
    private Long vendor;

    @Column(name = "source_filter", length = 150)
    private String sourceFilter;

    @Column(name = "message_filter", length = 150)
    private String messageFilter;

    @Column(name = "filtered")
    private Boolean filtered;

    @Column(name = "vendor_filter")
    private Long vendorFilter;

    @Column(name = "newsourceaddr", length = 20)
    private String newSourceAddr;

    @Column(name = "rule_order")
    private Integer ruleOrder;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "replace_source")
    private Boolean replaceSource;

    @Column(name = "replace_registered_delivery")
    private Boolean replaceRegisteredDelivery;

    @Column(name = "registered_delivery")
    private Boolean registeredDelivery;

    @Column(name = "filtered_content")
    private Boolean filteredContent;

    @Column(name = "vendor_type")
    private VendorTypes vendorType;

    @Column(name = "vendor_filter_type")
    private VendorTypes vendorFilterType;

    public RoutingSmsRule() {

    }

    public RoutingSmsRule(SmsRoutingLevel level, Long account, String mcc, String mnc, Long vendor, String sourceFilter, String messageFilter, Boolean filtered, Long vendorFilter, String newSourceAddr, Integer ruleOrder, Integer weight, Boolean replaceSource, Boolean replaceRegisteredDelivery, Boolean registeredDelivery, Boolean filteredContent, VendorTypes vendorType, VendorTypes vendorFilterType) {
        this.level = level;
        this.account = account;
        this.mcc = mcc;
        this.mnc = mnc;
        this.vendor = vendor;
        this.sourceFilter = sourceFilter;
        this.messageFilter = messageFilter;
        this.filtered = filtered;
        this.vendorFilter = vendorFilter;
        this.newSourceAddr = newSourceAddr;
        this.ruleOrder = ruleOrder;
        this.weight = weight;
        this.replaceSource = replaceSource;
        this.replaceRegisteredDelivery = replaceRegisteredDelivery;
        this.registeredDelivery = registeredDelivery;
        this.filteredContent = filteredContent;
        this.vendorType = vendorType;
        this.vendorFilterType = vendorFilterType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SmsRoutingLevel getLevel() {
        return level;
    }

    public void setLevel(SmsRoutingLevel level) {
        this.level = level;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public Long getVendor() {
        return vendor;
    }

    public void setVendor(Long vendor) {
        this.vendor = vendor;
    }

    public String getSourceFilter() {
        return sourceFilter;
    }

    public void setSourceFilter(String sourceFilter) {
        this.sourceFilter = sourceFilter;
    }

    public String getMessageFilter() {
        return messageFilter;
    }

    public void setMessageFilter(String messageFilter) {
        this.messageFilter = messageFilter;
    }

    public Boolean getFiltered() {
        return filtered;
    }

    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }

    public Long getVendorFilter() {
        return vendorFilter;
    }

    public void setVendorFilter(Long vendorFilter) {
        this.vendorFilter = vendorFilter;
    }

    public String getNewSourceAddr() {
        return newSourceAddr;
    }

    public void setNewSourceAddr(String newSourceAddr) {
        this.newSourceAddr = newSourceAddr;
    }

    public Integer getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(Integer ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getReplaceSource() {
        return replaceSource;
    }

    public void setReplaceSource(Boolean replaceSource) {
        this.replaceSource = replaceSource;
    }

    public Boolean getReplaceRegisteredDelivery() {
        return replaceRegisteredDelivery;
    }

    public void setReplaceRegisteredDelivery(Boolean replaceRegisteredDelivery) {
        this.replaceRegisteredDelivery = replaceRegisteredDelivery;
    }

    public Boolean getRegisteredDelivery() {
        return registeredDelivery;
    }

    public void setRegisteredDelivery(Boolean registeredDelivery) {
        this.registeredDelivery = registeredDelivery;
    }

    public Boolean getFilteredContent() {
        return filteredContent;
    }

    public void setFilteredContent(Boolean filteredContent) {
        this.filteredContent = filteredContent;
    }

    public VendorTypes getVendorType() {
        return vendorType;
    }

    public void setVendorType(VendorTypes vendorType) {
        this.vendorType = vendorType;
    }

    public VendorTypes getVendorFilterType() {
        return vendorFilterType;
    }

    public void setVendorFilterType(VendorTypes vendorFilterType) {
        this.vendorFilterType = vendorFilterType;
    }
}
