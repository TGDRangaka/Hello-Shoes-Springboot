package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.repo.SaleItemRepo;
import lk.ijse.helloshoesbackend.repo.SaleRepo;
import lk.ijse.helloshoesbackend.service.SaleService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SaleServiceIMPL implements SaleService {
    private final SaleRepo saleRepo;

    @Override
    public boolean save(SaleDTO saleDTO) {
        log.info("Saving sale for orderId: {}", saleDTO.getOrderId());
        SaleEntity saleEntity = Conversion.toSaleEntity(saleDTO);
        SaleEntity saved = saleRepo.save(saleEntity);

        if(saved != null){
            log.info("Sale saved for orderId: {}", saleDTO.getOrderId());
            return true;
        }else{
            log.warn("Sale not saved for orderId: {}", saleDTO.getOrderId());
            return false;
        }
    }

    @Override
    public List<SaleDTO> getSales() {
        log.info("Fetching all sales");
        List<SaleEntity> all = saleRepo.findAll();
        log.info("Fetched {} sales", all.size());
        return Conversion.toSaleDTOList(all);
    }

    @Override
    public List<SaleItemDTO> getSaleItems(String orderId) {
        log.info("Fetching sale items for orderId: {}", orderId);
        List<SaleItemEntity> saleItemsByOrderId = saleRepo.getSaleItemsByOrderId(orderId);
        log.info("Fetched {} sale items for orderId: {}", saleItemsByOrderId.size(), orderId);
        return Conversion.toSaleItemDTOList(saleItemsByOrderId);
    }

    @Override
    public boolean checkRefundAvailable(String orderId) {
        log.info("Checking refund availability for orderId: {}", orderId);
        Optional<SaleEntity> byId = saleRepo.findById(orderId);
        if(byId.isPresent()){
            return byId.get().getOrderDate().isAfter(LocalDate.now().minusDays(3));
        }
        log.error("Could not find order by id: {}", orderId);
        throw new NotFoundException("Not found order by id " + orderId);
    }


}
