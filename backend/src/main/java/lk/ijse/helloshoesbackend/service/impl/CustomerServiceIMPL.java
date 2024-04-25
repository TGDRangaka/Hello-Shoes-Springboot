package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceIMPL implements CustomerService {
    private final CustomerRepo customerRepo;

    public List<CustomerEntity> getAll(){
        return customerRepo.findAll();
    }

    public CustomerEntity save(CustomerEntity entity){
        customerRepo.save(entity);
        return entity;
    }
}
