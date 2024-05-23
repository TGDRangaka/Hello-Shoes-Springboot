package lk.ijse.helloshoesbackend.bo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lk.ijse.helloshoesbackend.bo.ItemBO;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.entity.enums.Sizes;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lk.ijse.helloshoesbackend.service.ItemImageService;
import lk.ijse.helloshoesbackend.service.ItemService;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemBOIMPL implements ItemBO {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final SupplierService supplierService;
    private final InventoryService inventoryService;

    @Override
    public boolean saveItem(ItemDTO dto){
        log.info("Attempting to save item");
        Random random = new Random();
        dto.setItemCode(dto.getItemCode() + (random.nextInt(40000) + 10000));
        List<InventoryDTO> colors = dto.getInventoryItems();
        List<InventoryDTO> allInventory = new ArrayList<>();

        if(dto.getCategory().name().equals("ACC")){
            String imgId = itemImageService.saveImage(colors.get(0).getItemImage().getImage());
            colors.get(0).getItemImage().setId(imgId);
            allInventory.add(getNewAccessory(dto, colors));
        }else{
            colors.forEach(color -> allInventory.addAll(getNewInventory(dto, color)));
        }
        dto.setInventoryItems(allInventory);
        SupplierDTO supplierByName = supplierService.findByName(dto.getSupplierName());
        dto.setSupplier(supplierByName);
        dto.setSupplierName(supplierByName.getName());

        return itemService.save(dto);
    }

    @Override
    public void updateItem(ItemDTO dto, String id) {
        log.info("Attempting to update item");
        List<String> availableColors = Arrays.asList(inventoryService.getAvailableColorsById(id));
        List<InventoryDTO> allInventory = new ArrayList<>();
        List<InventoryDTO> colors = dto.getInventoryItems();
        dto.setItemCode(id);

        colors.forEach(color -> {
            if(availableColors.contains(color.getColors())){
                itemImageService.updateImage(color.getItemImage());
            }else{
                allInventory.addAll(getNewInventory(dto, color));
            }
        });
        dto.setInventoryItems(allInventory);
        itemService.update(dto, id);
    }


    @Override
    public List<ItemDTO> getAllItems() {
        log.info("Attempting to get all items");
        List<ItemDTO> allItems = itemService.getAllItems();
        return allItems;
    }

    private InventoryDTO getNewAccessory(ItemDTO dto, List<InventoryDTO> colors) {
        String imgId = itemImageService.saveImage(colors.get(0).getItemImage().getImage());
        colors.get(0).getItemImage().setId(imgId);
        return new InventoryDTO(
                dto.getItemCode() +"_"+ colors.get(0).getSize() +"_"+ colors.get(0).getColors(),
                colors.get(0).getSize(),
                colors.get(0).getColors(),
                0,
                0,
                "not available",
                new ItemDTO(
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
                ),
                colors.get(0).getItemImage(),
                colors.get(0).getResupplyItems(),
                colors.get(0).getSaleItems()
        );
    }

    private List<InventoryDTO> getNewInventory(ItemDTO dto, InventoryDTO color){
        List<InventoryDTO> allInventory = new ArrayList<>();
        String imgId = itemImageService.saveImage(color.getItemImage().getImage());
        color.getItemImage().setId(imgId);
        for(int i = 5; i < 12; i++) {
            allInventory.add(new InventoryDTO(
                    dto.getItemCode() +"_SIZE_"+ i +"_"+ color.getColors(),
                    Sizes.valueOf("SIZE_"+i),
                    color.getColors(),
                    0,
                    0,
                    "not available",
                    new ItemDTO(
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
                    ),
                    color.getItemImage(),
                    color.getResupplyItems(),
                    color.getSaleItems()
            ));
        }
        return allInventory;
    }
}
