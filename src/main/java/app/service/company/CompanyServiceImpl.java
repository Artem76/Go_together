package app.service.company;

import app.entity.Company;
import app.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Олег on 20.10.2017.
 */

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public void save(Company company){
        companyRepository.save(company);
    }

    @Override
    public List<Company> getCompanyList() {
        return companyRepository.getAllCompanies();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findOne(id);
    }

}
