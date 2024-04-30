package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAll();
    CustomerDTO save(CustomerDTO dto);
}
