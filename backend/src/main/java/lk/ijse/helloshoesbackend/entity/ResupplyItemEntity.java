package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.keys.ResupplyItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "resupplyItem")
@AssociationOverrides({
        @AssociationOverride(name = "resupplyItemId.inventory", joinColumns = @JoinColumn(name = "inventoryCode")),
        @AssociationOverride(name = "resupplyItemId.resupply", joinColumns = @JoinColumn(name = "suppplyId"))
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResupplyItemEntity {
    @Id
    private ResupplyItemId resupplyItemId;
    private Integer suppliedQty;
    private Double total;
}
