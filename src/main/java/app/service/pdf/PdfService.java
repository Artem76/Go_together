package app.service.pdf;

import app.entity.EmailAttachment;
import app.entity.EmailContent;
import app.entity.OutgoingInvoice;

/**
 * Created by АРТЕМ on 15.12.2017.
 */
public interface PdfService {
    byte[] createPdfForOutgoingInvoice(OutgoingInvoice outgoingInvoice);
}
