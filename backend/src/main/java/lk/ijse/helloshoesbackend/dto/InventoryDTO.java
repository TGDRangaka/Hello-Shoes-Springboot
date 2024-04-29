package lk.ijse.helloshoesbackend.dto;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.ItemEntity;
import lk.ijse.helloshoesbackend.entity.ItemImageEntity;
import lk.ijse.helloshoesbackend.entity.ResupplyItemEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import lk.ijse.helloshoesbackend.entity.enums.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private String inventoryCode;
    private Sizes size;
    private String colors;
    private Integer originalQty;
    private Integer currentQty;
    private String status;

    private ItemDTO item;

    private ItemImageDTO itemImage;

    private List<ResupplyItemEntity> resupplyItems;

    private List<SaleItemEntity> saleItems;
}
