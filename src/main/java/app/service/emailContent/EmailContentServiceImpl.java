package app.service.emailContent;

import app.entity.*;
import app.entity.enums.EmailRole;
import app.entity.enums.EmailType;
import app.repository.EmailContentRepository;
import app.service.clientRatesHistroy.ClientRatesHistoryService;
import app.service.clientRatesUpdate.ClientRatesUpdateService;
import app.service.company.CompanyService;
import app.service.customer.CustomerService;
import app.service.emailAccount.EmailAccountService;
import app.service.emailAttachment.EmailAttachmentService;
import app.service.excel.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by АРТЕМ on 15.09.2017.
 */
@Service
public class EmailContentServiceImpl implements EmailContentService {
    @Autowired
    private EmailContentRepository emailContentRepository;

    @Autowired
    private EmailAccountService emailAccountService;

    @Autowired
    private ClientRatesUpdateService clientRatesUpdateService;

    @Autowired
    private ClientRatesHistoryService clientRatesHistoryService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmailAttachmentService emailAttachmentService;

    @Override
    public boolean isEmailContent(Date receivedDate, EmailType emailType, String from, String to, String subject) {
        return emailContentRepository.findByReceivedDateAndEmailTypeAndFromAndToAndSubject(receivedDate, emailType, from, to, subject).size() > 0;
    }

    @Override
    public List<EmailContent> getEmailContentByEmailType(EmailType emailType) {
        return emailContentRepository.findByEmailType(emailType);
    }

    @Override
    public List<EmailContent> getEmailContentByEmailAccountAndEmailType(EmailAccount emailAccount, EmailType emailType) {
        return emailContentRepository.findByEmailAccountAndEmailType(emailAccount, emailType);
    }

    @Override
    public List<EmailContent> getEmailContentByEmailAccountAndEmailTypeAndProcessed(EmailAccount emailAccount, EmailType emailType, Boolean processed) {
        return emailContentRepository.findByEmailAccountAndEmailTypeAndProcessed(emailAccount, emailType, processed);
    }

    @Override
    public List<EmailContent> getEmailContentByEmailAccountForPage(EmailAccount emailAccount, int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return emailContentRepository.findByEmailAccountForPage(emailAccount, pageable);
    }

    @Override
    public EmailContent getEmailContentById(Long id) {
        return emailContentRepository.findOne(id);
    }

    @Override
    public EmailContent getEmailContentByIncomingTTAndEmailType(IncomingTT incomingTT, EmailType emailType) {
        return emailContentRepository.findByIncomingTTAndEmailType(incomingTT, emailType).get(0);
    }

    @Override
    public EmailContent getEmailContentByOutgoingTTAndEmailType(OutgoingTT outgoingTT, EmailType emailType) {
        return emailContentRepository.findByOutgoingTTAndEmailType(outgoingTT, emailType).get(0);
    }

    @Override
    @Transactional
    public EmailContent addEmailContentIncoming(EmailContent emailContent) {
        if (emailContent.getFrom() == null || emailContent.getFrom().equals("") || emailContent.getTo() == null || emailContent.getTo().equals("") || emailContent.getDate() == null || emailContentRepository.findByReceivedDateAndEmailTypeAndFromAndToAndSubject(emailContent.getDate(), emailContent.getEmailType(), emailContent.getFrom(), emailContent.getTo(), emailContent.getSubject()).size() > 0) {
            return null;
        }
        emailContentRepository.save(emailContent);
        return emailContent;
    }

    @Override
    @Transactional
    public EmailContent addEmailContentOutgoing(EmailContent emailContent) {
        emailContentRepository.save(emailContent);
        return emailContent;
    }

    @Override
    @Transactional
    public EmailContent updateIncomingTT(EmailContent emailContent, IncomingTT incomingTT) {
        emailContent.setIncomingTT(incomingTT);
        emailContentRepository.save(emailContent);
        return emailContent;
    }

    @Override
    @Transactional
    public EmailContent updateOutgoingTT(EmailContent emailContent, OutgoingTT outgoingTT) {
        emailContent.setOutgoingTT(outgoingTT);
        emailContentRepository.save(emailContent);
        return emailContent;
    }

    @Override
    @Transactional
    public EmailContent updateVendorRatesUpdate(EmailContent emailContent, VendorRatesUpdate vendorRatesUpdate) {
        if (emailContent != null) {
            emailContent.setVendorRatesUpdate(vendorRatesUpdate);
            emailContentRepository.save(emailContent);
        }
        return emailContent;
    }

    @Override
    @Transactional
    public EmailContent updateProcessed(Long id, boolean processed) {
        EmailContent content = emailContentRepository.findOne(id);
        content.setProcessed(processed);
        emailContentRepository.save(content);
        return content;
    }

    @Override
    @Transactional
    public EmailContent updateProcessedAndDateSend(Long id, boolean processed) {
        EmailContent content = emailContentRepository.findOne(id);
        content.setProcessed(processed);
        content.setDateSend(new Date());
        emailContentRepository.save(content);
        return content;
    }

