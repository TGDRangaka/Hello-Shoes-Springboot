package lk.ijse.helloshoesspringboot.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesspringboot.entity.enums.Colors;
import lk.ijse.helloshoesspringboot.entity.enums.Sizes;
import lk.ijse.helloshoesspringboot.entity.keys.SaleItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "saleItem")
@AssociationOverrides({
        @AssociationOverride(name = "saleItemId.sale", joinColumns = @JoinColumn(name = "orderId")),
        @AssociationOverride(name = "saleItemId.item", joinColumns = @JoinColumn(name = "inventoryCode"))
})
public class SaleItemEntity {
    @Id
    private SaleItemId saleItemId;
    private Integer qty;
    private Double unitPrice;
}
