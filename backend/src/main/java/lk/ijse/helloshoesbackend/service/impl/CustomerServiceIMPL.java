package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceIMPL implements CustomerService {
    private final CustomerRepo customerRepo;

    @Override
    public List<CustomerDTO> getAll() {
        log.info("Fetching all customers");
        List<CustomerDTO> customers = Conversion.toCustomerDTOList(customerRepo.findAll());
        log.debug("Fetched {} customers", customers.size());
        return customers;
    }

    @Override
    public CustomerDTO save(CustomerDTO dto) {
        log.info("Saving new customer with email: {}", dto.getEmail());
        dto.setCustomerCode(UtilMatter.generateUUID());
        CustomerEntity savedEntity = customerRepo.save(Conversion.toCustomerEntity(dto));
        CustomerDTO savedCustomer = Conversion.toCustomerDTO(savedEntity);
        log.debug("Saved customer: {}", savedCustomer.getCustomerCode());
        return savedCustomer;
    }

    @Override
    public void update(CustomerDTO dto, String customerId) {
        log.info("Updating customer with ID: {}", customerId);
        if (customerRepo.existsById(customerId)) {
            CustomerEntity customerEntity = Conversion.toCustomerEntity(dto);
            customerEntity.setCustomerCode(customerId);
            customerRepo.save(customerEntity);
            log.debug("Updated customer with ID: {}", customerId);
            return;
        }
        log.warn("Customer not found with ID: {}", customerId);
        throw new NotFoundException("Not Found Customer : " + customerId);
    }

    @Override
    public CustomerDTO findByNameAndEmail(String name, String email) {
        log.info("Finding customer with name: {} and email: {}", name, email);
        Optional<CustomerEntity> customer = customerRepo.findByNameAndEmail(name, email);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = Conversion.toCustomerDTO(customer.get());
            log.debug("Found customer: {}", customerDTO.getCustomerCode());
            return customerDTO;
        }
        log.warn("Customer not found with name: {} and email: {}", name, email);
        return null;
    }
}
