package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.CustomerBO;
import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerBOIMPL implements CustomerBO {
    private final CustomerService customerService;

    @Override
    public List<CustomerDTO> getAllCustomers() throws Exception {
        return customerService.getAll();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO dto) throws Exception {
        dto.setLevel(CustomerLevel.NEW);
        dto.setTotalPoints(0);
        return customerService.save(dto);
    }
}
