package app.service.incomingInvoice;

import app.entity.IncomingInvoiceTrafficDetails;
import app.repository.IncomingInvoiceTrafficDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 08.11.2017.
 */
@Service
public class IncomingInvoiceTrafficDetailsServiceImpl implements IncomingInvoiceTrafficDetailsService {

    @Autowired
    IncomingInvoiceTrafficDetailsRepository incomingInvoiceTrafficDetailsRepository;


    @Override
    public List<IncomingInvoiceTrafficDetails> getTrafficDetailsByInvoiceId(Long id) {
        return incomingInvoiceTrafficDetailsRepository.getDetailByInvoiceId(id);
    }

    @Override
    public void save(IncomingInvoiceTrafficDetails incomingInvoiceTrafficDetails) {
        incomingInvoiceTrafficDetailsRepository.save(incomingInvoiceTrafficDetails);
    }
}
