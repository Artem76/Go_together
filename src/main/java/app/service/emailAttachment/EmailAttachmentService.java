package app.service.emailAttachment;

import app.entity.ClientRatesUpdate;
import app.entity.EmailAttachment;
import app.entity.EmailContent;
import app.entity.OutgoingInvoice;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by АРТЕМ on 22.09.2017.
 */
public interface EmailAttachmentService {
    EmailAttachment getEmailAttachmentById(Long id);

    List<EmailAttachment> createAttachmentsForOutgoingInvoice(OutgoingInvoice outgoingInvoice);

    @Transactional
    EmailAttachment save(EmailAttachment emailAttachment);

    @Transactional
    List<EmailAttachment> saveList(List<EmailAttachment> emailAttachmentList);

    @Transactional
    void  createAttachmentForCustomer(Long id_customer, MultipartFile file) throws Exception;

    @Transactional
    void deleteAttachment(Long id) throws Exception;
}
