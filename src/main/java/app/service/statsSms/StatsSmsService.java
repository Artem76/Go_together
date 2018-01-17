package app.service.statsSms;

import app.entity.StatsSmsRow;

import java.util.Date;

/**
 * Created by Олег on 10.07.2017.
 */
public interface StatsSmsService {

    public StatsSmsRow getStatsByKeyFields(Date date, String mcc, String mnc, String uid, String routed_cid, String status, long softswitch_id);
    void save(StatsSmsRow statsSmsRow);
}
