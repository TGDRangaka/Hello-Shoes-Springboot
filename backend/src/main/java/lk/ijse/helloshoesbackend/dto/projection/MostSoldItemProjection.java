package lk.ijse.helloshoesbackend.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostSoldItemProjection implements Comparable<MostSoldItemProjection> {
    private int qty;
    private String itemCode;
    private String itemName;
    private String itemImage;

    @Override
    public int compareTo(MostSoldItemProjection o) {
        return o.getQty() - this.qty;
    }
}
