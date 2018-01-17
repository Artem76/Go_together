package app.repository;

import app.entity.IncomingTT;
import app.entity.MessageLogForTT;
import app.entity.OutgoingTT;
import app.entity.enums.MessageIssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by АРТЕМ on 17.10.2017.
 */
public interface MessageLogForTTRepository extends JpaRepository<MessageLogForTT, Long> {
    List<MessageLogForTT> findByRelatedMsgidAndIncomingTT(String relatedMsgid, IncomingTT incomingTT);

    @Query("SELECT c FROM MessageLogForTT c where c.relatedMsgid = :relatedMsgid AND c.incomingTT = :incomingTT AND c.id <> :id")
    List<MessageLogForTT> findByRelatedMsgidAndIncomingTTExceptId(@Param("relatedMsgid") String relatedMsgid,
                                                            @Param("incomingTT") IncomingTT incomingTT,
                                                            @Param("id") Long id);

//    List<MessageLogForTT> findByRelatedMsgidAndOutgoingTTList(String relatedMsgid, OutgoingTT outgoingTT);

    List<MessageLogForTT> findByIncomingTT(IncomingTT incomingTT);

//    List<MessageLogForTT> findByOutgoingTTList(OutgoingTT outgoingTT);

//    List<MessageLogForTT> findByOutgoingTTListAndIssueType(OutgoingTT outgoingTT, MessageIssueType messageIssueType);
}
