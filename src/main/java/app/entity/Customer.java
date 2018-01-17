package app.entity;


import app.entity.enums.ContractTypes;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "signername")
    private String signerName;

    @Column(name = "signertitle")
    private String signerTitle;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "registrationnumber")
    private String registrationNumber;

    @Column(name = "vat")
    private String vat;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomUser manager;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private SmsBillingTerms smsBillingTerms;

    @ManyToOne(fetch = FetchType.LAZY)
    private VoiceBillingTerms voiceBillingTerms;

    @Column(name = "contract_type")
    private ContractTypes contractType = ContractTypes.NotSelected;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "minimal_payment")
    private Double minimalPayment;

    @Column(name = "smssupportemail")
    private String smsSupportEmail;

    @Column(name = "smsinvoiceemail")
    private String smsInvoiceEmail;

    @Column(name = "smsratemodeemail")
    private String smsRateModeEmail;

    @Column(name = "smsdisputeemail")
    private String smsDisputeEmail;

    @Column(name = "voicesupportemail")
    private String voiceSupportEmail;

    @Column(name = "voiceinvoiceemail")
    private String voiceInvoiceEmail;

    @Column(name = "voiceratemodeemail")
    private String voiceRateModeEmail;

    @Column(name = "voicedisputeemail")
    private String voiceDisputeEmail;

    @Column(name = "smpp_client_accounts")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<SmppClientAccount> smppClientAccountList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<SmppVendorAccount> smppVendorAccountList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<IncomingPayment> incomingPaymentSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<OutgoingPayment> outgoingPayments;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "date_format")
    private String dateFormat;

    @Column(name = "fake_name")
    private String fakeName;

    @Column(name = "email_attachments")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<EmailAttachment> emailAttachmentList = new HashSet<>();

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, String phone, String email, String website, CustomUser manager, CustomerStatus status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.manager = manager;
        this.status = status;
    }

    public Customer() {
    }

    public Customer(String name, String address, String fullName, String signerName, String signerTitle, String phone, String email, String website, String registrationNumber, String vat, CustomUser manager, CustomerStatus status, SmsBillingTerms smsBillingTerms, VoiceBillingTerms voiceBillingTerms, String smsSupportEmail, String smsInvoiceEmail, String smsRateModeEmail, String smsDisputeEmail, String voiceSupportEmail, String voiceInvoiceEmail, String voiceRateModeEmail, String voiceDisputeEmail) {
        this.name = name;
        this.address = address;
        this.fullName = fullName;
        this.signerName = signerName;
        this.signerTitle = signerTitle;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.registrationNumber = registrationNumber;
        this.vat = vat;
        this.manager = manager;
        this.status = status;
        this.smsBillingTerms = smsBillingTerms;
        this.voiceBillingTerms = voiceBillingTerms;
        this.smsSupportEmail = smsSupportEmail;
        this.smsInvoiceEmail = smsInvoiceEmail;
        this.smsRateModeEmail = smsRateModeEmail;
        this.smsDisputeEmail = smsDisputeEmail;
        this.voiceSupportEmail = voiceSupportEmail;
        this.voiceInvoiceEmail = voiceInvoiceEmail;
        this.voiceRateModeEmail = voiceRateModeEmail;
        this.voiceDisputeEmail = voiceDisputeEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public String getSignerTitle() {
        return signerTitle;
    }

    public void setSignerTitle(String signerTitle) {
        this.signerTitle = signerTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public CustomUser getManager() {
        return manager;
    }

    public void setManager(CustomUser manager) {
        this.manager = manager;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public SmsBillingTerms getSmsBillingTerms() {
        return smsBillingTerms;
    }

    public void setSmsBillingTerms(SmsBillingTerms smsBillingTerms) {
        this.smsBillingTerms = smsBillingTerms;
    }

    public VoiceBillingTerms getVoiceBillingTerms() {
        return voiceBillingTerms;
    }

    public void setVoiceBillingTerms(VoiceBillingTerms voiceBillingTerms) {
        this.voiceBillingTerms = voiceBillingTerms;
    }

    public String getSmsSupportEmail() {
        return smsSupportEmail;
    }

    public void setSmsSupportEmail(String smsSupportEmail) {
        this.smsSupportEmail = smsSupportEmail;
    }

    public String getSmsInvoiceEmail() {
        return smsInvoiceEmail;
    }

    public void setSmsInvoiceEmail(String smsInvoiceEmail) {
        this.smsInvoiceEmail = smsInvoiceEmail;
    }

    public String getSmsRateModeEmail() {
        return smsRateModeEmail;
    }

    public void setSmsRateModeEmail(String smsRateModeEmail) {
        this.smsRateModeEmail = smsRateModeEmail;
    }

    public String getSmsDisputeEmail() {
        return smsDisputeEmail;
    }

    public void setSmsDisputeEmail(String smsDisputeEmail) {
        this.smsDisputeEmail = smsDisputeEmail;
    }

    public String getVoiceSupportEmail() {
        return voiceSupportEmail;
    }

    public void setVoiceSupportEmail(String voiceSupportEmail) {
        this.voiceSupportEmail = voiceSupportEmail;
    }

    public String getVoiceInvoiceEmail() {
        return voiceInvoiceEmail;
    }

    public void setVoiceInvoiceEmail(String voiceInvoiceEmail) {
        this.voiceInvoiceEmail = voiceInvoiceEmail;
    }

    public String getVoiceRateModeEmail() {
        return voiceRateModeEmail;
    }

    public void setVoiceRateModeEmail(String voiceRateModeEmail) {
        this.voiceRateModeEmail = voiceRateModeEmail;
    }

    public String getVoiceDisputeEmail() {
        return voiceDisputeEmail;
    }

    public void setVoiceDisputeEmail(String voiceDisputeEmail) {
        this.voiceDisputeEmail = voiceDisputeEmail;
    }

    public List<SmppClientAccount> getSmppClientAccountList() {
        return smppClientAccountList;
    }

    public void setSmppClientAccountList(List<SmppClientAccount> smppClientAccountList) {
        this.smppClientAccountList = smppClientAccountList;
    }

    public List<SmppVendorAccount> getSmppVendorAccountList() {
        return smppVendorAccountList;
    }

    public void setSmppVendorAccountList(List<SmppVendorAccount> smppVendorAccountList) {
        this.smppVendorAccountList = smppVendorAccountList;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFakeName() {
        return fakeName;
    }

    public void setFakeName(String fakeName) {
        this.fakeName = fakeName;
    }

    public Set<IncomingPayment> getIncomingPaymentSet() {
        return incomingPaymentSet;
    }

    public void setIncomingPaymentSet(Set<IncomingPayment> incomingPaymentSet) {
        this.incomingPaymentSet = incomingPaymentSet;
    }

    public void addIncomingPayment(IncomingPayment incomingPayment) {
        this.incomingPaymentSet.add(incomingPayment);
    }

    public Set<OutgoingPayment> getOutgoingPayments() {
        return outgoingPayments;
    }

    public void setOutgoingPayments(Set<OutgoingPayment> outgoingPayments) {
        this.outgoingPayments = outgoingPayments;
    }

    public void addOutgoingPayments(OutgoingPayment outgoingPayment) {
        this.outgoingPayments.add(outgoingPayment);
    }

    public ContractTypes getContractType() {
        return contractType;
    }

    public void setContractType(ContractTypes contractType) {
        this.contractType = contractType;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getMinimalPayment() {
        return minimalPayment;
    }

    public void setMinimalPayment(Double minimalPayment) {
        this.minimalPayment = minimalPayment;
    }

    public Set<EmailAttachment> getEmailAttachmentList() {
        return emailAttachmentList;
    }

    public void setEmailAttachmentList(Set<EmailAttachment> emailAttachmentList) {
        this.emailAttachmentList = emailAttachmentList;
    }

    public void addEmailAttachment(EmailAttachment emailAttachment){
        this.emailAttachmentList.add(emailAttachment);
    }

    public void deleteEmailAttachment(EmailAttachment emailAttachment){
        this.emailAttachmentList.remove(emailAttachment);
    }
}
