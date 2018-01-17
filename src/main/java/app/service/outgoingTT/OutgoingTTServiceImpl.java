package app.service.outgoingTT;

import app.entity.*;
import app.entity.enums.TTStatuses;
import app.entityXML.incomingTTXML.OutgoingTTXML;
import app.repository.OutgoingTTRepository;
import app.service.counters.CountersService;
import app.service.customer.CustomerService;
import app.service.emailContent.EmailContentService;
import app.service.smppVendorAccount.SmppVendorAccountService;
import app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Олег on 31.08.2017.
 */

@Service
public class OutgoingTTServiceImpl implements OutgoingTTService {

    @Autowired
    OutgoingTTRepository outgoingTTRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    SmppVendorAccountService smppVendorAccountService;

    @Autowired
    EmailContentService emailContentService;

    @Autowired
    CountersService countersService;

    @Override
    public List<OutgoingTT> getOpenedTT() {
        List<OutgoingTT> returnList = outgoingTTRepository.getTTListByStatus(TTStatuses.Opened);

        return returnList;
    }

    @Override
    public List<OutgoingTTXML> getOpenedTTXMLList() {
        List<OutgoingTTXML> returnList = new ArrayList<>();
        List<TTStatuses> listStatuses = new ArrayList<>();
        listStatuses.add(TTStatuses.Opened);
        listStatuses.add(TTStatuses.WaitingVendor);

        for (TTStatuses status : listStatuses) {
            List<OutgoingTT> listTT = outgoingTTRepository.getTTListByStatus(status);
            for (OutgoingTT currentTt : listTT) {
                SmppVendorAccount currentCustomerAccount = smppVendorAccountService.getSmppVendorAccountById(currentTt.getCustomer_account_id());
//                //Customer currentCustomer = currentCustomerAccount.getCustomer();
                String currentUserOpenedName = "";
                String currentUserClosedName = "";
                if (currentTt.getUserCreated()!=null) {
                    CustomUser currentUserOpened = userService.getOne(currentTt.getUserCreated());
                    if (currentUserOpened==null) {
                        currentUserOpenedName = "System";
                    } else {
                        currentUserOpenedName = currentUserOpened.getName();
                    }

                }
                if (currentTt.getUserClosed()!=null) {
                    CustomUser currentUserClosed = userService.getOne(currentTt.getUserClosed());
                    currentUserClosedName = currentUserClosed.getName();
                }
                Integer externalTTCount = 0;
                Integer externalTTClosed = 0;
//
//                for (Long currentExtTT : currentTt.getRelatedTT()) {
//                    externalTTCount++;
//                    OutgoingTT currentOutTT = outgoingTTService.getTtById(currentExtTT);
//                    if (currentOutTT.getStatus() == TTStatuses.Closed) {
//                        externalTTClosed++;
//                    }
//                }
                String customerString = currentCustomerAccount == null ? "-" : (currentCustomerAccount.getCustomer().getName() + " - " + currentCustomerAccount.getName());
                OutgoingTTXML newTTXml = new OutgoingTTXML(currentTt.getId(), customerString,
                        currentTt.getDate().toGMTString(), currentTt.getDateClosed() != null ? currentTt.getDateClosed().toGMTString() : "-", currentUserOpenedName, currentUserClosedName, currentTt.getStatus().toString(), currentTt.getSubject(), externalTTCount, externalTTClosed);
                returnList.add(newTTXml);
            }
        }
        return returnList;
    }

