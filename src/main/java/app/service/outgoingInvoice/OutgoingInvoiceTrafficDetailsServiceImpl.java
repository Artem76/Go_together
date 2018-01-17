package app.service.outgoingInvoice;

import app.entity.OutgoingInvoiceTrafficDetails;
import app.entity.Refbook;
import app.entity.SmppClientSystemId;
import app.entityXML.outgoingInvoiceTrafficDetails.OutgoingInvoiceTrafficDetailsRow;
import app.repository.OutgoingInvoiceTrafficDetailsRepository;
import app.service.refbook.RefbookService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олег on 07.11.2017.
 */

@Service
public class OutgoingInvoiceTrafficDetailsServiceImpl implements OutgoingInvoiceTrafficDetailsService {

    @Autowired
    OutgoingInvoiceTrafficDetailsRepository outgoingInvoiceTrafficDetailsRepository;

    @Autowired
    RefbookService refbookService;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Override
    public List<OutgoingInvoiceTrafficDetails> getTrafficDetailsByInvoiceId(Long id) {
        return outgoingInvoiceTrafficDetailsRepository.getDetailByInvoiceId(id);
    }

    @Override
    public void save(OutgoingInvoiceTrafficDetails outgoingInvoiceTrafficDetails) {
        outgoingInvoiceTrafficDetailsRepository.save(outgoingInvoiceTrafficDetails);
    }

    @Override
    public List<OutgoingInvoiceTrafficDetailsRow> getTrafficDetailsRowList(Long id_invoice){
        List<OutgoingInvoiceTrafficDetails> detailsList = getTrafficDetailsByInvoiceId(id_invoice);
        List<OutgoingInvoiceTrafficDetailsRow> list = new ArrayList<>();
        for (OutgoingInvoiceTrafficDetails currentElement : detailsList) {
            String country = "";
            String network = "";

            if (currentElement.getMnc().equals("Flt")) {
                Refbook currentRefbook = refbookService.getRootRefbookByMcc(currentElement.getMcc());
                if (currentRefbook != null) {
                    country = currentRefbook.getCountry();
                    network = "FLAT";
                }
            } else {
                Refbook currentRefbookCountry = refbookService.getRootRefbookByMcc(currentElement.getMcc());
                if (currentRefbookCountry != null) {
                    country = currentRefbookCountry.getCountry();
                }
                List<Refbook> currentRefbookNetworkList = refbookService.getRefbookByMccAndMnc(currentElement.getMcc(), currentElement.getMnc());
                if (currentRefbookNetworkList.size() != 0) {
                    Refbook currentRefbookNetwork = currentRefbookNetworkList.get(0);
                    if (currentRefbookNetwork != null) {
                        network = currentRefbookNetwork.getNetwork();
                    } else {
                        currentRefbookNetwork = refbookService.getRootRefbookByMcc(currentElement.getMcc());
                        if (currentRefbookNetwork != null) {
                            country = currentRefbookNetwork.getCountry();
                        }
                    }
                }

            }

            String uid = "Not Found";
            if (currentElement.getUid() != null) {
                SmppClientSystemId currentIps = smppClientSystemIdService.getSmppClientSystemIdByUid(currentElement.getUid());
                if (currentIps != null) {
                    uid = currentIps.getSystemId();
                }
            }
            list.add(new OutgoingInvoiceTrafficDetailsRow(currentElement.getMcc(), currentElement.getMnc(), country, network, currentElement.getCount(), currentElement.getSum(), uid));
        }
        return list;
    }
}
