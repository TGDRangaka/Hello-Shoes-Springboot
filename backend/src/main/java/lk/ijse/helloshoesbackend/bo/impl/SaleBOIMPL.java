package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lk.ijse.helloshoesbackend.service.SaleService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class SaleBOIMPL implements SaleBO {
    private final SaleService saleService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public Map saveSale(SaleDTO saleDTO, String user) {
//        Set Employee who did the sale
        EmployeeDTO employee = employeeService.getEmployee(user);
        saleDTO.setEmployee(employee);

//        Set Customer
        CustomerDTO customer = customerService.findByNameAndEmail(saleDTO.getCustomer().getName(), saleDTO.getCustomer().getEmail());
        saleDTO.setCustomer((customer != null) ? customer : new CustomerDTO(
                UtilMatter.generateUUID(), saleDTO.getCustomer().getName(), null, null, CustomerLevel.NEW, 0, null, null, null, null, null, null, saleDTO.getCustomer().getEmail(), null, null
            ));
        int cusTotalPoints = saleDTO.getCustomer().getTotalPoints() + saleDTO.getAddedPoints();
        saleDTO.getCustomer().setTotalPoints(cusTotalPoints);
        saleDTO.getCustomer().setLevel(
                (cusTotalPoints >= 200) ? CustomerLevel.GOLD
                : (cusTotalPoints >= 100) ? CustomerLevel.SILVER
                : (cusTotalPoints >= 50) ? CustomerLevel.BRONZE
                : CustomerLevel.NEW
        );

//        Set order id and order datetime
        String orderId = UtilMatter.generateUUID();
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
}
