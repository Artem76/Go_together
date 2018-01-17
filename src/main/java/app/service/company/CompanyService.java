package app.service.company;

import app.entity.Company;

import java.util.List;

/**
 * Created by Олег on 20.10.2017.
 */
public interface CompanyService {
    void save(Company company);
    List<Company> getCompanyList();
    Company getCompanyById(Long id);

}
