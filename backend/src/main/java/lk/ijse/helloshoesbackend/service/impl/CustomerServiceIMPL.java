package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceIMPL implements CustomerService {
    private final CustomerRepo customerRepo;

    @Override
    public List<CustomerDTO> getAll() {
        return Conversion.toCustomerDTOList(customerRepo.findAll());
    }

    @Override
    public CustomerDTO save(CustomerDTO dto) {
        dto.setCustomerCode(UtilMatter.generateUUID());
        CustomerEntity save = customerRepo.save(Conversion.toCustomerEntity(dto));
        return Conversion.toCustomerDTO(save);
    }

    @Override
    public void update(CustomerDTO dto, String customerId) {
        if(customerRepo.existsById(customerId)){
            CustomerEntity customerEntity = Conversion.toCustomerEntity(dto);
            customerEntity.setCustomerCode(customerId);
            customerRepo.save(customerEntity);
            return;
        }
        throw new NotFoundException("Not Found Customer : " + customerId);
    }

    @Override
    public CustomerDTO findByNameAndEmail(String name, String email) {
        Optional<CustomerEntity> customer = customerRepo.findByNameAndEmail(name, email);
        if (customer.isPresent()) {
            return Conversion.toCustomerDTO(customer.get());
        }
        return null;
    }
}
