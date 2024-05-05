package lk.ijse.helloshoesbackend.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemProjection {
    private int qty;
    private Double expectedProfit;
}
