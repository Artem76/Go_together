package app.service.changesRegistery;

import app.entity.ChangesRegistery;
import app.entity.Softswitch;
import app.repository.ChangesRegisteryRepository;
import app.service.softswitch.SoftswitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 01.08.2017.
 */

@Service
public class ChangesRegisteryServiceImpl implements ChangesRegisteryService {
    @Autowired
    ChangesRegisteryRepository changesRegisteryRepository;

    @Autowired
    SoftswitchService softswitchService;

    @Override
    public ChangesRegistery getChangesRowByKeyFields(Long softswitch_id, String values) {
        ChangesRegistery currentRegistery = changesRegisteryRepository.getChangesRowByKeyFields(softswitch_id, values);
        return currentRegistery;
    }

    @Override
    public List<ChangesRegistery> getChangesBySoftswitch(Long softswitch_id) {
        List<ChangesRegistery> listChangesRegistery = changesRegisteryRepository.getChangesBySoftswitch(softswitch_id);
        return listChangesRegistery;
    }

    public void deleteChangesRegisteryById(Long id) {
        changesRegisteryRepository.delete(id);
    }

    public void registerChangesAllSoftswitches(String object) {
        List<Softswitch> listSoftswitches = softswitchService.getSoftswitchesAll();
        for (Softswitch currentSoftswitch : listSoftswitches) {
            ChangesRegistery currentRegistery = getChangesRowByKeyFields(currentSoftswitch.getId(), object);
            if (currentRegistery==null) {
                ChangesRegistery newChangesRegistery = new ChangesRegistery(currentSoftswitch.getId(), object);
                changesRegisteryRepository.save(newChangesRegistery);
            }

        }

    }


}
