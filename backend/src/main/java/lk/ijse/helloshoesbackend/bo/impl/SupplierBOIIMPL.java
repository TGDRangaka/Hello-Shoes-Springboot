package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.SupplierBO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierBOIIMPL implements SupplierBO {
    private final SupplierService supplierService;

    @Override
    public SupplierDTO saveSupplier(SupplierDTO dto) {
        return supplierService.save(dto);
    }
}
