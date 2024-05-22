package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.*;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.service.*;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
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
public class SaleBOIMPL implements SaleBO {
    private final SaleService saleService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final InventoryService inventoryService;
    private final RefundService refundService;

    @Override
    public Map saveSale(SaleDTO saleDTO, String user) {
//        Set Employee who did the sale
        EmployeeDTO employee = employeeService.getEmployee(user);
        saleDTO.setEmployee(employee);

//        Set Customer
        CustomerDTO customer = customerService.findByNameAndEmail(saleDTO.getCustomer().getName(), saleDTO.getCustomer().getEmail());
        saleDTO.setCustomer(customer);
        if(customer != null){
//            saleDTO.setCustomer((customer != null) ? customer : new CustomerDTO(
//                    UtilMatter.generateUUID(), saleDTO.getCustomer().getName(), null, null, CustomerLevel.NEW, 0, null, null, null, null, null, null, saleDTO.getCustomer().getEmail(), null, null
//            ));
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
        return saleService.getSales();
    }

    @Override
    public void refundSaleItems(List<RefundDTO> refunds, String user) {
//        Set Employee who did the sale
        EmployeeDTO employee = employeeService.getEmployee(user);

        LocalDate date = LocalDate.now();
        String orderId = refunds.get(0).getSaleItem().getSaleItemId().getSale().getOrderId();
//        double totalRefund = 0.0;

        for (int i = 0; i < refunds.size(); i++) {
            RefundDTO refund = refunds.get(i);
            if(refund.getQty() <= 0) throw new InvalidDataException("Invalid refund quantity: " + refund.getQty());
//            totalRefund += refund.getRefundTotal();
            String inventoryCode = refund.getSaleItem().getSaleItemId().getItem().getInventoryCode();

//            set refund details
            refund.setRefundId(UtilMatter.generateUUID());
            refund.setRefundDate(date);
            refund.setEmployee(employee);

//            save refund
            refundService.refundSaleItem(refund);

//            delete saleItem if qty equals, otherwise update quantity of saleItem
//            saleService.refundUpdateSaleItem(refund.getSaleItem().getSaleItemId(), refund.getQty());

//            inventory update (send minus integer to increment)
            int stockPercentage = inventoryService.updateCurrentQty(inventoryCode, -refund.getQty());
            if (stockPercentage > 50) {
                inventoryService.updateStockStatus(inventoryCode, "available");
            } else if(stockPercentage > 0) {
                inventoryService.updateStockStatus(inventoryCode, "low");
            }
        }

//        update order total
//        saleService.updateSaleTotal(orderId, totalRefund);

    }

    @Override
    public List<SaleItemDTO> getSaleItems(String orderId) {
        boolean isRefundAvailable = saleService.checkRefundAvailable(orderId);
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
        throw new NotFoundException("Refund not available for order: " + orderId);
    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        return refundService.getAllRefunds();
    }
}
