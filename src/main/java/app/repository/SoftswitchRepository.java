package app.repository;

import app.entity.Softswitch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Олег on 06.07.2017.
 */
public interface SoftswitchRepository extends JpaRepository<Softswitch, Long> {
}
