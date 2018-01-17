package app.repository;

import app.entity.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Олег on 20.10.2017.
 */
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {
}
