package app.service.softswitchTriggers;

import app.entity.SoftswitchTriggers;

/**
 * Created by Олег on 05.09.2017.
 */
public interface SoftswitchTriggersService {
    void updateSoftswitchTriggers(Long softswitch_id, String key, Boolean value);
    SoftswitchTriggers getTriggersBySoftswitchIdAndKey(Long softswitch_id, String key);
}
