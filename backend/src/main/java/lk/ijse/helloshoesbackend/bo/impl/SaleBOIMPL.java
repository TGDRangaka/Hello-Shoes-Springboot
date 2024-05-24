package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.*;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.exception.RefundTimeExceededException;
import lk.ijse.helloshoesbackend.service.*;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SaleBOIMPL implements SaleBO {
    private final SaleService saleService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final InventoryService inventoryService;
    private final RefundService refundService;

    @Override
    public Map saveSale(SaleDTO saleDTO, String user){
        log.info("Attempting to save sale");
//        Set Employee who did the sale
        EmployeeDTO employee = employeeService.getEmployee(user);
        saleDTO.setEmployee(employee);

//        Set Customer
        CustomerDTO customer = customerService.findByNameAndEmail(saleDTO.getCustomer().getName(), saleDTO.getCustomer().getEmail());
        saleDTO.setCustomer(customer);
        if(customer != null){
            int cusTotalPoints = saleDTO.getCustomer().getTotalPoints() + saleDTO.getAddedPoints();
            saleDTO.getCustomer().setTotalPoints(cusTotalPoints);
            saleDTO.getCustomer().setRecentPurchaseDateTime(LocalDateTime.now());
            saleDTO.getCustomer().setLevel(
                    (cusTotalPoints >= 200) ? CustomerLevel.GOLD
                            : (cusTotalPoints >= 100) ? CustomerLevel.SILVER
                            : (cusTotalPoints >= 50) ? CustomerLevel.BRONZE
                            : CustomerLevel.NEW
            );
        }


//        Set order id and order datetime
        String orderId = "OD" + (new Random().nextInt(900000) + 100000);
        log.info("Setting order id: {}", orderId);
        saleDTO.setOrderId(orderId);
        saleDTO.setOrderDate(LocalDate.now());
        saleDTO.setOrderTime(LocalTime.now());

        Map<String, Integer> percentageOfItems = new HashMap<>();
        for(int i = 0; i < saleDTO.getSaleItems().size(); i++){
            SaleItemDTO saleItem = saleDTO.getSaleItems().get(i);
            String inventoryCode = saleItem.getSaleItemId().getItem().getInventoryCode();


            saleItem.getSaleItemId().setSale(new SaleEntity(orderId, 0.0, PaymentMethods.CARD, 0, null, null,null, null, new ArrayList<>()));

            // Decrease the current qty for sale item and return percentage of current qty/original qty
            int percentageOfCurrentQty = inventoryService.updateCurrentQty(inventoryCode, saleItem.getQty());

            // Update status based on current quantity percentage
            if (percentageOfCurrentQty <= 0) {
                inventoryService.updateStockStatus(inventoryCode, "not available");
            } else if (percentageOfCurrentQty <= 50) {
                inventoryService.updateStockStatus(inventoryCode, "low");
            }

            percentageOfItems.put(saleItem.getSaleItemId().getItem().getInventoryCode(), percentageOfCurrentQty);
        }

        boolean save = saleService.save(saleDTO);
        if(!save) throw new InvalidDataException("Invalid Data");

        return percentageOfItems;
    }

    @Override
    public List<SaleDTO> getSales() {
        log.info("Attempting to get all sales");
        return saleService.getSales();
    }

    @Override
    public void refundSaleItems(List<RefundDTO> refunds, String user){
//        Set Employee who did the sale
        EmployeeDTO employee = employeeService.getEmployee(user);

        LocalDate date = LocalDate.now();
        String orderId = refunds.get(0).getSaleItem().getSaleItemId().getSale().getOrderId();
        log.info("Attempting to refund sale items for orderId: {}", orderId);
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
            log.info("Attempting to save refund item");
            refundService.refundSaleItem(refund);

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
    public List<SaleItemDTO> getSaleItems(String orderId) {
        log.info("Attempting to get sale items for orderId: {}", orderId);
        boolean isRefundAvailable = saleService.checkRefundAvailable(orderId);
        log.info("Refund available {} for orderId: {}", isRefundAvailable, orderId);
        if(isRefundAvailable){
            List<SaleItemDTO> saleItems = saleService.getSaleItems(orderId);
            for (int i = 0; i < saleItems.size(); i++) {
                int currentQty = saleItems.get(i).getQty();
                int refundedQty = 0;
//            check if refunded before
                List<RefundDTO> alreadyRefunds = refundService.checkRefundedBefore(saleItems.get(i));
                for (RefundDTO alreadyRefund : alreadyRefunds) {
                    refundedQty += alreadyRefund.getQty();
                }

                saleItems.get(i).setQty(currentQty - refundedQty);
                if(saleItems.get(i).getQty() <= 0){
                    saleItems.remove(i);
                }
            }
            return saleItems;
        }
        log.error("Refund request exceeded time limit for order item ID: {}", orderId);
        throw new RefundTimeExceededException("Refund request exceeded the 3-day limit for order item ID: " + orderId);
    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        log.info("Attempting to get all refunds");
        return refundService.getAllRefunds();
    }
}
