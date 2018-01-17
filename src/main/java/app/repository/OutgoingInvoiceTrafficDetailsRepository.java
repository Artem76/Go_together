package app.repository;

import app.entity.OutgoingInvoiceTrafficDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Олег on 07.11.2017.
 */
public interface OutgoingInvoiceTrafficDetailsRepository extends JpaRepository<OutgoingInvoiceTrafficDetails, Long>{
    @Query("SELECT c FROM OutgoingInvoiceTrafficDetails c WHERE c.invoice_id = :invoice_id")
    List<OutgoingInvoiceTrafficDetails> getDetailByInvoiceId(@Param("invoice_id") Long invoice_id);
}
