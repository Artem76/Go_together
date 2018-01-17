package app.service.indexReportService;

import app.entityXML.IndexReport.IndexManagerReport;

/**
 * Created by Олег on 14.10.2017.
 */
public interface IndexManagerReportService {
    IndexManagerReport getIndexManagerReport(String login);
}
