package lk.ijse.helloshoesbackend.dto;

import lk.ijse.helloshoesbackend.entity.keys.ResupplyItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResupplyItemDTO {
    private ResupplyItemId resupplyItemId;
    private Integer suppliedQty;
    private Double total;
}
