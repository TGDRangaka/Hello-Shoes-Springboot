package lk.ijse.helloshoesbackend.dto;

import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private String orderId;
    private Double totalPrice;
    private PaymentMethods paymentMethod;
    private Integer addedPoints;
    private LocalDateTime orderDate;

    private EmployeeDTO employee;
    private CustomerDTO customer;
    private List<SaleItemDTO> saleItems;
}
