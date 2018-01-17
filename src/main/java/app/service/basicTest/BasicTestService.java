package app.service.basicTest;

import app.entity.BasicTest;

import java.util.List;

/**
 * Created by Олег on 20.12.2017.
 */
public interface BasicTestService {

    void save(BasicTest basicTest);
    List<BasicTest> getBasicTestListByBage(Integer page);

}
