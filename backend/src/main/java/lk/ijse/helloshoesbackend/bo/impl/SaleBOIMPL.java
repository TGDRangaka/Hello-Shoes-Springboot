package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.service.SaleService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class SaleBOIMPL implements SaleBO {
    private final SaleService saleService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    @Override
    public boolean saveSale(SaleDTO saleDTO, String user) {
        EmployeeDTO employee = employeeService.getEmployee(user);
        saleDTO.setEmployee(employee);
        CustomerDTO customer = customerService.findByNameAndEmail(saleDTO.getCustomer().getName(), saleDTO.getCustomer().getEmail());
        if(customer != null){
            saleDTO.setCustomer(customer);
        }else saleDTO.setCustomer(new CustomerDTO(
                UtilMatter.generateUUID(), saleDTO.getCustomer().getName(), null, null, CustomerLevel.NEW, 0, null, null, null, null, null, null, saleDTO.getCustomer().getEmail(), null, null
        ));
        String orderId = UtilMatter.generateUUID();
        saleDTO.setOrderId(orderId);
        for(int i = 0; i < saleDTO.getSaleItems().size(); i++){
            saleDTO.getSaleItems().get(i).getSaleItemId().setSale(new SaleEntity(orderId, 0.0, PaymentMethods.CARD, 0, null, null, new ArrayList<>()));
        }

        return saleService.save(saleDTO);
    }
}
