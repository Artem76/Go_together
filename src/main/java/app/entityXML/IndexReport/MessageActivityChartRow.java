package app.entityXML.IndexReport;

/**
 * Created by Олег on 14.10.2017.
 */
public class MessageActivityChartRow {
    private Integer hour;

    private Long count;

    public MessageActivityChartRow(Integer hour, Long count) {
        this.hour = hour;
        this.count = count;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
