package app.service.counters;

/**
 * Created by Олег on 11.12.2017.
 */
public interface CountersService {

    Integer getCounterByKey(String key);
    void increaseCounterByKey(String key);
    void decreaseCounterByKey(String key);

}
