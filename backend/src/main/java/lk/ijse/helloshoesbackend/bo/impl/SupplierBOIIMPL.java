package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.SupplierBO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SupplierBOIIMPL implements SupplierBO {
    private final SupplierService supplierService;

    @Override
    public SupplierDTO saveSupplier(SupplierDTO dto) {
        return supplierService.save(dto);
    }

    @Override
    public void updateSupplier(SupplierDTO dto, String code){
        supplierService.update(dto, code);
    }

    @Override
    public List<SupplierDTO> getSuppliers() {
        return supplierService.getSuppliers();
    }
}
