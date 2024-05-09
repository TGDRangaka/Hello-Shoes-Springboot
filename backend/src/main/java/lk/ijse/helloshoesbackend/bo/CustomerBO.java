package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO {
    List<CustomerDTO> getAllCustomers() throws Exception;
    CustomerDTO saveCustomer(CustomerDTO dto) throws Exception;
    void updateCustomer(CustomerDTO dto, String customerId);
}
