package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.*;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.exception.RefundTimeExceededException;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.repo.SaleItemRepo;
import lk.ijse.helloshoesbackend.repo.SaleRepo;
import lk.ijse.helloshoesbackend.service.*;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SaleServiceIMPL implements SaleService {
    private final SaleRepo saleRepo;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final InventoryService inventoryService;
    private final RefundService refundService;

    @Override
    public boolean save(SaleDTO saleDTO, String user) {
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

        log.info("Saving sale for orderId: {}", saleDTO.getOrderId());
        SaleEntity saleEntity = Conversion.toSaleEntity(saleDTO);
        SaleEntity saved = saleRepo.save(saleEntity);

        if(saved != null){
            log.info("Sale saved for orderId: {}", saleDTO.getOrderId());
            return true;
        }

        log.warn("Sale not saved for orderId: {}", saleDTO.getOrderId());
        throw new InvalidDataException("Invalid Data");
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
        boolean isRefundAvailable = checkRefundAvailable(orderId);
        log.info("Refund available {} for orderId: {}", isRefundAvailable, orderId);
        if(isRefundAvailable){
//            Optional<SaleEntity> byId = saleRepo.findById(orderId);
            List<SaleItemDTO> saleItems = Conversion.toSaleItemDTOList(saleRepo.getSaleItemsByOrderId(orderId));
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
