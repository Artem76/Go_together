package app.service.basicTestDetails;

import app.entity.BasicTestDetails;
import app.repository.BasicTestDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 20.12.2017.
 */

@Service
public class BasicTestDetailsServiceImpl implements BasicTestDetailService{

    @Autowired
    BasicTestDetailsRepository basicTestDetailsRepository;

    @Override
    public void save(BasicTestDetails basicTestDetails) {
        basicTestDetailsRepository.save(basicTestDetails);
    }

    @Override
    public List<BasicTestDetails> getDetailsByTestId(Long testId) {
        return basicTestDetailsRepository.getDetailByTestId(testId);
    }

}
