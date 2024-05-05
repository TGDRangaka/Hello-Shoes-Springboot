package lk.ijse.helloshoesbackend.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostSoldItemProjection {
    private int qty;
    private String itemCode;
    private String itemName;
    private String itemImage;
}
