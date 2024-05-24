package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.dto.RefundDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.entity.RefundEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.repo.RefundRepo;
import lk.ijse.helloshoesbackend.service.*;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RefundServiceIMPL implements RefundService {
    private final RefundRepo refundRepo;
    private final EmployeeService employeeService;
    private final InventoryService inventoryService;

    @Override
    public void saveRefund(List<RefundDTO> refunds, String user) {
        log.info("Attempting to save refund items");
//        Set Employee who did the sale
        EmployeeDTO employee = employeeService.getEmployee(user);

        LocalDate date = LocalDate.now();
        String orderId = refunds.get(0).getSaleItem().getSaleItemId().getSale().getOrderId();
//        double totalRefund = 0.0;

        for (int i = 0; i < refunds.size(); i++) {
            RefundDTO refund = refunds.get(i);
            if(refund.getQty() <= 0) {
                log.error("Invalid refund quantity {}", refund.getQty());
                throw new InvalidDataException("Invalid refund quantity: " + refund.getQty());
            };
//            totalRefund += refund.getRefundTotal();
            String inventoryCode = refund.getSaleItem().getSaleItemId().getItem().getInventoryCode();

//            set refund details
            refund.setRefundId(UtilMatter.generateUUID());
            refund.setRefundDate(date);
            refund.setEmployee(employee);

//            save refund
            log.info("Saving refund");
            refund.setRefundId(UtilMatter.generateUUID());
            refundRepo.save(Conversion.toRefundEntity(refund));
            log.info("Refund saved successfully");

//            delete saleItem if qty equals, otherwise update quantity of saleItem
//            saleService.refundUpdateSaleItem(refund.getSaleItem().getSaleItemId(), refund.getQty());

//            inventory update (send minus integer to increment)
            log.info("Attempting to update item current quantity for inventoryCode: {}", inventoryCode);
            int stockPercentage = inventoryService.updateCurrentQty(inventoryCode, -refund.getQty());

            log.info("Attempting to update item stock status for inventoryCode: {}", inventoryCode);
            if (stockPercentage > 50) {
                inventoryService.updateStockStatus(inventoryCode, "available");
            } else if(stockPercentage > 0) {
                inventoryService.updateStockStatus(inventoryCode, "low");
            }
        }
        log.info("Saved All refund items");
    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        log.info("Fetching all refunds");
        List<RefundEntity> all = refundRepo.findAll();
        log.info("Fetched {} refunds", all.size());
        return Conversion.toRefundDTOList(all);
    }

    @Override
    public List<RefundDTO> checkRefundedBefore(SaleItemDTO saleItem) {
        log.info("Fetching refunds by orderId: {} and inventoryCode: {}", saleItem.getSaleItemId().getSale().getOrderId(), saleItem.getSaleItemId().getItem().getInventoryCode());
        List<RefundEntity> allBySaleItem = refundRepo.findAllBySaleItem(new ModelMapper().map(saleItem, SaleItemEntity.class));
        log.info("Fetched {} refunds", allBySaleItem.size());
        return Conversion.toRefundDTOList(allBySaleItem);
    }
}
