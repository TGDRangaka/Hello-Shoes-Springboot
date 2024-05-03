package lk.ijse.helloshoesbackend.entity.keys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleItemId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private SaleEntity sale;
    @ManyToOne(cascade = CascadeType.ALL)
    private InventoryEntity item;
}
