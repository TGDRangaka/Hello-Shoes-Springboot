package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.CustomerBO;
import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerBOIMPL implements CustomerBO {

    private final CustomerService customerService;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Attempting to get all customers");
        List<CustomerDTO> customers = customerService.getAll();
        return customers;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        log.info("Attempting to save customer with email: {}", dto.getEmail());
        dto.setLevel(CustomerLevel.NEW);
        dto.setTotalPoints(0);
        dto.setJoinedDateAsLoyalty(LocalDate.now());
        CustomerDTO savedCustomer = customerService.save(dto);
        return savedCustomer;
    }

    @Override
    public void updateCustomer(CustomerDTO dto, String customerId) {
        log.info("Attempting to update customer with id: {}", customerId);
        customerService.update(dto, customerId);
    }
}
