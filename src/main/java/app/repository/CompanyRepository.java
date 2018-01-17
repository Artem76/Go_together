package app.repository;

import app.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Олег on 20.10.2017.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c")
    List<Company> getAllCompanies();

}
