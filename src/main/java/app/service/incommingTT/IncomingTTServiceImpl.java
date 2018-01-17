package app.service.incommingTT;

import app.entity.*;
import app.entity.enums.MessageIssueType;
import app.entity.enums.TTStatuses;
import app.entityXML.incomingTTXML.IncomingTTXML;
import app.repository.IncomingTTRepository;
import app.repository.MessageLogForTTRepository;
import app.service.counters.CountersService;
import app.service.customer.CustomerService;
import app.service.emailContent.EmailContentService;
import app.service.mdr.MdrService;
import app.service.messageLogForTT.MessageLogForTTService;
import app.service.outgoingTT.OutgoingTTService;
import app.service.smppClientAccount.SmppClientAccountService;
import app.service.smppClientSystemId.SmppClientSystemIdService;
import app.service.user.UserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Олег on 29.08.2017.
 */

@Service
public class IncomingTTServiceImpl implements IncomingTTService {

    @Autowired
    IncomingTTRepository incomingTTRepository;

    @Autowired
    OutgoingTTService outgoingTTService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    SmppClientAccountService smppClientAccountService;

    @Autowired
    EmailContentService emailContentService;

    @Autowired
    MessageLogForTTService messageLogForTTService;

    @Autowired
    MdrService mdrService;

    @Autowired
    SmppClientSystemIdService smppClientSystemIdService;

    @Autowired
    CountersService countersService;

    @Override
    public List<IncomingTT> getOpenedTT() {
        List<IncomingTT> returnList = incomingTTRepository.getTTListByStatus(TTStatuses.Opened);

    return returnList;
    }

    @Override
    public List<IncomingTTXML> getOpenedTTXMLList() {
        List<IncomingTTXML> returnList = new ArrayList<>();
        List<TTStatuses> listStatuses = new ArrayList<>();
        listStatuses.add(TTStatuses.Opened);
        listStatuses.add(TTStatuses.WaitingVendor);

        for (TTStatuses status : listStatuses) {
            List<IncomingTT> listTT = incomingTTRepository.getTTListByStatus(status);
            for (IncomingTT currentTt : listTT) {
                SmppClientAccount currentCustomerAccount = smppClientAccountService.getSmppClientAccountById(currentTt.getCustomer_account_id());
                //Customer currentCustomer = currentCustomerAccount.getCustomer();
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

                /*for (Long currentExtTT : currentTt.getRelatedTT()) {
                    externalTTCount++;
                    OutgoingTT currentOutTT = outgoingTTService.getTtById(currentExtTT);
                    if (currentOutTT.getStatus() == TTStatuses.Closed) {
                        externalTTClosed++;
                    }
                }*/
                String customerString = currentCustomerAccount == null ? "-" : (currentCustomerAccount.getCustomer().getName() + " - " + currentCustomerAccount.getName());
                IncomingTTXML newTTXml = new IncomingTTXML(currentTt.getId(), customerString,
                        currentTt.getDate().toGMTString(), currentTt.getDateClosed() != null ? currentTt.getDateClosed().toGMTString() : "-", currentUserOpenedName, currentUserClosedName, currentTt.getStatus().toString(), currentTt.getSubject(), externalTTCount, externalTTClosed);
                returnList.add(newTTXml);
            }
        }
        return returnList;
    }

    @Override
    public List<IncomingTTXML> getOpenedTTHistoryXMLList(Date startDate, Date endDate, Customer customer) {
        List<IncomingTTXML> returnList = new ArrayList<>();
        List<IncomingTT> currentList = incomingTTRepository.getTTListByDateInterval(startDate, endDate);


        for (IncomingTT currentTt : currentList) {
            SmppClientAccount currentCustomerAccount = smppClientAccountService.getSmppClientAccountById(currentTt.getCustomer_account_id());
            Customer currentCustomer = currentCustomerAccount.getCustomer();
            String currentUserOpenedName = "System";
            String currentUserClosedName = "";
            if (currentTt.getUserCreated() != null && currentTt.getUserCreated() != 0L) {
                CustomUser currentUserOpened = userService.getOne(currentTt.getUserCreated());
                currentUserOpenedName = currentUserOpened.getName();
            }
            if (currentTt.getUserClosed() != null && currentTt.getUserClosed() != 0L) {
                CustomUser currentUserClosed = userService.getOne(currentTt.getUserClosed());
                currentUserClosedName = currentUserClosed.getName();
            }
            if (customer!=null) {
                if(currentCustomer.getId() == customer.getId()) {
                    IncomingTTXML newTTXml = new IncomingTTXML(currentTt.getId(), currentCustomer.getName() + " - " + currentCustomerAccount.getName(), currentTt.getDate().toGMTString(), currentTt.getDateClosed() != null ? currentTt.getDateClosed().toGMTString() : "-", currentUserOpenedName, currentUserClosedName, currentTt.getStatus().toString(), currentTt.getSubject());
                    returnList.add(newTTXml);
                }
            } else {
                IncomingTTXML newTTXml = new IncomingTTXML(currentTt.getId(), currentCustomer.getName() + " - " + currentCustomerAccount.getName(), currentTt.getDate().toGMTString(), currentTt.getDateClosed() != null ? currentTt.getDateClosed().toGMTString() : "-", currentUserOpenedName, currentUserClosedName, currentTt.getStatus().toString(), currentTt.getSubject());
                returnList.add(newTTXml);
            }
        }

        return returnList;
    }

