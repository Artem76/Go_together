package app.service.emailDelivery;

import app.entity.EmailAccount;
import app.entity.EmailAttachment;
import app.entity.EmailContent;
import app.entity.IncomingTT;
import app.entity.enums.EmailRole;
import app.entity.enums.EmailType;
import app.service.coder.CoderService;
import app.service.emailAccount.EmailAccountService;
import app.service.emailContent.EmailContentService;
import app.service.incommingTT.IncomingTTService;
import app.service.outgoingTT.OutgoingTTService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by АРТЕМ on 13.09.2017.
 */
@Service
public class EmailDeliveryServiceImpl implements EmailDeliveryService {
    @Autowired
    private EmailAccountService emailAccountService;

    @Autowired
    private EmailContentService emailContentService;

    @Autowired
    private IncomingTTService incomingTTService;

    @Autowired
    private OutgoingTTService outgoingTTService;

    @Autowired
    private CoderService coderService;

    @Override
    public void sendAndGetEmail(boolean onlyNew) {
        for (EmailAccount emailAccount : emailAccountService.getAllEmailAccountByInvisible(false)) {
            sendEmail(emailAccount);
            getEmail(emailAccount, onlyNew, 0);
        }
    }

    private void sendEmail(EmailAccount emailAccount) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", emailAccount.getSmtpServer());
        properties.setProperty("mail.smtp.port", emailAccount.getSmtpPort().toString());
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", emailAccount.getSmtpSSL().toString());
        properties.setProperty("mail.smtp.starttls.required", emailAccount.getSmtpSSL().toString());
        properties.setProperty("mail.smtp.timeout", "60000");
        properties.setProperty("mail.smtp.connectiontimeout", "60000");
        properties.setProperty("mail.smtp.socketFactory.port", emailAccount.getSmtpPort().toString());
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailAccount.getUserName(), emailAccount.getPassword());
                    }
                });
        MimeMessage msg;
        for (EmailContent emailContent : emailContentService.getEmailContentByEmailAccountAndEmailTypeAndProcessed(emailAccount, EmailType.Outgoing, false)) {
            msg = new MimeMessage(session);
            try {
                String[] internetAddressToStringArr = emailContent.getTo().split(" *, *");
                InternetAddress[] internetAddressesTo = new InternetAddress[internetAddressToStringArr.length];
                for (int i=0 ; i<internetAddressToStringArr.length ; i++){
                    internetAddressesTo[i] = new InternetAddress(internetAddressToStringArr[i]);
                }
                msg.setFrom(new InternetAddress(emailContent.getFrom()));
                msg.setRecipients(Message.RecipientType.TO, internetAddressesTo);
                if (emailContent.getCopy() != null && !emailContent.getCopy().equals("")){
                    String[] internetAddressCopyStringArr = emailContent.getCopy().split(" *, *");
                    InternetAddress[] internetAddressesCopy = new InternetAddress[internetAddressCopyStringArr.length];
                    for (int i=0 ; i<internetAddressCopyStringArr.length ; i++){
                        internetAddressesCopy[i] = new InternetAddress(internetAddressCopyStringArr[i]);
                    }
                    msg.addRecipients(Message.RecipientType.CC, internetAddressesCopy);
                }

                msg.setSubject(emailContent.getSubject(), "utf-8");
                Multipart mp = new MimeMultipart();
                MimeBodyPart bp = new MimeBodyPart();
                bp.setContent(emailContent.getBody(), "text/html; charset=utf-8");
                mp.addBodyPart(bp);

                Set<EmailAttachment> emailAttachmentSet = new HashSet<>(emailContent.getEmailAttachmentList());
                for (EmailAttachment emailAttachment : emailAttachmentSet) {
                    MimeBodyPart bpf = new MimeBodyPart();
                    bpf.setFileName(emailAttachment.getFileName());
                    File file = new File(emailAttachment.getFileName());
                    OutputStream os = null;
                    try {
                        os = new FileOutputStream(file);
                        os.write(emailAttachment.getFileBody());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        os.close();
                    }
                    bpf.attachFile(file);
                    mp.addBodyPart(bpf);
                }
                msg.setContent(mp);
                Transport.send(msg);
                emailContentService.updateProcessedAndDateSend(emailContent.getId(), true);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (EmailAttachment emailAttachment : emailContent.getEmailAttachmentList()) {
                    File file = new File(emailAttachment.getFileName());
                    file.delete();
                }
            }
        }

    }

    private void getEmail(EmailAccount emailAccount, boolean onlyNew, int numberMsg) {
//        System.out.println(new Date().toGMTString());
        Session session = null;
        Properties properties = new Properties();
        properties.setProperty("mail.imaps.host", emailAccount.getImapServer());
        properties.setProperty("mail.imaps.port", emailAccount.getImapPort().toString());
        properties.setProperty("mail.imaps.auth", "true");
        properties.setProperty("mail.imaps.starttls.enable", emailAccount.getImapSSL().toString());
        properties.setProperty("mail.imaps.starttls.required", emailAccount.getImapSSL().toString());
        properties.setProperty("mail.imaps.timeout", "60000");
        properties.setProperty("mail.imaps.connectiontimeout", "60000");
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailAccount.getUserName(), emailAccount.getPassword());
                    }
                });

        Store store = null;
        Folder folder = null;
        try {
            store = session.getStore("imaps");
            store.connect();
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            if (onlyNew) {
                try {
                    for (int i = folder.getMessageCount(); i >= 1; i--) {
                        Message message = folder.getMessage(i);
                        if (!processingIncomingMessage(message, emailAccount)) {
                            throw new Exception();
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                if (numberMsg > 0) {
                    int n = folder.getMessageCount();
                    for (int i = n; i >= n - numberMsg && i > 0; i--) {
//                        System.out.println(i);
                        Message message = folder.getMessage(i);
                        processingIncomingMessage(message, emailAccount);
                    }
                } else {
                    for (Message message : folder.getMessages()) {
                        processingIncomingMessage(message, emailAccount);
                    }
                }
            }
        }catch (AuthenticationFailedException e){
            e.printStackTrace();
        } catch (Exception e) {
//            e.printStackTrace();
            getEmail(emailAccount, false, 100);
        } finally {
            try {
                folder.close(false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            try {
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean processingIncomingMessage(Message message, EmailAccount emailAccount) {
        try {
            EmailContent emailContent = new EmailContent();
            emailContent.setEmailType(EmailType.Incoming);
            emailContent.setEmailAccount(emailAccount);
            emailContent.setFrom(editAddressArray(message.getFrom()));
            emailContent.setTo(emailAccount.getEmail());
            emailContent.setCopy(editAddressArray(message.getAllRecipients()));
            emailContent.setSubject(message.getSubject() == null ? "" : message.getSubject());
            emailContent.setDate(message.getReceivedDate());
            emailContent.setProcessed(false);
            if (message.getContentType().split("/")[0].equalsIgnoreCase("multipart")) {
                Multipart mp = (Multipart) message.getContent();
                emailContent = processingMultipart(mp, emailContent);
            } else {
                if (message.getContentType().split(";")[0].equalsIgnoreCase("text/html")) {
                    emailContent.setTextTypeHtml(true);
                } else {
                    emailContent.setTextTypeHtml(false);
                }
                emailContent.setBody(message.getContent().toString());
            }
            if ((emailContent = emailContentService.addEmailContentIncoming(emailContent)) != null) {
                if (emailAccount.getEmailRole().equals(EmailRole.NOC_SMS) || emailAccount.getEmailRole().equals(EmailRole.NOC_VOIP)) {  //=====Only NOC=====

                    if (Pattern.matches(".*\\[OTT#.*\\].*", emailContent.getSubject())){

                        //=========Добавление ответа к outgoingTT==========

                        outgoingTTService.setEmailContent(emailContent);

                    }else{

                        //=========Добавление incomingТТ=========
                        if (incomingTTService.setEmailContent(emailContent) && emailAccount.getAutoAnswer()) {

                            //====Формирование ответного письма====
                            emailContentService.answerNocEmailContent(emailContent);
                        }
                        emailContentService.updateProcessed(emailContent.getId(), true);
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String editAddressArray(Address[] addresses) {
        StringBuilder resultAddresses = new StringBuilder();
        for (int i = 0; i < addresses.length; i++) {
            try {
                resultAddresses.append(addresses[i].toString().split("<")[1].split(">")[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                resultAddresses.append(addresses[i].toString());
            }
            if (i != addresses.length - 1) resultAddresses.append(",");
        }
        return resultAddresses.toString();
    }

    private EmailContent processingMultipart(Multipart multipart, EmailContent emailContent) throws Exception {
        for (int y = 0; y < multipart.getCount(); y++) {
            BodyPart bp = multipart.getBodyPart(y);
            if (bp.getContentType().split(";")[0].equalsIgnoreCase("text/html") && (emailContent.getTextTypeHtml() == null || !emailContent.getTextTypeHtml())) {
                emailContent.setBody(bp.getContent().toString().replaceAll("(?i:charset=" + bp.getContentType().split("charset=")[1] + ")", "charset=utf-8"));
                emailContent.setTextTypeHtml(true);
            } else if (bp.getContentType().split(";")[0].equalsIgnoreCase("text/plain") && emailContent.getTextTypeHtml() == null) {
                emailContent.setBody(bp.getContent().toString().replaceAll("(?i:charset=" + bp.getContentType().split("charset=")[1] + ")", "charset=utf-8"));
                emailContent.setTextTypeHtml(false);
            } else if (bp.getFileName() != null) {
                String fileName = null;
                if (Pattern.matches(".*(?:\\*|\\||\\\\|\uD83D\uDE10\"|<|>|\\?|\\/).*", bp.getFileName())) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        for (String part : bp.getFileName().split("\\?=(?:$|\\s)")) {
                            String[] arrPart = part.split("\\?");
                            if (arrPart[2].equalsIgnoreCase("b")) {
                                sb.append(coderService.encoderBase64ToUtf8(arrPart[3]));
                            } else if (arrPart[2].equalsIgnoreCase("q")) {
                                sb.append(coderService.encoderQuotedPrintableToUtf8(arrPart[3]));
                            } else {
                                throw new Exception();
                            }
                        }
                        fileName = sb.toString();
                    } catch (Exception e) {
                        System.out.println(e);
                        fileName = bp.getContentType().replace("/", ".").split(";")[0];
                    }
                } else {
                    fileName = bp.getFileName();
                }
                InputStream is = bp.getInputStream();
                byte[] byteArr = IOUtils.toByteArray(is);
                EmailAttachment emailAttachment = new EmailAttachment(fileName, byteArr, emailContent);
                emailContent.addEmailAttachment(emailAttachment);
                is.close();
            } else if (bp.getContentType().split("/")[0].equalsIgnoreCase("multipart")) {
                emailContent = processingMultipart((Multipart) bp.getContent(), emailContent);
            }
        }
        return emailContent;
    }
}
