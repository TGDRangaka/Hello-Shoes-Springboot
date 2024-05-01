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

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceIMPL implements SaleService {
    private final SaleRepo saleRepo;
    private final EmployeeRepo employeeRepo;
    private final CustomerRepo customerRepo;

    @Override
    public boolean save(SaleDTO saleDTO) {
        SaleEntity saleEntity = Conversion.toSaleEntity(saleDTO);
        SaleEntity saved = saleRepo.save(saleEntity);
//        SaleEntity saved = saleRepo.save(new SaleEntity("add", 0.0, PaymentMethods.CARD, 1, employeeRepo.findByEmail("dilshan@gmail.com").get(), customerRepo.findByNameAndEmail("Krishan Mihiranga", "krishan@example.com").get(), new ArrayList<>()));

        if(saved != null){
            return true;
        }else{
            return false;
        }
    }
}
