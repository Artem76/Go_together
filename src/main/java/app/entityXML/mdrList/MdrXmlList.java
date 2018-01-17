package app.entityXML.mdrList;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by АРТЕМ on 09.08.2017.
 */
@XmlRootElement(name = "MdrXmlList")
public class MdrXmlList {
    List<MdrXml> mdrXmlList;
    Long first_number;
    Long last_number;
    Long min_id;
    Long max_id;

    public MdrXmlList() {
    }

    public MdrXmlList(List<MdrXml> mdrXmlList, Long first_number, Long last_number, Long min_id, Long max_id) {
        this.mdrXmlList = mdrXmlList;
        this.first_number = first_number;
        this.last_number = last_number;
        this.min_id = min_id;
        this.max_id = max_id;
    }

    public void setMdrXmlList(List<MdrXml> mdrXmlList) {
        this.mdrXmlList = mdrXmlList;
    }

    public List<MdrXml> getMdrXmlList() {
        return mdrXmlList;
    }

    public Long getFirst_number() {
        return first_number;
    }

    public void setFirst_number(Long first_number) {
        this.first_number = first_number;
    }

    public Long getLast_number() {
        return last_number;
    }

    public void setLast_number(Long last_number) {
        this.last_number = last_number;
    }

    public Long getMin_id() {
        return min_id;
    }

    public void setMin_id(Long min_id) {
        this.min_id = min_id;
    }

    public Long getMax_id() {
        return max_id;
    }

    public void setMax_id(Long max_id) {
        this.max_id = max_id;
    }
}
