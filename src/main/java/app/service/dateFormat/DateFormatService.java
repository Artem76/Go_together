package app.service.dateFormat;

import app.entity.DateFormat;

import java.util.List;

/**
 * Created by АРТЕМ on 07.11.2017.
 */
public interface DateFormatService {
    List<DateFormat> getAllDateFormat();

    void addDateFormat(DateFormat dateFormat);

    Long saveDateFormat(Long dateFormat_id, String format);

    void deleteDateFormat(Long dateFormat_id);
}
