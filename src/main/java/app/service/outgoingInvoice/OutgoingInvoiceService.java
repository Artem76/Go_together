package app.service.outgoingInvoice;

import app.entity.OutgoingInvoice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Олег on 06.11.2017.
 */
public interface OutgoingInvoiceService {
    List<OutgoingInvoice> getInvoicesSort();
    OutgoingInvoice save(OutgoingInvoice outgoingInvoice);
    OutgoingInvoice getInvoiceById(Long id);

    @Transactional
    void updateConfirmed(Long id_invoice, Boolean confirmed) throws Exception;

    @Transactional
    void createEmail();
}
