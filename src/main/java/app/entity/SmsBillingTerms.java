package app.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "smsbillingterms")
public class SmsBillingTerms {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "billing_days")
    private Integer billingDays;

    @NotNull
    @Column(name = "pay_days")
    private Integer payDays;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "customers")
    private List<Customer> customers;

    public SmsBillingTerms() {
    }

    public SmsBillingTerms(String name, Integer billingDays, Integer payDays) {
        this.name = name;
        this.billingDays = billingDays;
        this.payDays = payDays;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBillingDays() {
        return billingDays;
    }

    public void setBillingDays(Integer billingDays) {
        this.billingDays = billingDays;
    }

    public Integer getPayDays() {
        return payDays;
    }

    public void setPayDays(Integer payDays) {
        this.payDays = payDays;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return name;
    }

    public Date getCurrentBillingTermsStartDate() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        Date returnDate = null;

        if (this.billingDays == 7) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        if (this.billingDays == 15) {

            if (calendar.get(Calendar.DAY_OF_MONTH) >= 15) {
                calendar.set(Calendar.DAY_OF_MONTH, 15);
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            }
        }
        if (this.billingDays == 30) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        returnDate = calendar.getTime();
        return returnDate;
    }

    public Date getCurrentBillingTermsEndDate() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        Date returnDate = null;

        if (this.billingDays == 7) {
            //calendar.set(Calendar.DAY_OF_WEEK, 7);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        if (this.billingDays == 15) {

            if (calendar.get(Calendar.DAY_OF_MONTH) >= 15) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 15);
            }
        }
        if (this.billingDays == 30) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        returnDate = calendar.getTime();
        return returnDate;
    }

    public Date getCurrentBillingTermsPayDate() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        Date returnDate = null;


        if (this.billingDays == 7) {
            //calendar.set(Calendar.DAY_OF_WEEK, 7);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        if (this.billingDays == 15) {

            if (calendar.get(Calendar.DAY_OF_MONTH) >= 15) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 15);
            }
        }
        if (this.billingDays == 30) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        calendar.add(Calendar.DAY_OF_MONTH, payDays);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        returnDate = calendar.getTime();
        return returnDate;
    }

    public Date getBillingTermsStartDateByDate(Date date) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);

        Date returnDate = null;

        if (this.billingDays == 7) {
            //calendar.set(Calendar.DAY_OF_WEEK, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        if (this.billingDays == 15) {

            if (calendar.get(Calendar.DAY_OF_MONTH) >= 15) {
                calendar.set(Calendar.DAY_OF_MONTH, 15);
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            }
        }
        if (this.billingDays == 30) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        returnDate = calendar.getTime();
        return returnDate;
    }

    public Date getBillingTermsEndDateBbyDate(Date date) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);

        Date returnDate = null;

        if (this.billingDays == 7) {
            //calendar.set(Calendar.DAY_OF_WEEK, 7);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        if (this.billingDays == 15) {

            if (calendar.get(Calendar.DAY_OF_MONTH) >= 15) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 15);
            }
        }
        if (this.billingDays == 30) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        returnDate = calendar.getTime();
        return returnDate;
    }
}
