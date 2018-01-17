package app.service.ratemodeReports;

import app.entityXML.RateReports.ActualRatesReportRow;
import app.entityXML.RateReports.RatesHistoryReportRow;

import java.util.List;

/**
 * Created by Олег on 02.12.2017.
 */
public interface RatesReportsService {

    List<ActualRatesReportRow> getActualRatesReport(String mode, Long account, String country);

    List<RatesHistoryReportRow> getRatesHistoryReport(String mode, Long account, String country);
}