    @Override
    @Transactional
    public void answerNocEmailContent(EmailContent emailContent) {
        EmailContent answerEmailContent = new EmailContent(
                EmailType.Outgoing,
                emailContent.getEmailAccount(),
                emailContent.getTo(),
                emailContent.getFrom(),
                "",
                "Re: [ITT#" + emailContent.getIncomingTT().getId() + "] " + emailContent.getSubject(),
                emailContent.getEmailAccount().getTextAutoAnswer() +
                        emailContent.getEmailAccount().getSignature(),
                true,
                new Date(),
                false
        );
        answerEmailContent.setIncomingTT(emailContent.getIncomingTT());
        emailContentRepository.save(answerEmailContent);
    }

    @Override
    @Transactional
    public void clientRnEmailContent(Long id_rn, CustomUser user) {
        ClientRatesUpdate clientRatesUpdate = clientRatesUpdateService.getRnById(id_rn);
        clientRatesUpdate.setDate(new Date());
        clientRatesUpdate.setUser(user);
        EmailAccount emailAccount = emailAccountService.getAllEmailAccountByEmailRole(EmailRole.Ratemode_SMS).get(0);
        List<EmailContent> emailContentList = new ArrayList<>();
        for (SmppClientSystemId smppClientSystemId : clientRatesUpdate.getSmppClientAccount().getSmppClientSystemIdList()) {
            EmailContent emailContent = new EmailContent(
                    EmailType.Outgoing,
                    emailAccount,
                    emailAccount.getEmail(),
                    clientRatesUpdate.getSmppClientAccount().getCustomer().getSmsRateModeEmail().replace(" ", "").replaceFirst(",.*$", ""),
                    clientRatesUpdate.getSmppClientAccount().getCustomer().getSmsRateModeEmail().replace(" ", "").replaceFirst("^.*,", ""),
                    "Rate notification for " + clientRatesUpdate.getSmppClientAccount().getCustomer().getName() + " (" + smppClientSystemId.getSystemId() + ")",
                    clientRatesUpdate.getTextEmail().replaceFirst("\\[SystemId\\]", smppClientSystemId.getSystemId()),
                    true,
                    clientRatesUpdate.getDate(),
                    false
            );
            emailContent.setClientRatesUpdate(clientRatesUpdate);
            emailContent.setEmailAttachmentList(new HashSet<>(excelService.setClientRnGetEmailAttachmentXlsxCsv(clientRatesUpdate, emailContent)));
            emailContentList.add(emailContent);
        }
        emailContentRepository.save(emailContentList);
        Set<ClientRatesHistory> clientRatesHistoryList = clientRatesUpdate.getClientRatesHistories();
        for (ClientRatesHistory clientRatesHistory : clientRatesHistoryList) {
            clientRatesHistory.setApplied(true);
        }
        clientRatesHistoryService.save(new ArrayList<>(clientRatesHistoryList));
        clientRatesUpdate.setEmailContentList(new HashSet<>(emailContentList));
        clientRatesUpdateService.saveRateUpdate(clientRatesUpdate);
    }

    @Override
    @Transactional
    public EmailContent outgoingInvoiceEmailContent(OutgoingInvoice outgoingInvoice) {
        EmailAccount emailAccount = emailAccountService.getAllEmailAccountByEmailRole(EmailRole.Invoice_SMS).get(0);
        Customer customer = customerService.getCustomerById(outgoingInvoice.getCustomer_id());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String body = emailAccount.getTextEmailInvoice()
                .replace("[NameCompany]", companyService.getCompanyById(customer.getCompanyId()).getName()
                .replace("[PeriodStart]", format.format(outgoingInvoice.getStartDate()))
                .replace("[PeriodEnd]", format.format(outgoingInvoice.getEndDate())));
        EmailContent emailContent = new EmailContent(
                EmailType.Outgoing,
                emailAccount,
                emailAccount.getEmail(),
                customerService.getCustomerById(outgoingInvoice.getCustomer_id()).getSmsInvoiceEmail().replace(" ", "").replaceFirst(",.*$", ""),
                customerService.getCustomerById(outgoingInvoice.getCustomer_id()).getSmsInvoiceEmail().replace(" ", "").replaceFirst("^.*,", ""),
                "Invoice for " + customer.getName() + " (" + format.format(outgoingInvoice.getStartDate()) + " - " + format.format(outgoingInvoice.getEndDate()) + ")",
                body,
                true,
                new Date(),
                false);
        emailContent.setOutgoingInvoice(outgoingInvoice);
        List<EmailAttachment> emailAttachmentList = new ArrayList<>();
        for (EmailAttachment emailAttachment: outgoingInvoice.getEmailAttachmentList()) {
            EmailAttachment newAttachment = new EmailAttachment(emailAttachment.getFileName(), emailAttachment.getFileBody(), null);
            emailAttachmentList.add(newAttachment);
        }
        emailContent.setEmailAttachmentList(new HashSet<>(emailAttachmentList));
        emailContent = emailContentRepository.save(emailContent);
        for(EmailAttachment emailAttachment: emailAttachmentList){
            emailAttachment.setEmailContent(emailContent);
        }
        emailAttachmentService.saveList(new ArrayList<>(emailAttachmentList));
        return emailContent;
    }
}
