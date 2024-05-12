package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.SupplierDTO;

import java.util.List;

public interface SupplierBO {
    SupplierDTO saveSupplier(SupplierDTO dto);

    void updateSupplier(SupplierDTO dto, String code);

    List<SupplierDTO> getSuppliers();
}
