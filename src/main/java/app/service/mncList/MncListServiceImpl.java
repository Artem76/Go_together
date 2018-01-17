package app.service.mncList;

import app.entity.Refbook;
import app.entityXML.mncList.MncXml;
import app.entityXML.mncList.MncXmlList;
import app.repository.RefbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by АРТЕМ on 09.08.2017.
 */
@Service
public class MncListServiceImpl implements MncListService {
    @Autowired
    RefbookRepository refbookRepository;

    @Override
    public MncXmlList getMncXmlListByMcc(String mcc) {
        List<MncXml> mncXmls = new ArrayList<>();
        for (Refbook r: refbookRepository.findByMccAndNotEmptyMnc(mcc)) {
            mncXmls.add(new MncXml(r.getNetwork(), r.getMnc()));
        }
        MncXmlList mncXmlList = new MncXmlList(mncXmls);
        return mncXmlList;
    }
}
