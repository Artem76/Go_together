package app.repository;

import app.entity.IncomingInvoiceTrafficDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Олег on 08.11.2017.
 */
public interface IncomingInvoiceTrafficDetailsRepository extends JpaRepository<IncomingInvoiceTrafficDetails, Long> {

    @Query("SELECT c FROM IncomingInvoiceTrafficDetails c WHERE c.invoice_id = :invoice_id")
    List<IncomingInvoiceTrafficDetails> getDetailByInvoiceId(@Param("invoice_id") Long invoice_id);
}
