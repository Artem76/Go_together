package app.entityXML.reportSmsStatistic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 14.07.2017.
 */
//@XmlRootElement(name = "ReportSmsStatistic")
public class ReportSmsStatistic {
    private List<CustomerRssXml> customersRssXml = new ArrayList<>();

    private List<LastMinuteRowXML> lastMinuteRowXml = new ArrayList<>();

    public ReportSmsStatistic() {
    }

    public ReportSmsStatistic(List<CustomerRssXml> customersRssXml, List<LastMinuteRowXML> lastMinuteRowXml) {
        this.customersRssXml = customersRssXml;
        this.lastMinuteRowXml = lastMinuteRowXml;
    }

    public List<CustomerRssXml> getCustomersRssXml() {
        return customersRssXml;
    }

    public void setCustomersRssXml(List<CustomerRssXml> customersRssXml) {
        this.customersRssXml = customersRssXml;
    }

    public void addCustomerRssXml(CustomerRssXml customerRssXml){
        this.customersRssXml.add(customerRssXml);
    }

    public List<LastMinuteRowXML> getLastMinuteRowXml() {
        return lastMinuteRowXml;
    }

    public void setLastMinuteRowXml(List<LastMinuteRowXML> lastMinuteRowXml) {
        this.lastMinuteRowXml = lastMinuteRowXml;
    }
}
