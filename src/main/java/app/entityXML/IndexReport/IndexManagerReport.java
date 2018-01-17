package app.entityXML.IndexReport;

import java.util.List;

/**
 * Created by Олег on 14.10.2017.
 */
public class IndexManagerReport {
    public Long messagesCount;

    public float incomingSum;

    public float outgoingSum;

    public float profit;

    private List<MessageActivityChartRow> messageActivityTable;

    private List<ProfitRow> profitTable;

    private List<ConsumptionRow> clientConsumptionTable;

    private List<ConsumptionRow> vendorConsumptionTable;

    public IndexManagerReport(Long messagesCount, float incomingSum, float outgoingSum, float profit, List<MessageActivityChartRow> messageActivityTable, List<ProfitRow> profitTable,
                              List<ConsumptionRow> clientConsumptionTable, List<ConsumptionRow> vendorConsumptionTable) {
        this.messagesCount = messagesCount;
        this.incomingSum = incomingSum;
        this.outgoingSum = outgoingSum;
        this.profit = profit;
        this.messageActivityTable = messageActivityTable;
        this.profitTable = profitTable;
        this.clientConsumptionTable = clientConsumptionTable;
        this.vendorConsumptionTable = vendorConsumptionTable;
    }

    public Long getMessagesCount() {
        return messagesCount;
    }

    public void setMessagesCount(Long messagesCount) {
        this.messagesCount = messagesCount;
    }

    public float getIncomingSum() {
        return incomingSum;
    }

    public void setIncomingSum(float incomingSum) {
        this.incomingSum = incomingSum;
    }

    public float getOutgoingSum() {
        return outgoingSum;
    }

    public void setOutgoingSum(float outgoingSum) {
        this.outgoingSum = outgoingSum;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public List<MessageActivityChartRow> getMessageActivityTable() {
        return messageActivityTable;
    }

    public void setMessageActivityTable(List<MessageActivityChartRow> messageActivityTable) {
        this.messageActivityTable = messageActivityTable;
    }

    public List<ProfitRow> getProfitTable() {
        return profitTable;
    }

    public void setProfitTable(List<ProfitRow> profitTable) {
        this.profitTable = profitTable;
    }

    public List<ConsumptionRow> getClientConsumptionTable() {
        return clientConsumptionTable;
    }

    public void setClientConsumptionTable(List<ConsumptionRow> clientConsumptionTable) {
        this.clientConsumptionTable = clientConsumptionTable;
    }

    public List<ConsumptionRow> getVendorConsumptionTable() {
        return vendorConsumptionTable;
    }

    public void setVendorConsumptionTable(List<ConsumptionRow> vendorConsumptionTable) {
        this.vendorConsumptionTable = vendorConsumptionTable;
    }
}
