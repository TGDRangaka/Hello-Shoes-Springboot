package lk.ijse.helloshoesbackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "description should not be empty")
    private String description;
    private ItemCategories category;
    private String supplierName;
    private SupplierDTO supplier;
    @Min(1)
    private Double unitPriceSale;
    @Min(1)
    private Double unitPriceBuy;
    @Min(0)
    private Double expectedProfit;
    @Min(0)
    private Double profitMargin;
    private List<InventoryDTO> inventoryItems;
}
