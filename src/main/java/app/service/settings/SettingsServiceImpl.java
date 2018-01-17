package app.service.settings;

import app.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Олег on 09.12.2017.
 */
@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    SettingsRepository settingsRepository;

    @Override
    public app.entity.Settings getSettingByKey(String key) {
        return settingsRepository.getSettingByKey(key);
    }

    @Override
    public void save(app.entity.Settings settings) {
        settingsRepository.save(settings);
    }

    @Override
    public String getSiteTitle() {
        String siteHeader = "";
        app.entity.Settings siteHeaderSetting = getSettingByKey("global_site_header");
        if (siteHeaderSetting != null) {
            siteHeader = siteHeaderSetting.getValue();
        }
        return siteHeader;
    }

}
