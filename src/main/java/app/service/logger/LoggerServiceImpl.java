package app.service.logger;

import app.controller.MainController;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Олег on 19.12.2017.
 */

@Service
public class LoggerServiceImpl implements LoggerService{

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(MainController.class);

    @Override
    public void writeInfo(String text) {
        logger.info(text);
    }

}
