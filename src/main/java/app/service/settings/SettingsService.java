package app.service.settings;

import app.entity.Settings;

/**
 * Created by Олег on 09.12.2017.
 */
public interface SettingsService {
    Settings getSettingByKey(String key);
    void save(Settings settings);
    String getSiteTitle();
}
