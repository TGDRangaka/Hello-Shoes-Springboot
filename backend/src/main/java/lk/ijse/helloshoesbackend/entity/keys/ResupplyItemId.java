package lk.ijse.helloshoesbackend.entity.keys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.entity.ResupplyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResupplyItemId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private InventoryEntity inventory;
    @ManyToOne(cascade = CascadeType.ALL)
    private ResupplyEntity resupply;
}