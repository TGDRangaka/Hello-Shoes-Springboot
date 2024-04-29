package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.SupplierDTO;

public interface SupplierService {
    SupplierDTO save(SupplierDTO supplier);
    SupplierDTO findByName(String name);
}
