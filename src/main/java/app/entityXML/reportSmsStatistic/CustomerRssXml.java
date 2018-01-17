package app.entityXML.reportSmsStatistic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 14.07.2017.
 */
//@XmlRootElement(name = "CustomerRssXml")
public class CustomerRssXml extends ParentRssXml{
    private List<AccountRssXml> accountsRssXml = new ArrayList<>();

    public CustomerRssXml() {

    }

    public CustomerRssXml(String id, String name, Long attempts_count, Long attempts_success, Double client_price, Double vendor_price, float dlr, Integer adt, Double incoming_sum, Double outgoing_sum, Double profit, Long delivered, Long undelivered, Long rejected, Long other, Long delivery_time) {
        super(id, name, attempts_count, attempts_success, client_price, vendor_price, dlr, adt, incoming_sum, outgoing_sum, profit, delivered, undelivered, rejected, other, delivery_time);
    }


    public List<AccountRssXml> getAccountsRssXml() {
        return accountsRssXml;
    }

    public void setAccountsRssXml(List<AccountRssXml> accountsRssXml) {
        this.accountsRssXml = accountsRssXml;
    }

    public void addAccountRssXml(AccountRssXml accountRssXml){
        accountsRssXml.add(accountRssXml);
    }
}
