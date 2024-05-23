package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO saveCustomer(CustomerDTO dto);
    void updateCustomer(CustomerDTO dto, String customerId);
}
