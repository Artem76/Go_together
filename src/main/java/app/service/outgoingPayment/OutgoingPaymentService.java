package app.service.outgoingPayment;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * Created by Олег on 04.12.2017.
 */
public interface OutgoingPaymentService {
    Map<String,Object> getPaymentMapSortForPage(Long customerId, String dateStart, String dateEnd, Integer pageIndex, Integer pageSize);

    @Transactional
    void savePayment(Long customer_id, Long payment_id, Date date, Double sum);
}
