package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.ItemBO;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.dto.ItemImageDTO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.service.ItemImageService;
import lk.ijse.helloshoesbackend.service.ItemService;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ItemBOIMPL implements ItemBO {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final SupplierService supplierService;

    @Override
    public boolean saveItem(ItemDTO dto) {
        Random random = new Random();
        dto.setItemCode(dto.getItemCode() + (random.nextInt(40000) + 10000));
        dto.getInventoryItems();
        String tempColor = "EMPTY";
        ItemImageDTO image = new ItemImageDTO();
        for(int i = 0; i < dto.getInventoryItems().size(); i++){
            if(!tempColor.equals(dto.getInventoryItems().get(i).getColors())){
                image.setId(itemImageService.saveImage(dto.getInventoryItems().get(i).getItemImage().getImage()));
                image.setImage(dto.getInventoryItems().get(i).getItemImage().getImage());
                tempColor = dto.getInventoryItems().get(i).getColors();
            }
            dto.getInventoryItems().get(i).setInventoryCode(dto.getItemCode() +"_"+ dto.getInventoryItems().get(i).getSize().name() +"_"+ tempColor);
            dto.getInventoryItems().get(i).setItemImage(image);
            dto.getInventoryItems().get(i).setOriginalQty(0);
            dto.getInventoryItems().get(i).setCurrentQty(0);
            dto.getInventoryItems().get(i).setStatus("not available");
            dto.getInventoryItems().get(i).setItem(new ItemDTO(
                    dto.getItemCode(),
                    dto.getDescription(),
                    dto.getCategory(),
                    dto.getSupplierName(),
                    null,
                    dto.getUnitPriceSale(),
                    dto.getUnitPriceBuy(),
                    dto.getExpectedProfit(),
                    dto.getProfitMargin(),
                    new ArrayList<>()
            ));
        }
        SupplierDTO supplierByName = supplierService.findByName(dto.getSupplierName());
        dto.setSupplier(supplierByName);
        dto.setSupplierName(supplierByName.getName());

        return itemService.save(dto);
    }
}
