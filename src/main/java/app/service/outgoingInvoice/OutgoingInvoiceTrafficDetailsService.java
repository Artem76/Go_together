package app.service.outgoingInvoice;

import app.entity.OutgoingInvoiceTrafficDetails;
import app.entityXML.outgoingInvoiceTrafficDetails.OutgoingInvoiceTrafficDetailsRow;

import java.util.List;

/**
 * Created by Олег on 07.11.2017.
 */
public interface OutgoingInvoiceTrafficDetailsService {
    List<OutgoingInvoiceTrafficDetails> getTrafficDetailsByInvoiceId(Long id);
    void save(OutgoingInvoiceTrafficDetails outgoingInvoiceTrafficDetails);

    List<OutgoingInvoiceTrafficDetailsRow> getTrafficDetailsRowList(Long id_invoice);
}
