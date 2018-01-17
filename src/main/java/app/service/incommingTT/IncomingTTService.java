package app.service.incommingTT;

import app.entity.Customer;
import app.entity.EmailContent;
import app.entity.IncomingTT;
import app.entity.enums.TTStatuses;
import app.entityXML.incomingTTXML.IncomingTTXML;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 29.08.2017.
 */
public interface IncomingTTService {
    List<IncomingTT> getOpenedTT();
    List<IncomingTTXML> getOpenedTTXMLList();
    List<IncomingTTXML> getOpenedTTHistoryXMLList(Date startDate, Date endDate, Customer customer);
    IncomingTT getTtById(Long id);
    IncomingTT save(IncomingTT netTT);

    @Transactional
    IncomingTT addIncomingTT(Date date, Long customer_account_id, String subject, String solution, TTStatuses status, Long userCreated, Long userClosed);

    @Transactional
    IncomingTT updateIncomingTT(Long id, Long customer_account_id, String subject, String solution, TTStatuses status, Long userClosed);

    boolean setEmailContent(EmailContent emailContent) throws Exception;
}
