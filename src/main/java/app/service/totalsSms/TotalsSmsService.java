package app.service.totalsSms;

import app.entity.TotalsSmsRow;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 09.07.2017.
 */
public interface TotalsSmsService {
    boolean getSavingFlag();
    void setSavingFlag(boolean flag);
    List<TotalsSmsRow> getTotalsByDate(Date date);
    TotalsSmsRow getTotalsByKeyFields(Date date, String mcc, String mnc, String uid, String routed_cid, long softswitch_id, long client_account_id,
                                      long vendor_account_id, long vendor_account_ip_id);

    void save(TotalsSmsRow totalsSmsRow);
    List<TotalsSmsRow> getTotalsByDay(Date date);
    void recalculateTotalsByDates(Date startDate, Date endDate);
    void recalculateTotalsByDatesAndCustomer(Date startDate, Date endDate, Long customerId);
}
