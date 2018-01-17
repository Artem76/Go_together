package app.service.softswitchTriggers;

import app.entity.SoftswitchTriggers;
import app.repository.SoftswitchTriggersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Олег on 05.09.2017.
 */

@Service
public class SoftswitchTriggersServiceImpl implements SoftswitchTriggersService {

    @Autowired
    SoftswitchTriggersRepository softswitchTriggersRepository;

    @Override
    public void updateSoftswitchTriggers(Long softswitch_id, String key, Boolean value) {
        SoftswitchTriggers currentTrigger = softswitchTriggersRepository.getTriggersBySoftswitchIdAndKey(softswitch_id, key);
        if (currentTrigger == null) {
            currentTrigger = new SoftswitchTriggers(softswitch_id, key, value);
            softswitchTriggersRepository.save(currentTrigger);
        } else {
            currentTrigger.setValue(value);
        }
        softswitchTriggersRepository.save(currentTrigger);
    }

    @Override
    public SoftswitchTriggers getTriggersBySoftswitchIdAndKey(Long softswitch_id, String key) {
        SoftswitchTriggers returnValue;
        returnValue = softswitchTriggersRepository.getTriggersBySoftswitchIdAndKey(softswitch_id, key);
        if (returnValue == null) {
            returnValue = new SoftswitchTriggers();
        }
        return returnValue;
    }
}
