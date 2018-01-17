package app.entityXML.mncList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by АРТЕМ on 09.08.2017.
 */
@XmlRootElement(name = "MncXml")
public class MncXml {
    private String network;
    private String mnc;

    public MncXml() {
    }

    public MncXml(String network, String mnc) {
        this.network = network;
        this.mnc = mnc;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }
}
