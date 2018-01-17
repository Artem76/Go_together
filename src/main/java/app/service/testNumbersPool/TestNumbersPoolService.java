package app.service.testNumbersPool;

import app.entity.TestNumbersPool;

import java.util.List;
import java.util.Map;

/**
 * Created by Олег on 10.12.2017.
 */
public interface TestNumbersPoolService {

    List<TestNumbersPool> getNumbersByMccAndMnc(String mcc, String mnc);
    void save(Long id, String mcc, String mnc, String number, Boolean invalid) throws Exception;
    void delete(TestNumbersPool testNumbersPool);

    void delete(Long id) throws Exception;

    boolean hasNumber(String number, Long id);

    List<Map<String, String>> getTestNumberMapSort(String mcc_mnc);
}