    @Override
    public List<OutgoingTTXML> getOpenedTTHistoryXMLList(Date startDate, Date endDate, Customer customer) {
        List<OutgoingTTXML> returnList = new ArrayList<>();
        List<OutgoingTT> currentList = outgoingTTRepository.getTTListByDateInterval(startDate, endDate);


        for (OutgoingTT currentTt : currentList) {
            SmppVendorAccount currentCustomerAccount = smppVendorAccountService.getSmppVendorAccountById(currentTt.getCustomer_account_id());
            Customer currentCustomer = currentCustomerAccount.getCustomer();
            String currentUserOpenedName = "";
            String currentUserClosedName = "";
            if (currentTt.getUserCreated()!=null) {
                CustomUser currentUserOpened = userService.getOne(currentTt.getUserCreated());
                currentUserOpenedName = currentUserOpened.getName();
            }
            if (currentTt.getUserClosed()!=null) {
                CustomUser currentUserClosed = userService.getOne(currentTt.getUserClosed());
                currentUserClosedName = currentUserClosed.getName();
            }
            if (customer!=null) {
                if(currentCustomer.getId() == customer.getId()) {
                    OutgoingTTXML newTTXml = new OutgoingTTXML(currentTt.getId(), currentCustomer.getName() + " - " + currentCustomerAccount.getName(), currentTt.getDate().toGMTString(), currentTt.getDateClosed() != null ? currentTt.getDateClosed().toGMTString() : "-", currentUserOpenedName, currentUserClosedName, currentTt.getStatus().toString(), currentTt.getSubject());
                    returnList.add(newTTXml);
                }
            } else {
                OutgoingTTXML newTTXml = new OutgoingTTXML(currentTt.getId(), currentCustomer.getName() + " - " + currentCustomerAccount.getName(), currentTt.getDate().toGMTString(), currentTt.getDateClosed() != null ? currentTt.getDateClosed().toGMTString() : "-", currentUserOpenedName, currentUserClosedName, currentTt.getStatus().toString(), currentTt.getSubject());
                returnList.add(newTTXml);
            }
        }

        return returnList;
    }

    @Override
    public OutgoingTT getTtById(Long id) {
        return outgoingTTRepository.findOne(id);
    }

    @Override
    @Transactional
    public void save(OutgoingTT newTT) {
        outgoingTTRepository.save(newTT);
    }

    @Override
    @Transactional
    public OutgoingTT addOutgoingTT(Date date, Long customer_account_id, String subject, String solution, TTStatuses status, Long userCreated, Long userClosed){
        OutgoingTT outgoingTT = new OutgoingTT(new Date(), customer_account_id, subject, solution, status, userCreated);
        if (status.equals(TTStatuses.Closed) || status.equals(TTStatuses.ClosesOurProblem)) {
            outgoingTT.setDateClosed(new Date());
            outgoingTT.setUserClosed(userClosed);
        }
        outgoingTTRepository.save(outgoingTT);
        countersService.increaseCounterByKey("incoming_tt");
        return outgoingTT;
    }

    @Override
    @Transactional
    public OutgoingTT updateOutgoingTT(Long id, Long customer_account_id, String subject, String solution, TTStatuses status, Long userClosed){
        OutgoingTT outgoingTT = outgoingTTRepository.findOne(id);
        outgoingTT.setCustomer_account_id(customer_account_id);
        outgoingTT.setSubject(subject);
        outgoingTT.setSolution(solution);
        if (!outgoingTT.getStatus().equals(TTStatuses.Closed) && !outgoingTT.getStatus().equals(TTStatuses.ClosesOurProblem) && (status.equals(TTStatuses.Closed) || status.equals(TTStatuses.ClosesOurProblem))){
            outgoingTT.setDateClosed(new Date());
            outgoingTT.setUserClosed(userClosed);
        }
        outgoingTT.setStatus(status);
        outgoingTTRepository.save(outgoingTT);

        if (outgoingTT.getStatus() == TTStatuses.Closed) {
            countersService.decreaseCounterByKey("incoming_tt");
        }
        return outgoingTT;
    }

    @Override
    @Transactional
    public void setEmailContent(EmailContent emailContent){
        String subject = emailContent.getSubject();
        if (Pattern.matches(".*\\[OTT#.*\\].*", subject) && outgoingTTRepository.findOne(Long.parseLong(subject.replaceFirst(".*\\[OTT#", "").replaceFirst("\\].*", ""))) != null){
            OutgoingTT outgoingTT = outgoingTTRepository.findOne(Long.parseLong(subject.replaceFirst(".*\\[OTT#", "").replaceFirst("\\].*", "")));
            outgoingTT.addEmailContent(emailContent);
            outgoingTTRepository.save(outgoingTT);
            emailContentService.updateOutgoingTT(emailContent, outgoingTT);
        }
    }
}
