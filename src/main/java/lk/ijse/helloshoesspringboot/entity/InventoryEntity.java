package lk.ijse.helloshoesspringboot.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesspringboot.entity.enums.Colors;
import lk.ijse.helloshoesspringboot.entity.enums.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Sizes size;
    private Colors colors;
    private Integer originalQty;
    private Integer currentQty;
    private String status;
    @ManyToOne
    private ItemEntity item;
    @ManyToOne
    private ItemImageEntity itemImage;
}
