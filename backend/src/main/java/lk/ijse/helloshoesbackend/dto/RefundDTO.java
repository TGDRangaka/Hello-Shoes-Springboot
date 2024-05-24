package lk.ijse.helloshoesbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {
    private String refundId;
    @NotNull
    @NotEmpty
    private String description;
    private LocalDate refundDate;
    @NotNull
    @Min(1)
    private int qty;
    @NotNull
    @Min(0)
    private double refundTotal;

    private EmployeeDTO employee;
    @NotNull
    private SaleItemDTO saleItem;
}
