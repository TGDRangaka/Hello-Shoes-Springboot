package lk.ijse.helloshoesbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemDTO {
    private SaleItemId saleItemId;
    @NotNull
    @Min(1)
    private Integer qty;
    private Double unitPrice;
}
