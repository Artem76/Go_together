package app.service.emailAttachment;

import app.entity.Customer;
import app.entity.EmailAttachment;
import app.entity.OutgoingInvoice;
import app.repository.EmailAttachmentRepository;
import app.service.company.CompanyService;
import app.service.customer.CustomerService;
import app.service.excel.ExcelService;
import app.service.pdf.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 22.09.2017.
 */
@Service
public class EmailAttachmentServiceImpl implements EmailAttachmentService{
    @Autowired
    private EmailAttachmentRepository emailAttachmentRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExcelService excelService;

    @Override
    public EmailAttachment getEmailAttachmentById(Long id) {
        return emailAttachmentRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<EmailAttachment> createAttachmentsForOutgoingInvoice(OutgoingInvoice outgoingInvoice){
        String nameInvoice = new String();
        try{
            Customer customer = customerService.getCustomerById(outgoingInvoice.getCustomer_id());
            nameInvoice = companyService.getCompanyById(customer.getCompanyId()).getDocumentsPrefix() + outgoingInvoice.getId();
        }catch (Exception e){
            nameInvoice = String.valueOf(outgoingInvoice.getId());
        }

        List<EmailAttachment> emailAttachmentList;
        if (outgoingInvoice.getEmailAttachmentList() == null || outgoingInvoice.getEmailAttachmentList().size() != 2){
            emailAttachmentList = new ArrayList<>();
            emailAttachmentList.add(new EmailAttachment(nameInvoice + ".pdf", pdfService.createPdfForOutgoingInvoice(outgoingInvoice), null, outgoingInvoice));
            emailAttachmentList.add(new EmailAttachment(nameInvoice + "_Traffic_details.csv", excelService.createCsvForOutgoingInvoice(outgoingInvoice), null, outgoingInvoice));
        }else {
            emailAttachmentList = new ArrayList<>(outgoingInvoice.getEmailAttachmentList());
            for (EmailAttachment emailAttachment: new ArrayList<>(emailAttachmentList)) {
                if (emailAttachment.getFileName().replaceAll("^.*\\.", "").equalsIgnoreCase("pdf")){
                    emailAttachment.setFileName(nameInvoice + ".pdf");
                    emailAttachment.setFileBody(pdfService.createPdfForOutgoingInvoice(outgoingInvoice));
                    emailAttachment.setEmailContent(null);
                    emailAttachment.setOutgoingInvoice(outgoingInvoice);
                }else {
                    emailAttachment.setFileName(nameInvoice + "_Traffic_details.csv");
                    emailAttachment.setFileBody(excelService.createCsvForOutgoingInvoice(outgoingInvoice));
                    emailAttachment.setEmailContent(null);
                    emailAttachment.setOutgoingInvoice(outgoingInvoice);
                }
                emailAttachmentList.add(emailAttachment);
            }
        }

        emailAttachmentList = emailAttachmentRepository.save(emailAttachmentList);
        return emailAttachmentList;
    }

    @Override
    @Transactional
    public EmailAttachment save(EmailAttachment emailAttachment){
        return emailAttachmentRepository.save(emailAttachment);
    }

    @Override
    @Transactional
    public List<EmailAttachment> saveList(List<EmailAttachment> emailAttachmentList){
        return emailAttachmentRepository.save(emailAttachmentList);
    }

    @Override
    @Transactional
    public void  createAttachmentForCustomer(Long id_customer, MultipartFile file) throws Exception{
        Customer customer = customerService.getCustomerById(id_customer);
        EmailAttachment emailAttachment = new EmailAttachment(file.getOriginalFilename(), file.getBytes(), null, null, customer);
        emailAttachment = emailAttachmentRepository.save(emailAttachment);
        customer.addEmailAttachment(emailAttachment);
        customerService.save(customer);
    }

    @Override
    @Transactional
    public void deleteAttachment(Long id) throws Exception{
        EmailAttachment emailAttachment = emailAttachmentRepository.findOne(id);
        emailAttachment.setEmailContent(null);
        emailAttachment.setOutgoingInvoice(null);
        emailAttachment.setCustomer(null);
        emailAttachmentRepository.save(emailAttachment);
        emailAttachmentRepository.delete(emailAttachment);
    }
}
