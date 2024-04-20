package lk.ijse.helloshoesspringboot.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesspringboot.entity.enums.Colors;
import lk.ijse.helloshoesspringboot.entity.enums.Sizes;
import lk.ijse.helloshoesspringboot.entity.keys.ResupplyItemId;

import java.util.Date;

@Entity
@Table(name = "resupplyItem")
@AssociationOverrides({
        @AssociationOverride(name = "resupplyItemId.item", joinColumns = @JoinColumn(name = "itemCode")),
        @AssociationOverride(name = "resupplyItemId.resupply", joinColumns = @JoinColumn(name = "suppplyId"))
})
public class ResupplyItemEntity {
    @Id
    private ResupplyItemId resupplyItemId;
    private Integer suppliedQty;
    private Date suppliedDate;
    private Colors color;
    @Enumerated(EnumType.STRING)
    private Sizes size;
}
