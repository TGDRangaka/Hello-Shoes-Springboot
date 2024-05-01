package lk.ijse.helloshoesbackend.dto;

import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemDTO {
    private SaleItemId saleItemId;
    private Integer qty;
    private Double unitPrice;
}
