package app.service.basicTestDetails;

import app.entity.BasicTestDetails;

import java.util.List;

/**
 * Created by Олег on 20.12.2017.
 */
public interface BasicTestDetailService {
    void save(BasicTestDetails basicTestDetails);
    List<BasicTestDetails> getDetailsByTestId(Long testId);
}
