package app.entityXML.routingSmsRuleXmlList;

import app.entity.RoutingSmsRule;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by cmua7 on 23.08.2017.
 */
@XmlRootElement(name = "RoutingSmsRuleXmlList")
public class RoutingSmsRuleXmlList {
    private List<RoutingSmsRule> routingSmsRuleList;

    public RoutingSmsRuleXmlList() {
    }

    public RoutingSmsRuleXmlList(List<RoutingSmsRule> routingSmsRuleList) {
        this.routingSmsRuleList = routingSmsRuleList;
    }

    public List<RoutingSmsRule> getRoutingSmsRuleList() {
        return routingSmsRuleList;
    }

    public void setRoutingSmsRuleList(List<RoutingSmsRule> routingSmsRuleList) {
        this.routingSmsRuleList = routingSmsRuleList;
    }
}
