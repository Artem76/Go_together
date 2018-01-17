package app.repository;

import app.entity.VendorDialpeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
public interface VendorDialpeerRepository extends JpaRepository<VendorDialpeer, Long> {

    @Query("SELECT c FROM VendorDialpeer c WHERE c.tag = :tag")
    VendorDialpeer getDpByTag(@Param("tag") Integer tag);
}
