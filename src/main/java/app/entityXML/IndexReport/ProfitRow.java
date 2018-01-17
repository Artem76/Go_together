package app.entityXML.IndexReport;

/**
 * Created by Олег on 14.10.2017.
 */
public class ProfitRow {
    private String clientAccount;

    private Long messagesCount;

    private float profit;

    private String direction;

    public ProfitRow(String clientAccount, Long messagesCount, float profit, String direction) {
        this.clientAccount = clientAccount;
        this.messagesCount = messagesCount;
        this.profit = profit;
        this.direction = direction;
    }

    public String getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }

    public Long getMessagesCount() {
        return messagesCount;
    }

    public void setMessagesCount(Long messagesCount) {
        this.messagesCount = messagesCount;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
