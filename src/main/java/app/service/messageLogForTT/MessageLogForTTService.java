package app.service.messageLogForTT;

import app.entity.IncomingTT;
import app.entity.MessageLogForTT;
import app.entity.OutgoingTT;
import app.entity.enums.MessageIssueType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by АРТЕМ on 17.10.2017.
 */
public interface MessageLogForTTService {
    MessageLogForTT getMessageLogForTTById(Long id);
    List<MessageLogForTT> getMessageLogForTTByMsgidAndIncomingTT(String msgid, IncomingTT incomingTT);
    List<MessageLogForTT> getMessagelogForTTByMsgidAndOutgoingTT(String msgid, OutgoingTT outgoingTT);
    List<MessageLogForTT> getMessageLogForTTByIncomingTT(IncomingTT incomingTT);
    List<MessageLogForTT> getMessageLogForTTByOutgoingTT(OutgoingTT outgoingTT);
    List<MessageLogForTT> getMessageLogForTTByOutgoingTTAndIssueType(OutgoingTT outgoingTT, MessageIssueType issueType);
    MessageLogForTT addMessageLogForIncomingTT(String msgid, MessageIssueType issueType, IncomingTT incomingTT);
    MessageLogForTT addMessageLogForOutgoingTT(String msgid, MessageIssueType issueType, OutgoingTT outgoingTT);

    @Transactional
    void addOutgoingTT(List<MessageLogForTT> messageLogForTTList, OutgoingTT outgoingTT);

    MessageLogForTT updateMessageLogForTT(Long id, String msgid, MessageIssueType issueType, IncomingTT incomingTT, OutgoingTT outgoingTT);

    @Transactional
    void deleteIncomingTT(Long id_messageLogForTT);

    @Transactional
    void deleteOutgoingTT(Long id_messageLogForTT, OutgoingTT outgoingTT);
}
