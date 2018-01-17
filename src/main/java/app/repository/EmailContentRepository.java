package app.repository;

import app.entity.EmailAccount;
import app.entity.EmailContent;
import app.entity.IncomingTT;
import app.entity.OutgoingTT;
import app.entity.enums.EmailType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by АРТЕМ on 11.09.2017.
 */
public interface EmailContentRepository extends JpaRepository<EmailContent, Long> {
    @Query("SELECT c FROM EmailContent c where c.emailType = :emailType")
    List<EmailContent> findByEmailType(@Param("emailType") EmailType emailType);

    @Query("SELECT c FROM EmailContent c where c.emailAccount = :emailAccount and c.emailType = :emailType")
    List<EmailContent> findByEmailAccountAndEmailType(@Param("emailAccount") EmailAccount emailAccount,
                                                      @Param("emailType") EmailType emailType);

    @Query("SELECT c FROM EmailContent c where c.incomingTT = :incomingTT and c.emailType = :emailType")
    List<EmailContent> findByIncomingTTAndEmailType(@Param("incomingTT") IncomingTT incomingTT,
                                                    @Param("emailType") EmailType emailType);

    @Query("SELECT c FROM EmailContent c where c.outgoingTT = :outgoingTT and c.emailType = :emailType")
    List<EmailContent> findByOutgoingTTAndEmailType(@Param("outgoingTT") OutgoingTT outgoingTT,
                                                    @Param("emailType") EmailType emailType);

    @Query("SELECT c FROM EmailContent c where c.emailAccount = :emailAccount and c.emailType = :emailType and c.processed = :processed")
    List<EmailContent> findByEmailAccountAndEmailTypeAndProcessed(@Param("emailAccount") EmailAccount emailAccount,
                                                                  @Param("emailType") EmailType emailType,
                                                                  @Param("processed") Boolean processed);

    @Query("SELECT c FROM EmailContent c where c.date = :date and c.emailType = :emailType and c.from = :from and c.to = :to and c.subject = :subject")
    List<EmailContent> findByReceivedDateAndEmailTypeAndFromAndToAndSubject(@Param("date") Date date,
                                                                            @Param("emailType") EmailType emailType,
                                                                            @Param("from") String from,
                                                                            @Param("to") String to,
                                                                            @Param("subject") String subject);

    @Query("SELECT c FROM EmailContent c where c.emailAccount = :emailAccount order by c.date desc")
    List<EmailContent> findByEmailAccountForPage(@Param("emailAccount") EmailAccount emailAccount,
                                                 Pageable pageable);
}
