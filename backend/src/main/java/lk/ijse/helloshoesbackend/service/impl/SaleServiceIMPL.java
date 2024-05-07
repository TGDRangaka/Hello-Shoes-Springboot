package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.repo.SaleItemRepo;
import lk.ijse.helloshoesbackend.repo.SaleRepo;
import lk.ijse.helloshoesbackend.service.SaleService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceIMPL implements SaleService {
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;

    @Override
    public void updateSaleTotal(String orderId, Double amount) {
        Optional<SaleEntity> byId = saleRepo.findById(orderId);
        Double totalPrice = byId.get().getTotalPrice();
        byId.get().setTotalPrice(totalPrice - amount);
        saleRepo.save(byId.get());
    }

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

    @Override
    public List<SaleItemDTO> getSaleItems(String orderId) {
        List<SaleItemEntity> saleItemsByOrderId = saleRepo.getSaleItemsByOrderId(orderId);
        return Conversion.toSaleItemDTOList(saleItemsByOrderId);
    }

    @Override
    public void refundUpdateSaleItem(SaleItemId saleItemId, int refundQty) {
        Optional<SaleItemEntity> byId = saleItemRepo.findById(saleItemId);
        if(byId.isPresent()){
            int soldQty = byId.get().getQty();

            if(refundQty > soldQty){
                throw new InvalidDataException("Refund quantity is greater than sold quantity");
            }else if(refundQty == soldQty){
                saleItemRepo.deleteById(saleItemId);
            }else if(refundQty < soldQty && refundQty > 0){
                byId.get().setQty(soldQty - refundQty);
                saleItemRepo.save(byId.get());
            }else{
                throw new InvalidDataException("Invalid refund quantity");
            }
        }
    }

    @Override
    public boolean checkRefundAvailable(String orderId) {
        SaleEntity saleEntity = saleRepo.findById(orderId).get();
        return saleEntity.getOrderDate().isAfter(LocalDate.now().minusDays(3));
    }


}
