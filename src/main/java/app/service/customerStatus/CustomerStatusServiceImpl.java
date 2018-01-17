package app.service.customerStatus;

import app.entity.CustomerStatus;
import app.repository.CustomerStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerStatusServiceImpl implements CustomerStatusService {
    @Autowired
    CustomerStatusRepository customerStatusRepository;

    @Override
    public CustomerStatus getCustomerStatusById(Long id) {
        return customerStatusRepository.findOne(id);
    }

    @Override
    public CustomerStatus getCustomerStatusByName(String name) {
        return customerStatusRepository.findByName(name);
    }

    @Override
    public List<CustomerStatus> getCustomerStatusesAllSort() {
        return customerStatusRepository.findAllSort();
    }

    @Override
    public List<CustomerStatus> getCustomerStatusesByInvisibleSort(Boolean invisible) {
        return customerStatusRepository.findByInvisibleSort(invisible);
    }

    @Override
    @Transactional
    public Boolean addCustomerStatus(String name, Boolean invisible) {
        if (name.equals("") || customerStatusRepository.findByName(name) != null) {
            return false;
        }
        customerStatusRepository.save(new CustomerStatus(name, invisible));
        return true;
    }

    @Override
    public Boolean updateCustomerStatus(Long id, String name, Boolean invisible) {
        if (!name.equals("") && customerStatusRepository.findByNameExceptId(name, id).size() == 0){
            CustomerStatus customerStatus = customerStatusRepository.findOne(id);
            customerStatus.setName(name);
            customerStatus.setInvisible(invisible);
            customerStatusRepository.save(customerStatus);
            return true;
        }
        return false;
    }
}
