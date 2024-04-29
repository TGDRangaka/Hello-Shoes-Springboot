package lk.ijse.helloshoesbackend.dto;

import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.entity.SupplierEntity;
import lk.ijse.helloshoesbackend.entity.enums.ItemCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private String itemCode;
    private String description;
    private ItemCategories category;
    private String supplierName;
    private SupplierDTO supplier;
    private Double unitPriceSale;
    private Double unitPriceBuy;
    private Double expectedProfit;
    private Double profitMargin;
    private List<InventoryDTO> inventoryItems;
}
