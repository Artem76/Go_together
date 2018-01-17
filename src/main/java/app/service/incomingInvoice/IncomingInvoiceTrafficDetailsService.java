package app.service.incomingInvoice;

import app.entity.IncomingInvoiceTrafficDetails;

import java.util.List;

/**
 * Created by Олег on 08.11.2017.
 */
public interface IncomingInvoiceTrafficDetailsService {
    List<IncomingInvoiceTrafficDetails> getTrafficDetailsByInvoiceId(Long id);
    void save(IncomingInvoiceTrafficDetails incomingInvoiceTrafficDetails);
}
