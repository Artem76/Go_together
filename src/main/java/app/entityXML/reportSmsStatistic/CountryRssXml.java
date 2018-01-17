package app.entityXML.reportSmsStatistic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 14.07.2017.
 */
//@XmlRootElement(name = "CountryRssXml")
public class CountryRssXml extends ParentRssXml{
    private List<NetworkRssXml> networksRssXml = new ArrayList<>();

    public CountryRssXml() {
    }

    public CountryRssXml(String id, String name, Long attempts_count, Long attempts_success, Double client_price, Double vendor_price, float dlr, Integer adt, Double incoming_sum, Double outgoing_sum, Double profit, Long delivered, Long undelivered, Long rejected, Long other, Long delivery_time) {
        super(id, name, attempts_count, attempts_success, client_price, vendor_price, dlr, adt, incoming_sum, outgoing_sum, profit, delivered, undelivered, rejected, other, delivery_time);
    }

    public List<NetworkRssXml> getNetworksRssXml() {
        return networksRssXml;
    }

    public void setNetworksRssXml(List<NetworkRssXml> networksRssXml) {
        this.networksRssXml = networksRssXml;
    }

    public void addNetworkRssXml(NetworkRssXml networkRssXml){
        this.networksRssXml.add(networkRssXml);
    }
}
