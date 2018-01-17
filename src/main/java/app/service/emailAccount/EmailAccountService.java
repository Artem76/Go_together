package app.service.emailAccount;

import app.entity.EmailAccount;
import app.entity.enums.EmailRole;
import app.entity.enums.FontFamily;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by АРТЕМ on 12.09.2017.
 */
public interface EmailAccountService {
    boolean isEmail(Long id, String email);
    boolean isUserName(Long id, String userName);
    boolean isEmailRole(Long id, EmailRole emailRole);
    List<EmailAccount> getAllEmailAccountByEmailRole(EmailRole emailRole);
    List<EmailAccount> getAllEmailAccountByInvisible(Boolean invisible);
    EmailAccount getEmailAccountById(Long id);
    EmailAccount addEmailAccount(String email, String userName, String password, String smtpServer, Integer smtpPort, String imapServer, Integer imapPort, Boolean smtpSSL, Boolean imapSSL, EmailRole emailRole, String signature, Boolean autoAnswer, String textAutoAnswer, String textAutoEmailOutgoingTT, String textEmailClientRN, Boolean invisible, FontFamily outgoingFont, Integer outgoingFontSize, String textEmailInvoice, String textHtmlForPdfInvoice);
    EmailAccount updateEmailAccount(Long id, String email, String userName, String password, String smtpServer, Integer smtpPort, String imapServer, Integer imapPort, Boolean smtpSSL, Boolean imapSSL, EmailRole emailRole, String signature, Boolean autoAnswer, String textAutoAnswer, String textAutoEmailOutgoingTT, String textEmailClientRN, Boolean invisible, FontFamily outgoingFont, Integer outgoingFontSize, String textEmailInvoice, String textHtmlForPdfInvoice);
}
