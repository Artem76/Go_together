package app.service.incomingInvoice;

import app.entity.IncomingInvoice;
import app.repository.IncomingInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 08.11.2017.
 */
@Service
public class IncomingInvoiceServiceImpl implements IncomingInvoiceService {

    @Autowired
    IncomingInvoiceRepository incomingInvoiceRepository;

    @Override
    public void save(IncomingInvoice incomingInvoice) {
        incomingInvoiceRepository.save(incomingInvoice);
    }

    @Override
    public List<IncomingInvoice> getInvoicesSort() {
        return incomingInvoiceRepository.getInvoicesAllSort();
    }

    @Override
    public IncomingInvoice getInvoiceById(Long id) {
        return incomingInvoiceRepository.findOne(id);
    }
}
