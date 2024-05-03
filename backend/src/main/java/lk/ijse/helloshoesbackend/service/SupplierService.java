package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    SupplierDTO save(SupplierDTO supplier);
    SupplierDTO findByName(String name);
    List<SupplierDTO> getSuppliers();
}
