package app.entityXML.mncList;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by АРТЕМ on 09.08.2017.
 */
@XmlRootElement(name = "MncXmlList")
public class MncXmlList {
    List<MncXml> mncXmlList;

    public MncXmlList() {
    }

    public MncXmlList(List<MncXml> mncXmlList) {
        this.mncXmlList = mncXmlList;
    }

    public List<MncXml> getMncXmlList() {
        return mncXmlList;
    }

    public void setMncXmlList(List<MncXml> mncXmlList) {
        this.mncXmlList = mncXmlList;
    }
}
