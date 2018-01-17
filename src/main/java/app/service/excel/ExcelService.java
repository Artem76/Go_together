package app.service.excel;

import app.entity.ClientRatesUpdate;
import app.entity.EmailAttachment;
import app.entity.EmailContent;
import app.entity.OutgoingInvoice;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by АРТЕМ on 28.08.2017.
 */
public interface ExcelService {
    Map<String,String> setExcelGetStringForEmail(EmailAttachment emailAttachment) throws IOException;
    Map<String,String> setExcelGetStringForPrice(MultipartFile multipartFile) throws IOException;

    Map<String, String> setAttachmentExcelGetStringForPrice(EmailAttachment emailAttachment) throws IOException;

    List<EmailAttachment> setClientRnGetEmailAttachmentXlsxCsv(ClientRatesUpdate clientRatesUpdate, EmailContent emailContent);

    byte[] createCsvForOutgoingInvoice(OutgoingInvoice outgoingInvoice);
}
