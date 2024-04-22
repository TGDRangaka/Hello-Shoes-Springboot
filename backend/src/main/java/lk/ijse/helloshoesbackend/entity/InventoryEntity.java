package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.Colors;
import lk.ijse.helloshoesbackend.entity.enums.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    private String inventoryCode;
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

    @OneToMany(mappedBy = "resupplyItemId.inventory", cascade = CascadeType.ALL)
    private List<ResupplyItemEntity> resupplyItems;

    @OneToMany(mappedBy="saleItemId.item", cascade = CascadeType.ALL)
    private List<SaleItemEntity> saleItems;

    public InventoryEntity(Sizes size, Colors colors, Integer originalQty, Integer currentQty, String status, ItemEntity item, ItemImageEntity itemImage, List<ResupplyItemEntity> resupplyItems, List<SaleItemEntity> saleItems) {
        this.inventoryCode = generateInventoryCode(item.getItemCode(), size, colors);
        this.size = size;
        this.colors = colors;
        this.originalQty = originalQty;
        this.currentQty = currentQty;
        this.status = status;
        this.item = item;
        this.itemImage = itemImage;
        this.resupplyItems = resupplyItems;
        this.saleItems = saleItems;
    }

    public String generateInventoryCode(String itemCode, Sizes size, Colors color){
        return itemCode + "-" + size.name() + "-" + color.name();
    }
}
