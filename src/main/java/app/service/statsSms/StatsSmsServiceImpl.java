package app.service.statsSms;

import app.entity.StatsSmsRow;
import app.repository.StatsSmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Олег on 10.07.2017.
 */
@Service
public class StatsSmsServiceImpl implements StatsSmsService{
    @Autowired
    StatsSmsRepository statsSmsRepository;

    public StatsSmsRow getStatsByKeyFields(Date date, String mcc, String mnc, String uid, String routed_cid, String status, long softswitch_id) {
        return statsSmsRepository.getStatsByKeyFields(date, mcc, mnc, uid, routed_cid, status, softswitch_id);
    }

    @Override
    public void save(StatsSmsRow statsSmsRow) {
        statsSmsRepository.save(statsSmsRow);
    }
}
