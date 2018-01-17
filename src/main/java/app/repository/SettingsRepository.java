package app.repository;

import app.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Олег on 09.12.2017.
 */
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    @Query("SELECT c FROM Settings c WHERE c.key = :key")
    Settings getSettingByKey(@Param("key") String key);
}
