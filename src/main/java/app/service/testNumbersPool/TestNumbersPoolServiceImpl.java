package app.service.testNumbersPool;

import app.entity.TestNumbersPool;
import app.repository.TestNumbersPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 10.12.2017.
 */
@Service
public class TestNumbersPoolServiceImpl implements TestNumbersPoolService {

    @Autowired
    private TestNumbersPoolRepository testNumbersPoolRepository;

    @Override
    public List<TestNumbersPool> getNumbersByMccAndMnc(String mcc, String mnc) {
        return testNumbersPoolRepository.getNumbersByMccAndMnc(mcc, mnc);
    }

    @Override
    public void save(Long id, String mcc, String mnc, String number, Boolean invalid) throws Exception{
        TestNumbersPool testNumber = null;
        if (id == null || id == 0){
            testNumber = new TestNumbersPool(number, mcc, mnc, invalid);
        }else {
            testNumber = testNumbersPoolRepository.findOne(id);
            testNumber.setInvalid(invalid);
            testNumber.setMcc(mcc);
            testNumber.setMnc(mnc);
            testNumber.setNumber(number);
        }
        testNumbersPoolRepository.save(testNumber);
    }

    @Override
    public void delete(TestNumbersPool testNumbersPool) {
        testNumbersPoolRepository.delete(testNumbersPool);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception{
        testNumbersPoolRepository.delete(id);
    }

    @Override
    public boolean hasNumber(String number, Long id) {
        return !(testNumbersPoolRepository.findFirstByNumberAndIdNot(number, id) == null);
    }

    @Override
    public List<Map<String, String>> getTestNumberMapSort(String mcc_mnc) {
        List<Map<String, String>> result = new ArrayList<>();
        String mcc = null;
        String mnc = null;
        List<TestNumbersPool> testNumbersPool;
        if (mcc_mnc != null && !mcc_mnc.equals("") && !mcc_mnc.equals("0")) {
            mcc = mcc_mnc.split("_")[0];
            mnc = mcc_mnc.split("_").length > 1 ? mcc_mnc.split("_")[1] : "";
            if (!mnc.equals("")){
                testNumbersPool = testNumbersPoolRepository.getNumbersByMccAndMncSort(mcc, mnc);
            }else {
                testNumbersPool = testNumbersPoolRepository.getNumbersByMccSort(mcc);
            }
        }else {
            testNumbersPool = testNumbersPoolRepository.findAll(new Sort(Sort.Direction.ASC, "number"));
        }
        for (TestNumbersPool testNumber: testNumbersPool) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(testNumber.getId()));
            map.put("number", testNumber.getNumber());
            map.put("mcc", testNumber.getMcc());
            map.put("mnc", testNumber.getMnc());
            map.put("invalid", String.valueOf(testNumber.getInvalid()));
            result.add(map);
        }
        return result;
    }
}
