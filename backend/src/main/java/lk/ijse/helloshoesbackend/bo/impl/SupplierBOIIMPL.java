package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.SupplierBO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SupplierBOIIMPL implements SupplierBO {
    private final SupplierService supplierService;

    @Override
    public SupplierDTO saveSupplier(SupplierDTO dto) {
        log.info("Attempting to save supplier name {}", dto.getName());
        return supplierService.save(dto);
    }

    @Override
    public void updateSupplier(SupplierDTO dto, String code){
        log.info("Attempting to update supplier by id {}", code);
        supplierService.update(dto, code);
    }

    @Override
    public List<SupplierDTO> getSuppliers() {
        log.info("Attempting to get all suppliers");
        return supplierService.getSuppliers();
    }
}
