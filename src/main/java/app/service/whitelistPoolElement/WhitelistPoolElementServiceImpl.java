package app.service.whitelistPoolElement;

import app.entity.WhitelistPoolElement;
import app.repository.WhitelistPoolElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Олег on 21.08.2017.
 */

@Service
public class WhitelistPoolElementServiceImpl implements WhitelistPoolElementService {
    @Autowired
    WhitelistPoolElementRepository whitelistPoolElementRepository;

    @Override
    public Boolean addNumber(String number) {
        if (!whitelistPoolElementRepository.exists(number)) {
            whitelistPoolElementRepository.save(new WhitelistPoolElement(number));
            return true;
        }
        return false;
    }

    @Override
    public Boolean numberExists(String number){
        return whitelistPoolElementRepository.exists(number);
    }

}
