package lk.ijse.helloshoesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {
    private String refundId;
    private String description;
    private LocalDate refundDate;
    private int qty;
    private double refundTotal;

    private EmployeeDTO employee;
    private SaleItemDTO saleItem;
}
