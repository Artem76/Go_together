package app.repository;

import app.entity.OutgoingInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Олег on 20.10.2017.
 */
public interface OutgoingInvoiceRepository extends JpaRepository<OutgoingInvoice, Long>{
    @Query("SELECT c FROM OutgoingInvoice c ORDER BY c.date DESC")
    List<OutgoingInvoice> getInvoicesAllSort();

    List<OutgoingInvoice> findAllByConfirmedAndProcessed(Boolean confirmed, Boolean processed);
}
