package lk.ijse.helloshoesbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private String orderId;
    @NotNull
    @Min(0)
    private Double totalPrice;
    @NotNull
    private PaymentMethods paymentMethod;
    private Integer addedPoints;
    private LocalDate orderDate;
    private LocalTime orderTime;

    private EmployeeDTO employee;
    private CustomerDTO customer;
    @NotNull
    private List<SaleItemDTO> saleItems;
}
