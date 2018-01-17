package app.entityXML.mdr;

import app.entity.enums.MessageIssueType;

/**
 * Created by Олег on 30.08.2017.
 */
public class TTMdr {
    private String msgid;

    private MessageIssueType issue;

    private String destination_addr;

    private String vendor_msgid;

    private  String vendor_name;

    private Long vendor_account_id;

    private Long messageLogForTT_id;

    public TTMdr(String msgid, MessageIssueType issue, String destination_addr, String vendor_msgid, String vendor_name, Long vendor_account_id, Long messageLogForTT_id) {
        this.msgid = msgid;
        this.issue = issue;
        this.destination_addr = destination_addr;
        this.vendor_msgid = vendor_msgid;
        this.vendor_name = vendor_name;
        this.vendor_account_id = vendor_account_id;
        this.messageLogForTT_id = messageLogForTT_id;
    }

    public TTMdr() {

    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getDestination_addr() {
        return destination_addr;
    }

    public void setDestination_addr(String destination_addr) {
        this.destination_addr = destination_addr;
    }

    public String getVendor_msgid() {
        return vendor_msgid;
    }

    public void setVendor_msgid(String vendor_msgid) {
        this.vendor_msgid = vendor_msgid;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public Long getVendor_account_id() {
        return vendor_account_id;
    }

    public void setVendor_account_id(Long vendor_account_id) {
        this.vendor_account_id = vendor_account_id;
    }

    public MessageIssueType getIssue() {
        return issue;
    }

    public void setIssue(MessageIssueType issue) {
        this.issue = issue;
    }

    public Long getMessageLogForTT_id() {
        return messageLogForTT_id;
    }

    public void setMessageLogForTT_id(Long messageLogForTT_id) {
        this.messageLogForTT_id = messageLogForTT_id;
    }
}
