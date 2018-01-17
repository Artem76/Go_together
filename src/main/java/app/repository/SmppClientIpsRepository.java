package app.repository;

import app.entity.SmppClientIps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmppClientIpsRepository extends JpaRepository<SmppClientIps, Long>{
    @Query("SELECT c FROM SmppClientIps c ORDER BY ip ASC")
    List<SmppClientIps> findAllSort();
}
