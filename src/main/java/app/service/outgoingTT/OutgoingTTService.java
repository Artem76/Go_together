package app.service.outgoingTT;

import app.entity.Customer;
import app.entity.EmailContent;
import app.entity.OutgoingTT;
import app.entity.enums.TTStatuses;
import app.entityXML.incomingTTXML.IncomingTTXML;
import app.entityXML.incomingTTXML.OutgoingTTXML;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 31.08.2017.
 */
public interface OutgoingTTService {
    List<OutgoingTT> getOpenedTT();

    List<OutgoingTTXML> getOpenedTTXMLList();

    List<OutgoingTTXML> getOpenedTTHistoryXMLList(Date startDate, Date endDate, Customer customer);

    //List<OutgoingTTXML> getOpenedTTXMLList();
    //List<OutgoingTTXML> getOpenedTTHistoryXMLList(Date startDate, Date endDate, Customer customer);
    OutgoingTT getTtById(Long id);
    void save(OutgoingTT netTT);

    @Transactional
    OutgoingTT addOutgoingTT(Date date, Long customer_account_id, String subject, String solution, TTStatuses status, Long userCreated, Long userClosed);

    @Transactional
    OutgoingTT updateOutgoingTT(Long id, Long customer_account_id, String subject, String solution, TTStatuses status, Long userClosed);

    @Transactional
    void setEmailContent(EmailContent emailContent);
}
