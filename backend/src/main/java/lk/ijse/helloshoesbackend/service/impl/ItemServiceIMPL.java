package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.entity.ItemEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.ItemRepo;
import lk.ijse.helloshoesbackend.service.ItemService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemServiceIMPL implements ItemService {
    private final ItemRepo itemRepo;

    @Override
    public boolean save(ItemDTO dto){
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
}
