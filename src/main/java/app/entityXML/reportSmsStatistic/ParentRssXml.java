package app.entityXML.reportSmsStatistic;

/**
 * Created by АРТЕМ on 31.07.2017.
 */
public class ParentRssXml {
    private String id;
    private String name;
    private Long attempts_count;
    private Long attempts_success;
    private Double client_price;
    private Double vendor_price;
    private float dlr;
    private Integer adt;
    private Double incoming_sum;
    private Double outgoing_sum;
    private Double profit;
    private Long delivered;
    private Long undelivered;
    private Long rejected;
    private Long other;
    private Long delivery_time;

    public ParentRssXml() {
    }

    public ParentRssXml(String id, String name, Long attempts_count, Long attempts_success, Double client_price, Double vendor_price, float dlr, Integer adt, Double incoming_sum, Double outgoing_sum, Double profit, Long delivered, Long undelivered, Long rejected, Long other, Long delivery_time) {
        this.id = id;
        this.name = name;
        this.attempts_count = attempts_count;
        this.attempts_success = attempts_success;
        this.client_price = client_price;
        this.vendor_price = vendor_price;
        this.dlr = dlr;
        this.adt = adt;
        this.incoming_sum = incoming_sum;
        this.outgoing_sum = outgoing_sum;
        this.profit = profit;
        this.delivered = delivered;
        this.undelivered = undelivered;
        this.rejected = rejected;
        this.other = other;
        this.delivery_time = delivery_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAttempts_count() {
        return attempts_count;
    }

    public void setAttempts_count(Long attempts_count) {
        this.attempts_count = attempts_count;
    }

    public Long getAttempts_success() {
        return attempts_success;
    }

    public void setAttempts_success(Long attempts_success) {
        this.attempts_success = attempts_success;
    }

    public Double getClient_price() {
        return client_price;
    }

    public void setClient_price(Double client_price) {
        this.client_price = client_price;
    }

    public Double getVendor_price() {
        return vendor_price;
    }

    public void setVendor_price(Double vendor_price) {
        this.vendor_price = vendor_price;
    }

    public float getDlr() {
        return dlr;
    }

    public void setDlr(float dlr) {
        this.dlr = dlr;
    }

    public Integer getAdt() {
        return adt;
    }

    public void setAdt(Integer adt) {
        this.adt = adt;
    }

    public Double getIncoming_sum() {
        return incoming_sum;
    }

    public void setIncoming_sum(Double incoming_sum) {
        this.incoming_sum = incoming_sum;
    }

    public Double getOutgoing_sum() {
        return outgoing_sum;
    }

    public void setOutgoing_sum(Double outgoing_sum) {
        this.outgoing_sum = outgoing_sum;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Long getDelivered() {
        return delivered;
    }

    public void setDelivered(Long delivered) {
        this.delivered = delivered;
    }

    public Long getUndelivered() {
        return undelivered;
    }

    public void setUndelivered(Long undelivered) {
        this.undelivered = undelivered;
    }

    public Long getRejected() {
        return rejected;
    }

    public void setRejected(Long rejected) {
        this.rejected = rejected;
    }

    public Long getOther() {
        return other;
    }

    public void setOther(Long other) {
        this.other = other;
    }

    public Long getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Long delivery_time) {
        this.delivery_time = delivery_time;
    }
}
