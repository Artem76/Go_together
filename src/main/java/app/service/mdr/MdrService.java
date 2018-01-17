package app.service.mdr;

import app.entity.Mdr;
import app.entityXML.mdr.TTMdr;

import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 09.07.2017.
 */
public interface MdrService {
    TTMdr getTTMdrByMsgidAndMessageLogForTT(String msgid, Long messageLogForTT_id);

    TTMdr getTTMdrByMsgid(String msgid);

    List<TTMdr> getIncomingTTMdrByTTId(Long tt_id);
    List<TTMdr> getOutgoingTTMdrByTTId(Long tt_id);
    Mdr getMdrByMsgId(String msgId);
    void save(Mdr mdr);
    void updateQueuedMessage(String submit_status, String vendor_msgid, String msgid);
}
