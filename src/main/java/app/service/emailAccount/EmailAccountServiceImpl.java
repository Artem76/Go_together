package app.service.emailAccount;

import app.entity.EmailAccount;
import app.entity.enums.EmailRole;
import app.entity.enums.FontFamily;
import app.repository.EmailAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 12.09.2017.
 */
@Service
public class EmailAccountServiceImpl implements EmailAccountService{
    @Autowired
    private EmailAccountRepository emailAccountRepository;

    @Override
    @Transactional
    public boolean isEmail(Long id, String email) {
        return emailAccountRepository.findByEmailExceptId(email, id).size() > 0;
    }

    @Override
    @Transactional
    public boolean isUserName(Long id, String userName) {
        return emailAccountRepository.findByUserNameExceptId(userName, id).size() > 0;
    }

    @Override
    public boolean isEmailRole(Long id, EmailRole emailRole) {
        return emailAccountRepository.findByEmailRoleExceptId(emailRole, id).size() > 0;
    }

    @Override
    public List<EmailAccount> getAllEmailAccountByEmailRole(EmailRole emailRole) {
        return emailAccountRepository.findByEmailRole(emailRole);
    }

    @Override
    @Transactional
    public List<EmailAccount> getAllEmailAccountByInvisible(Boolean invisible) {
        return emailAccountRepository.findByInvisible(invisible);
    }

    @Override
    @Transactional
    public EmailAccount getEmailAccountById(Long id) {
        return emailAccountRepository.findOne(id);
    }

    @Override
    @Transactional
    public EmailAccount addEmailAccount(String email, String userName, String password, String smtpServer, Integer smtpPort, String imapServer, Integer imapPort, Boolean smtpSSL, Boolean imapSSL, EmailRole emailRole, String signature, Boolean autoAnswer, String textAutoAnswer, String textAutoEmailOutgoingTT, String textEmailClientRN,Boolean invisible, FontFamily outgoingFont, Integer outgoingFontSize, String textEmailInvoice, String textHtmlForPdfInvoice) {
        if (email == null || email.equals("") || emailAccountRepository.findByEmail(email) != null) return null;
        if (userName == null || userName.equals("") || emailAccountRepository.findByUserName(userName) != null) return null;
        if (password == null || password.equals("") || smtpServer == null || smtpServer.equals("") || imapServer == null || imapServer.equals("")) return null;
        if (emailRole == null || (!emailRole.equals(EmailRole.User) && emailAccountRepository.findByEmailRole(emailRole).size() > 0)) return null;
        if (smtpPort == null || imapPort == null) return null;
        if (smtpSSL == null) smtpSSL = false;
        if (imapSSL == null) imapSSL = false;
        if (invisible == null) invisible = false;
        if (signature == null || signature.equals("")) return null;
        if ((emailRole.equals(EmailRole.NOC_SMS) || emailRole.equals(EmailRole.NOC_VOIP)) && (textAutoAnswer == null || textAutoAnswer.equals("") || textAutoEmailOutgoingTT == null || textAutoEmailOutgoingTT.equals("")))return null;
        EmailAccount emailAccount = new EmailAccount(email, userName, password, smtpServer, smtpPort, imapServer, imapPort, smtpSSL, imapSSL, emailRole, signature, autoAnswer, textAutoAnswer, textAutoEmailOutgoingTT, textEmailClientRN,invisible, outgoingFont, outgoingFontSize);
        emailAccount.setTextEmailInvoice(textEmailInvoice);
        emailAccount.setTextHtmlForPdfInvoice(textHtmlForPdfInvoice);
        emailAccountRepository.save(emailAccount);
        return emailAccount;
    }

    @Override
    @Transactional
    public EmailAccount updateEmailAccount(Long id, String email, String userName, String password, String smtpServer, Integer smtpPort, String imapServer, Integer imapPort, Boolean smtpSSL, Boolean imapSSL, EmailRole emailRole, String signature, Boolean autoAnswer, String textAutoAnswer, String textAutoEmailOutgoingTT, String textEmailClientRN, Boolean invisible, FontFamily outgoingFont, Integer outgoingFontSize, String textEmailInvoice, String textHtmlForPdfInvoice) {
        if (email == null || email.equals("") || emailAccountRepository.findByEmailExceptId(email, id).size() > 0) return null;
        if (userName == null || userName.equals("") || emailAccountRepository.findByUserNameExceptId(userName, id).size() > 0) return null;
        if (password == null || password.equals("") || smtpServer == null || smtpServer.equals("") || imapServer == null || imapServer.equals("")) return null;
        if (emailRole == null || (!emailRole.equals(EmailRole.User) && emailAccountRepository.findByEmailRoleExceptId(emailRole, id).size() > 0)) return null;
        if (smtpPort == null || imapPort == null) return null;
        if (smtpSSL == null) smtpSSL = false;
        if (imapSSL == null) imapSSL = false;
        if (invisible == null) invisible = false;
        if (signature == null || signature.equals("")) return null;
        if ((emailRole.equals(EmailRole.NOC_SMS) || emailRole.equals(EmailRole.NOC_VOIP)) && (textAutoAnswer == null || textAutoAnswer.equals("") || textAutoEmailOutgoingTT == null || textAutoEmailOutgoingTT.equals("")))return null;
        if (outgoingFontSize == null || outgoingFontSize < 1) return null;
        EmailAccount emailAccount = emailAccountRepository.findOne(id);
        emailAccount.setEmail(email);
        emailAccount.setUserName(userName);
        emailAccount.setPassword(password);
        emailAccount.setSmtpServer(smtpServer);
        emailAccount.setSmtpPort(smtpPort);
        emailAccount.setImapServer(imapServer);
        emailAccount.setImapPort(imapPort);
        emailAccount.setSmtpSSL(smtpSSL);
        emailAccount.setImapSSL(imapSSL);
        emailAccount.setEmailRole(emailRole);
        emailAccount.setAutoAnswer(autoAnswer);
        emailAccount.setTextAutoAnswer(textAutoAnswer);
        emailAccount.setTextAutoEmailOutgoingTT(textAutoEmailOutgoingTT);
        emailAccount.setTextEmailClientRn(textEmailClientRN);
        emailAccount.setInvisible(invisible);
        emailAccount.setSignature(signature);
        emailAccount.setOutgoingFont(outgoingFont);
        emailAccount.setOutgoingFontSize(outgoingFontSize);
        emailAccount.setTextEmailInvoice(textEmailInvoice);
        emailAccount.setTextHtmlForPdfInvoice(textHtmlForPdfInvoice);
        emailAccountRepository.save(emailAccount);
        return emailAccount;
    }

}
