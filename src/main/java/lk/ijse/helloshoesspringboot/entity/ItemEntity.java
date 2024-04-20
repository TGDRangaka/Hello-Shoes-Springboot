package lk.ijse.helloshoesspringboot.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesspringboot.entity.enums.ItemCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy="saleItemId.item", cascade = CascadeType.ALL)
    private List<SaleItemEntity> saleItems;

    @OneToMany(mappedBy = "resupplyItemId.item", cascade = CascadeType.ALL)
    private List<ResupplyItemEntity> resupplyItems;
}
