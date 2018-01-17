package app.entity;

import app.entity.enums.EmailRole;
import app.entity.enums.FontFamily;

import javax.persistence.*;
import java.util.List;

/**
 * Created by АРТЕМ on 11.09.2017.
 */
@Entity
@Table(name = "email_accounts")
public class EmailAccount {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "smtp_server")
    private String smtpServer;

    @Column(name = "smtp_port")
    private Integer smtpPort;

    @Column(name = "imap_server")
    private String imapServer;

    @Column(name = "imap_port")
    private Integer imapPort;

    @Column(name = "smtp_ssl")
    private Boolean smtpSSL = true;

    @Column(name = "imap_ssl")
    private Boolean imapSSL = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_role")
    private EmailRole emailRole;

    @Column(name = "signature", columnDefinition = "text")
    private String signature;

    @Column(name = "auto_answer")
    private Boolean autoAnswer = true;

    @Column(name = "text_auto_answer", columnDefinition = "text")
    private String textAutoAnswer;

    @Column(name = "text_auto_email_outgoing_tt", columnDefinition = "text")
    private String textAutoEmailOutgoingTT;

    @Column(name = "text_email_client_rn", columnDefinition = "text")
    private String textEmailClientRn;

    @Column(name = "text_email_invoice", columnDefinition = "text")
    private String textEmailInvoice;

    @Column(name = "text_html_for_pdf_invoice", columnDefinition = "text")
    private String textHtmlForPdfInvoice;

    @Column(name = "invisible")
    private Boolean invisible = false;

    @Column(name = "outgoing_font")
    private FontFamily outgoingFont = FontFamily.Arial;

    @Column(name = "outgoing_fon_size")
    private Integer outgoingFontSize = 16;

    @Column(name = "email_contents")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emailAccount")
    private List<EmailContent> emailContentList;

    public EmailAccount() {
    }

    public EmailAccount(String email, String userName, String password, String smtpServer, Integer smtpPort, String imapServer, Integer imapPort, Boolean smtpSSL, Boolean imapSSL, EmailRole emailRole, String signature, Boolean autoAnswer, String textAutoAnswer, String textAutoEmailOutgoingTT, String textEmailClientRn, Boolean invisible, FontFamily outgoingFont, Integer outgoingFontSize) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
        this.imapServer = imapServer;
        this.imapPort = imapPort;
        this.smtpSSL = smtpSSL;
        this.imapSSL = imapSSL;
        this.emailRole = emailRole;
        this.signature = signature;
        this.autoAnswer = autoAnswer;
        this.textAutoAnswer = textAutoAnswer;
        this.textAutoEmailOutgoingTT = textAutoEmailOutgoingTT;
        this.textEmailClientRn = textEmailClientRn;
        this.invisible = invisible;
        this.outgoingFont = outgoingFont;
        this.outgoingFontSize = outgoingFontSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getImapServer() {
        return imapServer;
    }

    public void setImapServer(String imapServer) {
        this.imapServer = imapServer;
    }

    public Boolean getSmtpSSL() {
        return smtpSSL;
    }

    public void setSmtpSSL(Boolean smtpSSL) {
        this.smtpSSL = smtpSSL;
    }

    public Boolean getImapSSL() {
        return imapSSL;
    }

    public void setImapSSL(Boolean imapSSL) {
        this.imapSSL = imapSSL;
    }

    public EmailRole getEmailRole() {
        return emailRole;
    }

    public void setEmailRole(EmailRole emailRole) {
        this.emailRole = emailRole;
    }

    public List<EmailContent> getEmailContentList() {
        return emailContentList;
    }

    public void setEmailContentList(List<EmailContent> emailContentList) {
        this.emailContentList = emailContentList;
    }

    public Boolean getInvisible() {
        return invisible;
    }

    public void setInvisible(Boolean invisible) {
        this.invisible = invisible;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public Integer getImapPort() {
        return imapPort;
    }

    public void setImapPort(Integer imapPort) {
        this.imapPort = imapPort;
    }

    public Boolean getAutoAnswer() {
        return autoAnswer;
    }

    public void setAutoAnswer(Boolean autoAnswer) {
        this.autoAnswer = autoAnswer;
    }

    public String getTextAutoAnswer() {
        return textAutoAnswer;
    }

    public void setTextAutoAnswer(String textAutoAnswer) {
        this.textAutoAnswer = textAutoAnswer;
    }

    public FontFamily getOutgoingFont() {
        return outgoingFont;
    }

    public void setOutgoingFont(FontFamily outgoingFont) {
        this.outgoingFont = outgoingFont;
    }

    public Integer getOutgoingFontSize() {
        return outgoingFontSize;
    }

    public void setOutgoingFontSize(Integer outgoingFontSize) {
        this.outgoingFontSize = outgoingFontSize;
    }

    public String getTextAutoEmailOutgoingTT() {
        return textAutoEmailOutgoingTT;
    }

    public void setTextAutoEmailOutgoingTT(String textAutoEmailOutgoingTT) {
        this.textAutoEmailOutgoingTT = textAutoEmailOutgoingTT;
    }

    public String getTextEmailClientRn() {
        return textEmailClientRn;
    }

    public void setTextEmailClientRn(String textEmailClientRn) {
        this.textEmailClientRn = textEmailClientRn;
    }

    public String getTextEmailInvoice() {
        return textEmailInvoice;
    }

    public void setTextEmailInvoice(String textEmailInvoice) {
        this.textEmailInvoice = textEmailInvoice;
    }

    public String getTextHtmlForPdfInvoice() {
        return textHtmlForPdfInvoice;
    }

    public void setTextHtmlForPdfInvoice(String textHtmlForPdfInvoice) {
        this.textHtmlForPdfInvoice = textHtmlForPdfInvoice;
    }
}
