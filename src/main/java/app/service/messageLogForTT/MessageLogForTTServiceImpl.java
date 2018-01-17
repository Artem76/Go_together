package app.service.messageLogForTT;

import app.entity.IncomingTT;
import app.entity.MessageLogForTT;
import app.entity.OutgoingTT;
import app.entity.enums.MessageIssueType;
import app.repository.MessageLogForTTRepository;
import app.service.outgoingTT.OutgoingTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by АРТЕМ on 17.10.2017.
 */
@Service
public class MessageLogForTTServiceImpl implements MessageLogForTTService {
    @Autowired
    MessageLogForTTRepository messageLogForTTRepository;

    @Autowired
    OutgoingTTService outgoingTTService;

    @Override
    public MessageLogForTT getMessageLogForTTById(Long id) {
        return messageLogForTTRepository.findOne(id);
    }

    @Override
    public List<MessageLogForTT> getMessageLogForTTByMsgidAndIncomingTT(String msgid, IncomingTT incomingTT) {
        return messageLogForTTRepository.findByRelatedMsgidAndIncomingTT(msgid, incomingTT);
    }

    @Override
    public List<MessageLogForTT> getMessagelogForTTByMsgidAndOutgoingTT(String msgid, OutgoingTT outgoingTT) {
        List<MessageLogForTT> messageLogForTTList = new ArrayList<>();
        for (MessageLogForTT ml: outgoingTT.getMessageLogForTTList()){
            if (ml.getRelatedMsgid().equals(msgid)) messageLogForTTList.add(ml);
        }
        return messageLogForTTList;
    }

    @Override
    public List<MessageLogForTT> getMessageLogForTTByIncomingTT(IncomingTT incomingTT) {
        return messageLogForTTRepository.findByIncomingTT(incomingTT);
    }

    @Override
    public List<MessageLogForTT> getMessageLogForTTByOutgoingTT(OutgoingTT outgoingTT) {
        return new ArrayList<>(outgoingTT.getMessageLogForTTList());
    }

    @Override
    public List<MessageLogForTT> getMessageLogForTTByOutgoingTTAndIssueType(OutgoingTT outgoingTT, MessageIssueType issueType) {
        List<MessageLogForTT> messageLogForTTList = new ArrayList<>();
        for (MessageLogForTT ml: outgoingTT.getMessageLogForTTList()){
            if (ml.getIssueType().equals(issueType)) messageLogForTTList.add(ml);
        }
        return messageLogForTTList;
    }

    @Override
    @Transactional
    public MessageLogForTT addMessageLogForIncomingTT(String msgid, MessageIssueType issueType, IncomingTT incomingTT) {
        if (incomingTT == null) return null;
        if (msgid == null || msgid.equals("") || messageLogForTTRepository.findByRelatedMsgidAndIncomingTT(msgid, incomingTT).size() > 0)
            return null;
        MessageLogForTT messageLogForTT = new MessageLogForTT(msgid, issueType);
        messageLogForTT.setIncomingTT(incomingTT);
        messageLogForTTRepository.save(messageLogForTT);
        return messageLogForTT;
    }

    @Override
    @Transactional
    public MessageLogForTT addMessageLogForOutgoingTT(String msgid, MessageIssueType issueType, OutgoingTT outgoingTT) {
        if (outgoingTT == null) return null;
        if (msgid == null || msgid.equals("")) return null;
        if (getMessagelogForTTByMsgidAndOutgoingTT(msgid, outgoingTT).size() > 0) return null;
        MessageLogForTT messageLogForTT = new MessageLogForTT(msgid, issueType);
        messageLogForTT.addOutgoingTT(outgoingTT);
        outgoingTT.addMessageLogForTT(messageLogForTT);
        messageLogForTTRepository.save(messageLogForTT);
        outgoingTTService.save(outgoingTT);
        return messageLogForTT;
    }

    @Override
    @Transactional
    public void addOutgoingTT(List<MessageLogForTT> messageLogForTTList, OutgoingTT outgoingTT){
        List<MessageLogForTT> messageLogForTTList1 = new ArrayList<>(messageLogForTTList);
        for (MessageLogForTT messageLogForTT : messageLogForTTList1){
            messageLogForTT.addOutgoingTT(outgoingTT);
            messageLogForTTRepository.save(messageLogForTT);
        }
    }

    @Override
    @Transactional
    public MessageLogForTT updateMessageLogForTT(Long id, String msgid, MessageIssueType issueType, IncomingTT incomingTT, OutgoingTT outgoingTT) {
        MessageLogForTT messageLogForTT = messageLogForTTRepository.findOne(id);
        if (messageLogForTT == null) return null;
        if (incomingTT == null && messageLogForTT.getIncomingTT() == null && messageLogForTT.getOutgoingTTList().size() == 0 && outgoingTT == null)
            return null;
        if (incomingTT != null && messageLogForTTRepository.findByRelatedMsgidAndIncomingTTExceptId(msgid, incomingTT, id).size() > 0)
            return null;

        if (outgoingTT != null){
            for (MessageLogForTT ml: outgoingTT.getMessageLogForTTList()){
                if (ml.getId() != id && ml.getRelatedMsgid().equals(msgid)) return null;
            }
        }
        messageLogForTT.setRelatedMsgids(msgid);
        messageLogForTT.setIssueType(issueType);
        if (incomingTT != null) messageLogForTT.setIncomingTT(incomingTT);
        if (outgoingTT != null && !messageLogForTT.getOutgoingTTList().contains(outgoingTT)){
            messageLogForTT.addOutgoingTT(outgoingTT);
            outgoingTT.addMessageLogForTT(messageLogForTT);
        }
        messageLogForTTRepository.save(messageLogForTT);
        if (outgoingTT != null) outgoingTTService.save(outgoingTT);
        return messageLogForTT;
    }

    @Override
    @Transactional
    public void deleteIncomingTT(Long id_messageLogForTT) {
        MessageLogForTT messageLogForTT = messageLogForTTRepository.findOne(id_messageLogForTT);
        messageLogForTT.setIncomingTT(null);
        messageLogForTTRepository.save(messageLogForTT);
//        if (messageLogForTT.getIncomingTT() == null && messageLogForTT.getOutgoingTTList().size() == 0) {
//            messageLogForTTRepository.delete(messageLogForTT);
//        }
    }

    @Override
    @Transactional
    public void deleteOutgoingTT(Long id_messageLogForTT, OutgoingTT outgoingTT) {
        MessageLogForTT messageLogForTT = messageLogForTTRepository.findOne(id_messageLogForTT);
        messageLogForTT.deleteOutgoingTT(outgoingTT);
        outgoingTT.deleteMessageLogForTT(messageLogForTT);
        messageLogForTTRepository.save(messageLogForTT);
        outgoingTTService.save(outgoingTT);
//        if (messageLogForTT.getIncomingTT() == null && messageLogForTT.getOutgoingTTList().size() == 0) {
//            messageLogForTTRepository.delete(messageLogForTT);
//        }
    }
}
