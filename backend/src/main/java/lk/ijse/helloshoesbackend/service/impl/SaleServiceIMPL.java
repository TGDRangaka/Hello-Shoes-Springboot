package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.repo.SaleRepo;
import lk.ijse.helloshoesbackend.service.SaleService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceIMPL implements SaleService {
    private final SaleRepo saleRepo;

    @Override
    public boolean save(SaleDTO saleDTO) {
        SaleEntity saleEntity = Conversion.toSaleEntity(saleDTO);
        SaleEntity saved = saleRepo.save(saleEntity);

        if(saved != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<SaleDTO> getSales() {
        List<SaleEntity> all = saleRepo.findAll();
        return Conversion.toSaleDTOList(all);
    }
}
