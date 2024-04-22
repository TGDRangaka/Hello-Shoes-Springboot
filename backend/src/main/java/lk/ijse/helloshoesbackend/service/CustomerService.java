package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> getAll();
    CustomerEntity save(CustomerEntity entity);
}
