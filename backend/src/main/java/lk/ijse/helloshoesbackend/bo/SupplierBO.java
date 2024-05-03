package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.SupplierDTO;

import java.util.List;

public interface SupplierBO {
    SupplierDTO saveSupplier(SupplierDTO dto);
    List<SupplierDTO> getSuppliers();
}
