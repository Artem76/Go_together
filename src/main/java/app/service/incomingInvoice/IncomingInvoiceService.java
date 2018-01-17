package app.service.incomingInvoice;

import app.entity.IncomingInvoice;

import java.util.List;

/**
 * Created by Олег on 08.11.2017.
 */
public interface IncomingInvoiceService {
    List<IncomingInvoice> getInvoicesSort();
    void save(IncomingInvoice incomingInvoice);
    IncomingInvoice getInvoiceById(Long id);

}
