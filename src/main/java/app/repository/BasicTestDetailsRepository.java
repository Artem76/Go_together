package app.repository;

import app.entity.BasicTestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Олег on 20.12.2017.
 */
public interface BasicTestDetailsRepository extends JpaRepository<BasicTestDetails, Long> {

    @Query("SELECT c FROM BasicTestDetails c WHERE c.testId = :testId ORDER BY c.vendorAccount, c.mcc, c.mnc")
    List<BasicTestDetails> getDetailByTestId(@Param("testId") Long testId);

}
