package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.entity.SupplierEntity;
import lk.ijse.helloshoesbackend.repo.SupplierRepo;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceIMPL implements SupplierService {
    private final SupplierRepo supplierRepo;

    @Override
    public SupplierDTO save(SupplierDTO supplier) {
        supplier.setCode(UtilMatter.generateUUID());

        SupplierEntity savedSupplier = supplierRepo.save(Conversion.toSupplierEntity(supplier));
        return Conversion.toSupplierDTO(savedSupplier);
    }

    @Override
    public SupplierDTO findByName(String name) {
        Optional<SupplierEntity> byName = supplierRepo.findByName(name);
        return Conversion.toSupplierDTO(byName.get());
    }
}
