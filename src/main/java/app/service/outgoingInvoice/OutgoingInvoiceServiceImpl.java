package app.service.outgoingInvoice;

import app.entity.EmailContent;
import app.entity.OutgoingInvoice;
import app.repository.OutgoingInvoiceRepository;
import app.service.emailContent.EmailContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Олег on 06.11.2017.
 */

@Service
public class OutgoingInvoiceServiceImpl implements OutgoingInvoiceService {

    @Autowired
    private OutgoingInvoiceRepository outgoingInvoiceRepository;

    @Autowired
    private EmailContentService emailContentService;

    @Override
    public OutgoingInvoice save(OutgoingInvoice outgoingInvoice) {
        return outgoingInvoiceRepository.save(outgoingInvoice);
    }

    @Override
    public List<OutgoingInvoice> getInvoicesSort() {
        return outgoingInvoiceRepository.getInvoicesAllSort();
    }

    @Override
    public OutgoingInvoice getInvoiceById(Long id) {
        return outgoingInvoiceRepository.findOne(id);
    }

    @Override
    @Transactional
    public void updateConfirmed(Long id_invoice, Boolean confirmed) throws Exception{
        OutgoingInvoice outgoingInvoice = outgoingInvoiceRepository.findOne(id_invoice);
        outgoingInvoice.setConfirmed(confirmed);
        outgoingInvoiceRepository.save(outgoingInvoice);
    }

    @Override
    @Transactional
    public void createEmail(){
        List<OutgoingInvoice> outgoingInvoiceList = outgoingInvoiceRepository.findAllByConfirmedAndProcessed(true, false);
        for (OutgoingInvoice outgoingInvoice: outgoingInvoiceList) {
            EmailContent emailContent = emailContentService.outgoingInvoiceEmailContent(outgoingInvoice);
            outgoingInvoice.addEmailContent(emailContent);
            outgoingInvoice.setProcessed(true);
        }
        outgoingInvoiceRepository.save(outgoingInvoiceList);
    }
}
