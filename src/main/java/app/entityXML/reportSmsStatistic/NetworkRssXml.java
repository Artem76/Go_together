package app.entityXML.reportSmsStatistic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 14.07.2017.
 */
//@XmlRootElement(name = "NetworkRssXml")
public class NetworkRssXml extends ParentRssXml{
    private List<VendorRssXml> vendorsRssXml = new ArrayList<>();

    public NetworkRssXml() {
    }

    public NetworkRssXml(String id, String name, Long attempts_count, Long attempts_success, Double client_price, Double vendor_price, float dlr, Integer adt, Double incoming_sum, Double outgoing_sum, Double profit, Long delivered, Long undelivered, Long rejected, Long other, Long delivery_time) {
        super(id, name, attempts_count, attempts_success, client_price, vendor_price, dlr, adt, incoming_sum, outgoing_sum, profit, delivered, undelivered, rejected, other, delivery_time);
    }

    public List<VendorRssXml> getVendorsRssXml() {
        return vendorsRssXml;
    }

    public void setVendorsRssXml(List<VendorRssXml> vendorsRssXml) {
        this.vendorsRssXml = vendorsRssXml;
    }

    public void addVendorRssXml(VendorRssXml vendorRssXml){
        this.vendorsRssXml.add(vendorRssXml);
    }
}
