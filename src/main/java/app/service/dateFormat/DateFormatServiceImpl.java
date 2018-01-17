package app.service.dateFormat;

import app.entity.DateFormat;
import app.repository.DateFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by АРТЕМ on 07.11.2017.
 */
@Service
public class DateFormatServiceImpl implements DateFormatService {
    @Autowired
    DateFormatRepository dateFormatRepository;

    @Override
    public List<DateFormat> getAllDateFormat(){
        return dateFormatRepository.findAll();
    }

    @Override
    public void addDateFormat(DateFormat dateFormat){
        dateFormatRepository.save(dateFormat);
    }

    @Override
    @Transactional
    public Long saveDateFormat(Long dateFormat_id, String format){
        DateFormat dateFormat = dateFormatRepository.findOne(dateFormat_id);
        if (dateFormat != null){
            dateFormat.setFormat(format);
        }else {
            dateFormat = new DateFormat(format);
        }
        return dateFormatRepository.saveAndFlush(dateFormat).getId();
    }

    @Override
    public void deleteDateFormat(Long dateFormat_id) {dateFormatRepository.delete(dateFormat_id);}
}
