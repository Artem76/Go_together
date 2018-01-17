package app.entityXML.BasicTests;

import app.entity.BasicTestDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олег on 21.12.2017.
 */
public class BasicTestReportRow {

    private String vendorName;

    private String countryName;

    private String networkName;

    private List<BasicTestDetails> details = new ArrayList<>();


    public BasicTestReportRow() {

    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public List<BasicTestDetails> getDetails() {
        return details;
    }

    public void setDetails(List<BasicTestDetails> details) {
        this.details = details;
    }

    public void addToDetailsList(BasicTestDetails basicTestDetails) {
        this.details.add(basicTestDetails);
    }
}
