package app.service.reportSmsStatistic;

import app.entity.CustomUser;
import app.entityXML.reportSmsStatistic.ReportSmsStatistic;


/**
 * Created by АРТЕМ on 14.07.2017.
 */
public interface ReportSmsStatisticService {
    ReportSmsStatistic getReportSmsStatistic(CustomUser user, Long id_customer, String mcc, String date_start, String date_end, String report_option);
}
