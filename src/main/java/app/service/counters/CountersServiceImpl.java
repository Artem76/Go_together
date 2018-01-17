package app.service.counters;

import app.entity.Counters;
import app.repository.CountersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Олег on 11.12.2017.
 */

@Service
public class CountersServiceImpl implements CountersService {

    @Autowired
    CountersRepository countersRepository;

    @Override
    public Integer getCounterByKey(String key) {
        Counters currentElement = countersRepository.getCounterByKey(key);
        if (currentElement == null) {
            return 0;
        }

        return currentElement.getValue();
    }


    @Override
    public void increaseCounterByKey(String key) {
        Counters currentElement = countersRepository.getCounterByKey(key);
        if (currentElement != null) {
            currentElement.setValue(currentElement.getValue() + 1);
            countersRepository.save(currentElement);
        } else {
            currentElement = new Counters(key, 1);
            countersRepository.save(currentElement);
        }
    }

    @Override
    public void decreaseCounterByKey(String key) {
        Counters currentElement = countersRepository.getCounterByKey(key);
        if (currentElement != null) {
            currentElement.setValue(currentElement.getValue() - 1);
            countersRepository.save(currentElement);
        } else {
            currentElement = new Counters(key, 0);
            countersRepository.save(currentElement);
        }
    }

}
