package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.InventoryBO;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryBOIMPL implements InventoryBO {
    private final InventoryService inventoryService;

    @Override
    public List<InventoryDTO> getAllAvailableItems() {
        List<InventoryDTO> allAvailableItems = inventoryService.getAllAvailableItems();
        List<InventoryDTO> tempList = new ArrayList<>();
        allAvailableItems.forEach(item -> {
            ItemDTO dto = item.getItem();
            ItemDTO newDTO = new ItemDTO(
                    dto.getItemCode(),
                    dto.getDescription(),
                    null, null, null,
                    dto.getUnitPriceSale(),
                    null, null, null, null
            );

            InventoryDTO inventoryDTO = new InventoryDTO(
                    item.getInventoryCode(),
                    item.getSize(),
                    item.getColors(),
                    item.getOriginalQty(),
                    item.getCurrentQty(),
                    item.getStatus(),
                    newDTO,
                    item.getItemImage(),
                    null,
                    null
            );
            tempList.add(inventoryDTO);
        });
        return tempList;
    }
}
