package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.entity.ItemEntity;
import lk.ijse.helloshoesbackend.entity.enums.Sizes;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.ItemRepo;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lk.ijse.helloshoesbackend.service.ItemImageService;
import lk.ijse.helloshoesbackend.service.ItemService;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemServiceIMPL implements ItemService {
    private final ItemRepo itemRepo;
    private final ItemImageService itemImageService;
    private final SupplierService supplierService;
    private final InventoryService inventoryService;

    @Override
    public boolean save(ItemDTO dto){
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

        log.info("Saving new item with id {}", dto.getItemCode());
        List<InventoryEntity> inventoryEntities = Conversion.toInventoryEntityList(dto.getInventoryItems());

        ItemEntity itemEntity = Conversion.toItemEntity(dto);
        itemEntity.setInventoryItems(inventoryEntities);
        itemRepo.save(itemEntity);
        log.info("New item with id {} saved successfully", dto.getItemCode());

        return true;
    }

    @Override
    public void update(ItemDTO dto, String itemCode) {
        log.info("Attempting to update item");
        List<String> availableColors = Arrays.asList(inventoryService.getAvailableColorsById(itemCode));
        List<InventoryDTO> allInventory = new ArrayList<>();
        List<InventoryDTO> colors = dto.getInventoryItems();
        dto.setItemCode(itemCode);

        colors.forEach(color -> {
            if(availableColors.contains(color.getColors())){
                itemImageService.updateImage(color.getItemImage());
            }else{
                allInventory.addAll(getNewInventory(dto, color));
            }
        });
        dto.setInventoryItems(allInventory);

        log.info("Updating item with id {}", itemCode);
        Optional<ItemEntity> byId = itemRepo.findById(itemCode);
        if(byId.isPresent()){

            byId.get().setDescription(dto.getDescription());
            byId.get().setUnitPriceBuy(dto.getUnitPriceBuy());
            byId.get().setUnitPriceSale(dto.getUnitPriceSale());
            byId.get().setExpectedProfit(dto.getExpectedProfit());
            byId.get().setProfitMargin(dto.getProfitMargin());
            if(!dto.getCategory().name().equals("ACC") && dto.getInventoryItems().size() > 0) {
                byId.get().getInventoryItems().addAll(Conversion.toInventoryEntityList(dto.getInventoryItems()));
            }
            log.info("Item with id {} updated successfully", itemCode);
            return;
        }
        log.error("Not found item with id {}", itemCode);
        throw new NotFoundException("Not Found Item : " + itemCode);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        log.info("Fetching all items");
        return Conversion.toItemDTOList(itemRepo.findAll());
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
