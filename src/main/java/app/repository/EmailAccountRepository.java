package app.repository;

import app.entity.EmailAccount;
import app.entity.enums.EmailRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by АРТЕМ on 11.09.2017.
 */
public interface EmailAccountRepository extends JpaRepository<EmailAccount, Long> {
    @Query("SELECT c FROM EmailAccount c where c.invisible = :invisible")
    List<EmailAccount> findByInvisible(@Param("invisible") Boolean invisible);

    @Query("SELECT c FROM EmailAccount c where c.email = :email and c.invisible = false")
    EmailAccount findByEmail(@Param("email") String email);

    @Query("SELECT c FROM EmailAccount c where c.email = :email AND c.id <> :id and c.invisible = false")
    List<EmailAccount> findByEmailExceptId(@Param("email") String email,
                                           @Param("id") long id);

    @Query("SELECT c FROM EmailAccount c where c.userName = :userName and c.invisible = false")
    EmailAccount findByUserName(@Param("userName") String userName);

    @Query("SELECT c FROM EmailAccount c where c.userName = :userName AND c.id <> :id and c.invisible = false")
    List<EmailAccount> findByUserNameExceptId(@Param("userName") String userName,
                                              @Param("id") long id);

    @Query("SELECT c FROM EmailAccount c where c.emailRole = :emailRole AND c.id <> :id and c.invisible = false")
    List<EmailAccount> findByEmailRoleExceptId(@Param("emailRole") EmailRole emailRole,
                                              @Param("id") long id);

    @Query("SELECT c FROM EmailAccount c where c.emailRole = :emailRole and c.invisible = false")
    List<EmailAccount> findByEmailRole(@Param("emailRole") EmailRole emailRole);
}
