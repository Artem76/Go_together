package app.repository;

import app.entity.IncomingInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Олег on 08.11.2017.
 */
public interface IncomingInvoiceRepository extends JpaRepository<IncomingInvoice, Long> {
    @Query("SELECT c FROM IncomingInvoice c ORDER BY c.date DESC")
    List<IncomingInvoice> getInvoicesAllSort();
}
