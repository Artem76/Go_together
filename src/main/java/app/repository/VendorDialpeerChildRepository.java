package app.repository;

import app.entity.VendorDialpeer;
import app.entity.VendorDialpeerChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by АРТЕМ on 04.08.2017.
 */
public interface VendorDialpeerChildRepository extends JpaRepository<VendorDialpeerChild, Long> {
    @Query("SELECT c FROM VendorDialpeerChild c where c.vendorDialpeer = :vendorDialpeer")
    List<VendorDialpeerChild> findByVendorDialpeer(@Param("vendorDialpeer") VendorDialpeer vendorDialpeer);
}
