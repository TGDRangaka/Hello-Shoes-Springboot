package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.entity.SupplierEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.SupplierRepo;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void update(SupplierDTO supplierDTO, String code){
        Optional<SupplierEntity> byId = supplierRepo.findById(code);
        if(byId.isPresent()){
            SupplierEntity entity = byId.get();
            entity.setName(supplierDTO.getName());
            entity.setCategory(supplierDTO.getCategory());
            entity.setAddressNo(supplierDTO.getAddressNo());
            entity.setAddressLane(supplierDTO.getAddressLane());
            entity.setAddressCity(supplierDTO.getAddressCity());
            entity.setAddressState(supplierDTO.getAddressState());
            entity.setPostalCode(supplierDTO.getPostalCode());
            entity.setOriginCountry(supplierDTO.getOriginCountry());
            entity.setContactNo1(supplierDTO.getContactNo1());
            entity.setContactNo2(supplierDTO.getContactNo2());
            entity.setEmail(supplierDTO.getEmail());
            return;
        }
        throw new NotFoundException("Not Found Supplier : " + code);
    }

    @Override
    public SupplierDTO findByName(String name) {
        Optional<SupplierEntity> byName = supplierRepo.findByName(name);
        return Conversion.toSupplierDTO(byName.get());
    }

    @Override
    public List<SupplierDTO> getSuppliers() {
        return Conversion.toSupplierDTOList(supplierRepo.findAll());
    }
}
