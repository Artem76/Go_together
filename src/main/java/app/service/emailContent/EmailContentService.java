package app.service.emailContent;

import app.entity.*;
import app.entity.enums.EmailType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by АРТЕМ on 15.09.2017.
 */
public interface EmailContentService {
    boolean isEmailContent(Date receivedDate, EmailType emailType, String from, String to, String subject);
    List<EmailContent> getEmailContentByEmailType(EmailType emailType);
    List<EmailContent> getEmailContentByEmailAccountAndEmailType(EmailAccount emailAccount, EmailType emailType);
    List<EmailContent> getEmailContentByEmailAccountAndEmailTypeAndProcessed(EmailAccount emailAccount, EmailType emailType, Boolean processed);
    List<EmailContent> getEmailContentByEmailAccountForPage(EmailAccount emailAccount, int page, int size);
    EmailContent getEmailContentById(Long id);
    EmailContent getEmailContentByIncomingTTAndEmailType(IncomingTT incomingTT, EmailType emailType);

    EmailContent getEmailContentByOutgoingTTAndEmailType(OutgoingTT outgoingTT, EmailType emailType);

    EmailContent addEmailContentIncoming(EmailContent emailContent);
    EmailContent addEmailContentOutgoing(EmailContent emailContent);
    EmailContent updateIncomingTT(EmailContent emailContent, IncomingTT incomingTT);

    @Transactional
    EmailContent updateOutgoingTT(EmailContent emailContent, OutgoingTT outgoingTT);

    @Transactional
    EmailContent updateVendorRatesUpdate(EmailContent emailContent, VendorRatesUpdate vendorRatesUpdate);

    EmailContent updateProcessed(Long id, boolean processed);

    @Transactional
    EmailContent updateProcessedAndDateSend(Long id, boolean processed);

    void answerNocEmailContent(EmailContent emailContent);

    @Transactional
    void clientRnEmailContent(Long id_rn, CustomUser user);

    @Transactional
    EmailContent outgoingInvoiceEmailContent(OutgoingInvoice outgoingInvoice);
}
