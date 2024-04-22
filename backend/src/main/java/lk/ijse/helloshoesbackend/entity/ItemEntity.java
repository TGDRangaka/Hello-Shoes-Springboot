package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.ItemCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    private String itemCode;
    private String description;
    private ItemCategories category;
    private String supplierName;
    @ManyToOne
    private SupplierEntity supplier;
    private Double unitPriceSale;
    private Double unitPriceBuy;
    private Double expectedPrice;
    private Double profitMargin;
}
