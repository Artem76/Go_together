package app.repository;

import app.entity.Customer;
import app.entity.IncomingPayment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Олег on 04.12.2017.
 */
public interface IncomingPaymentRepository extends JpaRepository<IncomingPayment, Long> {
    @Query("SELECT c FROM IncomingPayment c WHERE c.date between :start and :end ORDER BY c.date desc")
    List<IncomingPayment> findAllByDateBetween(@Param("start") Date start,
                                               @Param("end") Date end,
                                               Pageable pageable);

    @Query("SELECT c FROM IncomingPayment c WHERE c.date between :start and :end and c.customer = :customer ORDER BY c.date desc")
    List<IncomingPayment> findAllByDateBetweenAndCustomer(@Param("start") Date start,
                                                          @Param("end") Date end,
                                                          @Param("customer") Customer customer,
                                                          Pageable pageable);
}
