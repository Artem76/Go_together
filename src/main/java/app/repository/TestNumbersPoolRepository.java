package app.repository;

import app.entity.TestNumbersPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Олег on 10.12.2017.
 */
public interface TestNumbersPoolRepository extends JpaRepository<TestNumbersPool, Long> {

    @Query("SELECT c FROM TestNumbersPool c WHERE c.mcc = :mcc AND c.mnc = :mnc")
    List<TestNumbersPool> getNumbersByMccAndMnc(@Param("mcc") String mcc,
                                                @Param("mnc") String mnc);

    @Query("SELECT c FROM TestNumbersPool c WHERE c.mcc = :mcc AND c.mnc = :mnc ORDER BY c.number asc")
    List<TestNumbersPool> getNumbersByMccAndMncSort(@Param("mcc") String mcc,
                                                @Param("mnc") String mnc);

    @Query("SELECT c FROM TestNumbersPool c WHERE c.mcc = :mcc ORDER BY c.number asc")
    List<TestNumbersPool> getNumbersByMccSort(@Param("mcc") String mcc);

    TestNumbersPool findFirstByNumberAndIdNot(String number, Long id);
}
