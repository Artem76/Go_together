package app.service.incomingPayment;

import java.util.Date;
import java.util.Map;

/**
 * Created by Олег on 04.12.2017.
 */
public interface IncomingPaymentService {
    Map<String,Object> getPaymentMapSortForPage(Long customerId, String dateStart, String dateEnd, Integer pageIndex, Integer pageSize);

    void savePayment(Boolean confirmed, Long customer_id, Long payment_id, Date date, Double sum);
}
