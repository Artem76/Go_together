package app.service.changesRegistery;

import app.entity.ChangesRegistery;

import java.util.List;

/**
 * Created by Олег on 01.08.2017.
 */
public interface ChangesRegisteryService {
    void registerChangesAllSoftswitches(String object);
    void deleteChangesRegisteryById(Long id);
    ChangesRegistery getChangesRowByKeyFields(Long softswitch_id, String values);
    List<ChangesRegistery> getChangesBySoftswitch(Long softswitch_id);
}
