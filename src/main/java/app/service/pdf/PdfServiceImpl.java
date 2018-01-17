package app.service.pdf;

import app.entity.*;
import app.entity.enums.EmailRole;
import app.service.company.CompanyService;
import app.service.customer.CustomerService;
import app.service.emailAccount.EmailAccountService;
import app.service.outgoingInvoice.OutgoingInvoiceTrafficDetailsService;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by АРТЕМ on 15.12.2017.
 */
@Service
public class PdfServiceImpl implements PdfService {
    @Autowired
    EmailAccountService emailAccountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OutgoingInvoiceTrafficDetailsService outgoingInvoiceTrafficDetailsService;

    @Autowired
    CompanyService companyService;

    @Override
    public byte[] createPdfForOutgoingInvoice(OutgoingInvoice outgoingInvoice) {
        byte[] bytes = null;
        Customer customer = customerService.getCustomerById(outgoingInvoice.getCustomer_id());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        long numberOfSMS = 0;
        Double sum = 0.0;
        for (OutgoingInvoiceTrafficDetails outgoingInvoiceTrafficDetails: outgoingInvoiceTrafficDetailsService.getTrafficDetailsByInvoiceId(outgoingInvoice.getId())) {
            numberOfSMS += outgoingInvoiceTrafficDetails.getCount();
            sum += outgoingInvoiceTrafficDetails.getSum();
        }

        try{
            String html = emailAccountService.getAllEmailAccountByEmailRole(EmailRole.Invoice_SMS).get(0).getTextHtmlForPdfInvoice()
                    .replace("[Address]", customer.getAddress())
                    .replace("[Manager]", "") //*********************** дописать ***************************
                    .replace("[Date]", format.format(outgoingInvoice.getDate()))
                    .replace("[PeriodStart]", format.format(outgoingInvoice.getStartDate()))
                    .replace("[PeriodEnd]", format.format(outgoingInvoice.getEndDate()))
                    .replace("[InvoiceName]", companyService.getCompanyById(customer.getCompanyId()).getDocumentsPrefix() + outgoingInvoice.getId())
                    .replace("[PayDue]", format.format(outgoingInvoice.getPayDate()))
                    .replace("[NumberOfSMS]", String.valueOf(numberOfSMS))
                    .replace("[Sum]", String.valueOf(sum));

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                HtmlConverter.convertToPdf(html, oos);
                bytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            bytes = new byte[0];
        }
        return bytes;
    }
}
