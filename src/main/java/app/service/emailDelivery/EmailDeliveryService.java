package app.service.emailDelivery;

/**
 * Created by АРТЕМ on 15.09.2017.
 */
public interface EmailDeliveryService {
    void sendAndGetEmail(boolean onlyNew);
}
