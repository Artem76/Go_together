package app.service.mdr;


import app.entity.*;
import app.entity.enums.MessageIssueType;
import app.entityXML.mdr.TTMdr;
import app.repository.IncomingTTRepository;
import app.repository.MdrRepository;
import app.repository.MessageLogForTTRepository;
import app.repository.OutgoingTTRepository;
import app.service.smppVendorIps.SmppVendorIpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Олег on 09.07.2017.
 */
@Service
public class MdrServiceImpl implements MdrService {
    @Autowired
    MdrRepository mdrRepository;

    @Autowired
    SmppVendorIpsService smppVendorIpsService;

    @Autowired
    IncomingTTRepository incomingTTRepositoryl;

    @Autowired
    OutgoingTTRepository outgoingTTRepository;

    @Autowired
    MessageLogForTTRepository messageLogForTTRepository;

    @Override
    public TTMdr getTTMdrByMsgidAndMessageLogForTT(String msgid, Long messageLogForTT_id) {
        TTMdr currentTTMdr = null;
        Mdr currentMdr = mdrRepository.getMessageByMsgid(msgid);
        MessageLogForTT messageLogForTT = messageLogForTTRepository.findOne(messageLogForTT_id);
        if (currentMdr != null) {
            SmppVendorIps currentIps = smppVendorIpsService.getSmppVendorIpsByCid(currentMdr.getRouted_cid());
            currentTTMdr = new TTMdr(msgid,
                    messageLogForTT.getIssueType(),
                    currentMdr.getDestination_addr(),
                    currentMdr.getVendor_msgid(),
                    currentIps.getSmppVendorAccount().getCustomer().getName() + " - " + currentIps.getSmppVendorAccount().getName() + " - " + currentIps.getSystemId(),
                    currentIps.getSmppVendorAccount().getId(),
                    messageLogForTT_id);
        }
        return currentTTMdr;
    }

    @Override
    public TTMdr getTTMdrByMsgid(String msgid) {
        TTMdr currentTTMdr = null;
        Mdr currentMdr = mdrRepository.getMessageByMsgid(msgid);
        if (currentMdr != null) {
            SmppVendorIps currentIps = smppVendorIpsService.getSmppVendorIpsByCid(currentMdr.getRouted_cid());
            currentTTMdr = new TTMdr(msgid,
                    MessageIssueType.Other,
                    currentMdr.getDestination_addr(),
                    currentMdr.getVendor_msgid(),
                    currentIps.getSmppVendorAccount().getCustomer().getName() + " - " + currentIps.getSmppVendorAccount().getName() + " - " + currentIps.getSystemId(),
                    currentIps.getSmppVendorAccount().getId(),
                    0L);
        }
        return currentTTMdr;
    }

    @Override
    public List<TTMdr> getIncomingTTMdrByTTId(Long tt_id) {
        List<TTMdr> listMdr = new ArrayList<>();
        IncomingTT currentTT = incomingTTRepositoryl.findOne(tt_id);
        if (currentTT != null) {
            List<MessageLogForTT> messageLogForTTList = new ArrayList<>(currentTT.getMessageLogForTTList());
            listMdr = getTTMdrByMsgids(messageLogForTTList);
        }
        return listMdr;
    }

    @Override
    public List<TTMdr> getOutgoingTTMdrByTTId(Long tt_id) {
        List<TTMdr> listMdr = new ArrayList<>();
        OutgoingTT currentTT = outgoingTTRepository.findOne(tt_id);
        if (currentTT != null) {
            List<MessageLogForTT> messageLogForTTList = new ArrayList<>(currentTT.getMessageLogForTTList());
            listMdr = getTTMdrByMsgids(messageLogForTTList);
        }
        return listMdr;
    }

    @Override
    public Mdr getMdrByMsgId(String msgId) {
        return mdrRepository.getMessageByMsgid(msgId);
    }

    @Override
    public void save(Mdr mdr) {
        mdrRepository.save(mdr);
    }

    private List<TTMdr> getTTMdrByMsgids(List<MessageLogForTT> messageLogForTTList) {
        List<TTMdr> ttMdrList = new ArrayList<>();
        for (MessageLogForTT messageLogForTT : messageLogForTTList) {
            TTMdr currentTTMdr = getTTMdrByMsgidAndMessageLogForTT(messageLogForTT.getRelatedMsgid(), messageLogForTT.getId());
            if (currentTTMdr != null) {
                ttMdrList.add(currentTTMdr);
            } else {
                currentTTMdr = new TTMdr(messageLogForTT.getRelatedMsgid(), messageLogForTT.getIssueType(), "Not Found", "Not Found", "Not Found", 0L, messageLogForTT.getId());
                ttMdrList.add(currentTTMdr);
            }
        }
        return ttMdrList;
    }

    @Override
    public void updateQueuedMessage(String submit_status, String vendor_msgid, String msgid) {
        mdrRepository.updateQueuedMessage(submit_status, vendor_msgid, msgid);
    }


}

