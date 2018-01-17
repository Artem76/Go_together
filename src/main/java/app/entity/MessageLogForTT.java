package app.entity;

import app.entity.enums.MessageIssueType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by АРТЕМ on 13.10.2017.
 */
@Entity
@Table(name = "message_log_for_tt")
public class MessageLogForTT {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "related_msgid")
    private String relatedMsgid;

    @Column(name = "issue")
    private MessageIssueType issueType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incomingTT_id")
    private IncomingTT incomingTT;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OutgoingTT> outgoingTTList = new HashSet<>();

    public MessageLogForTT() {
    }

    public MessageLogForTT(String relatedMsgid, MessageIssueType issueType) {
        this.relatedMsgid = relatedMsgid;
        this.issueType = issueType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRelatedMsgid() {
        return relatedMsgid;
    }

    public void setRelatedMsgids(String relatedMsgid) {
        this.relatedMsgid = relatedMsgid;
    }

    public MessageIssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(MessageIssueType issueType) {
        this.issueType = issueType;
    }

    public IncomingTT getIncomingTT() {
        return incomingTT;
    }

    public void setIncomingTT(IncomingTT incomingTT) {
        this.incomingTT = incomingTT;
    }

    public Set<OutgoingTT> getOutgoingTTList() {
        return outgoingTTList;
    }

    public void setOutgoingTTList(Set<OutgoingTT> outgoingTTList) {
        this.outgoingTTList = outgoingTTList;
    }

    public void addOutgoingTT(OutgoingTT outgoingTT){
        this.outgoingTTList.add(outgoingTT);
    }

    public void addOutgoingTTList(List<OutgoingTT> outgoingTTList){
        this.outgoingTTList.addAll(outgoingTTList);
    }

    public void deleteOutgoingTT(OutgoingTT outgoingTT){
        this.outgoingTTList.remove(outgoingTT);
    }
}
