package app.entityXML.reportSmsStatistic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 14.07.2017.
 */
//@XmlRootElement(name = "AccountRssXml")
public class AccountRssXml extends ParentRssXml{
    private List<CountryRssXml> countriesRssXml = new ArrayList<>();

    public AccountRssXml() {
    }

    public AccountRssXml(String id, String name, Long attempts_count, Long attempts_success, Double client_price, Double vendor_price, float dlr, Integer adt, Double incoming_sum, Double outgoing_sum, Double profit, Long delivered, Long undelivered, Long rejected, Long other, Long delivery_time) {
        super(id, name, attempts_count, attempts_success, client_price, vendor_price, dlr, adt, incoming_sum, outgoing_sum, profit, delivered, undelivered, rejected, other, delivery_time);
    }

    public List<CountryRssXml> getCountriesRssXml() {
        return countriesRssXml;
    }

    public void setCountriesRssXml(List<CountryRssXml> countriesRssXml) {
        this.countriesRssXml = countriesRssXml;
    }

    public void addCountrieRssXml(CountryRssXml countryRssXml){
        this.countriesRssXml.add(countryRssXml);
    }
}
