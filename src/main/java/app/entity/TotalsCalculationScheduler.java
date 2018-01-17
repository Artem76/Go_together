package app.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Олег on 13.12.2017.
 */

@Entity
@Table(name = "totals_calculation_scheduler")
public class TotalsCalculationScheduler {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "customer_id")
    private Long customer_id;

    public TotalsCalculationScheduler()  {

    }

    public TotalsCalculationScheduler(Date dateStart, Date dateEnd, Long customer_id) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.customer_id = customer_id;
    }

    public TotalsCalculationScheduler(Date dateStart, Date dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }
}
