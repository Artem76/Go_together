package app.service.mncList;

import app.entityXML.mncList.MncXmlList;

/**
 * Created by АРТЕМ on 09.08.2017.
 */
public interface MncListService {
    MncXmlList getMncXmlListByMcc(String mcc);
}
