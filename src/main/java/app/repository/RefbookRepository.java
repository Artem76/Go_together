package app.repository;

import app.entity.Refbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RefbookRepository extends JpaRepository<Refbook, Long> {
    @Query("SELECT c FROM Refbook c ORDER BY country ASC")
    List<Refbook> findAllSortCountry();

    @Query("SELECT c FROM Refbook c where c.mnc = '' ORDER BY country ASC")
    List<Refbook> findAllCountrySort();

    @Query("SELECT c FROM Refbook c where c.mnc <> '' ORDER BY network ASC")
    List<Refbook> findAllNetworkSort();

    @Query("SELECT c FROM Refbook c ORDER BY mcc,mnc ASC")
    List<Refbook> findAllSortMccMnc();

    @Query("SELECT c FROM Refbook c where c.mcc = :mcc AND c.state <> 'dialcode'")
    List<Refbook> findByMcc(@Param("mcc") String mcc);

    @Query("SELECT c FROM Refbook c where c.mcc = :mcc and c.mnc = '' AND c.state = 'country'")
    Refbook findOneByMcc(@Param("mcc") String mcc);

    @Query("SELECT c FROM Refbook c where c.mcc = :mcc and c.mnc <> ''")
    List<Refbook> findByMccAndNotEmptyMnc(@Param("mcc") String mcc);

    @Query("SELECT c FROM Refbook c where c.mcc = :mcc and c.mnc = :mnc and c.state = 'network'")
    List<Refbook> findByMccAndMnc(@Param("mcc") String mcc,
                                  @Param("mnc") String mnc);

    @Query("SELECT c FROM Refbook c where c.mcc = :mcc AND c.id <> :id")
    List<Refbook> findByMccExceptId(@Param("mcc") String mcc,
                                    @Param("id") long id);

    @Query("SELECT c FROM Refbook c where c.mcc = :mcc AND c.mnc = :mnc AND c.id <> :id")
    List<Refbook> findByMccAndMncExceptId(@Param("mcc") String mcc,
                                          @Param("mnc") String mnc,
                                          @Param("id") long id);

    @Query("SELECT c FROM Refbook c WHERE c.mcc = :mcc AND c.mnc <> '' AND c.state = 'network'")
    List<Refbook> findNetworksByMcc(@Param("mcc") String mcc);

    @Query("SELECT c FROM Refbook c WHERE c.mcc = :mcc AND c.mnc <> '' AND c.state = 'network' ORDER BY network ASC")
    List<Refbook> findNetworksByMccSort(@Param("mcc") String mcc);

    @Query("SELECT c FROM Refbook c where c.mcc = :mcc and c.mnc = :mnc AND c.state = 'dialcode'")
    List<Refbook> findAllByMccAndMnc(@Param("mcc") String mcc,
                                     @Param("mnc") String mnc);
}