    @Override
    public IncomingTT getTtById(Long id) {
        return incomingTTRepository.findOne(id);
    }

    @Override
    @Transactional
    public IncomingTT save(IncomingTT newTT) {
        incomingTTRepository.save(newTT);
        return newTT;
    }

    @Override
    @Transactional
    public IncomingTT addIncomingTT(Date date, Long customer_account_id, String subject, String solution, TTStatuses status, Long userCreated, Long userClosed){
        IncomingTT incomingTT = new IncomingTT(new Date(), customer_account_id, subject, solution, status, userCreated);
        if (status.equals(TTStatuses.Closed) || status.equals(TTStatuses.ClosesOurProblem)) {
            incomingTT.setDateClosed(new Date());
            incomingTT.setUserClosed(userClosed);
        }
        incomingTTRepository.save(incomingTT);
        countersService.increaseCounterByKey("incoming_tt");
        return incomingTT;
    }

    @Override
    @Transactional
    public IncomingTT updateIncomingTT(Long id, Long customer_account_id, String subject, String solution, TTStatuses status, Long userClosed){
        IncomingTT incomingTT = incomingTTRepository.findOne(id);
        incomingTT.setCustomer_account_id(customer_account_id);
        incomingTT.setSubject(subject);
        incomingTT.setSolution(solution);
        if (!incomingTT.getStatus().equals(TTStatuses.Closed) && !incomingTT.getStatus().equals(TTStatuses.ClosesOurProblem) && (status.equals(TTStatuses.Closed) || status.equals(TTStatuses.ClosesOurProblem))){
            incomingTT.setDateClosed(new Date());
            incomingTT.setUserClosed(userClosed);
        }
        incomingTT.setStatus(status);
        incomingTTRepository.save(incomingTT);

        if (incomingTT.getStatus() == TTStatuses.Closed) {
            countersService.decreaseCounterByKey("incoming_tt");
        }

        return incomingTT;
    }

    @Override
    @Transactional
    public boolean setEmailContent(EmailContent emailContent)/* throws Exception*/{
        String subject = emailContent.getSubject();
        if (Pattern.matches(".*\\[ITT#.*\\].*", subject) && incomingTTRepository.findOne(Long.parseLong(subject.replaceFirst(".*\\[ITT#", "").replaceFirst("\\].*", ""))) != null){

            //====Существующий ТТ====
            IncomingTT incomingTT = incomingTTRepository.findOne(Long.parseLong(subject.replaceFirst(".*\\[ITT#", "").replaceFirst("\\].*", "")));
            incomingTT.addEmailContent(emailContent);
            incomingTTRepository.save(incomingTT);
            emailContentService.updateIncomingTT(emailContent, incomingTT);
            return false;
        }else{

            //====Новый ТТ====
            IncomingTT incomingTT = new IncomingTT(
                    emailContent.getDate(),
                    0l,
                    emailContent.getSubject(),
                    "",
                    TTStatuses.Opened,
                    0l);
            incomingTT.setUserCreated(0L);
            Document doc = Jsoup.parseBodyFragment(emailContent.getBody());
            Element body = doc.body();
            Pattern pattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
            Matcher matcher = pattern.matcher(body.text());
            List<MessageLogForTT> messageLogForTTList = new ArrayList<>();
            while (matcher.find()) {
                messageLogForTTList.add(new MessageLogForTT(matcher.group(), MessageIssueType.Other));
            }
            if (messageLogForTTList.size() == 0){
                incomingTT.setCustomer_account_id(0L);
            }else {
                for (MessageLogForTT m: messageLogForTTList) {
                    Mdr mdr = null;
                    if ((mdr = mdrService.getMdrByMsgId(m.getRelatedMsgid())) != null){
                        SmppClientSystemId smppClientSystemId = smppClientSystemIdService.getSmppClientSystemIdByUid(mdr.getUid());
                        incomingTT.setCustomer_account_id(smppClientSystemId.getSmppClientAccount().getId());
                        break;
                    }
                }
            }
            incomingTT.setMessageLogForTTList(new HashSet<>(messageLogForTTList));
            incomingTTRepository.save(incomingTT);
            for(MessageLogForTT messageLogForTT : messageLogForTTList){
                messageLogForTTService.updateMessageLogForTT(messageLogForTT.getId(), messageLogForTT.getRelatedMsgid(), messageLogForTT.getIssueType(), incomingTT, null);
            }
            emailContentService.updateIncomingTT(emailContent, incomingTT);
            return true;
        }
    }

}
