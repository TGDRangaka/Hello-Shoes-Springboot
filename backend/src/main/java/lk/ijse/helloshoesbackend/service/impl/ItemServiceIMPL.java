package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.entity.ItemEntity;
import lk.ijse.helloshoesbackend.repo.ItemRepo;
import lk.ijse.helloshoesbackend.service.ItemService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceIMPL implements ItemService {
    private final ItemRepo itemRepo;

    @Override
    public boolean save(ItemDTO dto) throws Exception {
            List<InventoryEntity> inventoryEntities = Conversion.toInventoryEntityList(dto.getInventoryItems());

            ItemEntity itemEntity = Conversion.toItemEntity(dto);
            itemEntity.setInventoryItems(inventoryEntities);
            itemRepo.save(itemEntity);

            return true;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return Conversion.toItemDTOList(itemRepo.findAll());
    }
}
