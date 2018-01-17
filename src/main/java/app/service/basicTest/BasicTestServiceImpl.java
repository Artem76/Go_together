package app.service.basicTest;

import app.entity.BasicTest;
import app.repository.BasicTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 20.12.2017.
 */

@Service
public class BasicTestServiceImpl implements BasicTestService {

    @Autowired
    BasicTestRepository basicTestRepository;

    @Override
    public void save(BasicTest basicTest) {
        basicTestRepository.save(basicTest);
    }

    @Override
    public List<BasicTest> getBasicTestListByBage(Integer page) {
        //return basicTestRepository.getList();
        return basicTestRepository.getListByPages(new PageRequest(page, 50));
    }

}
