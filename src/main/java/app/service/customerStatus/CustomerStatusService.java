package app.service.customerStatus;

import app.entity.CustomerStatus;

import java.util.List;

public interface CustomerStatusService {
    CustomerStatus getCustomerStatusById(Long id);
    CustomerStatus getCustomerStatusByName(String name);
    List<CustomerStatus> getCustomerStatusesAllSort();
    List<CustomerStatus> getCustomerStatusesByInvisibleSort(Boolean invisible);
    Boolean addCustomerStatus(String name, Boolean invisible);
    Boolean updateCustomerStatus(Long id, String name, Boolean invisible);
}
