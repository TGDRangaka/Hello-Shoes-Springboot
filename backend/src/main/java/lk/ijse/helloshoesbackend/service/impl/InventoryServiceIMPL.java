package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.InventoryRepo;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceIMPL implements InventoryService {
    private final InventoryRepo inventoryRepo;

    @Override
    public int updateCurrentQty(String inventoryId, int qty) {
        Optional<InventoryEntity> itemOptional = inventoryRepo.findById(inventoryId);
        if (itemOptional.isPresent()) {
            InventoryEntity item = itemOptional.get();
            int currentQty = item.getCurrentQty();
            int originalQty = item.getOriginalQty();

            if(currentQty <= 0 ) throw new InvalidDataException("No stock available in inventory for : " + inventoryId);

            item.setCurrentQty(currentQty - qty);
            double currentQtyPer = ((double) (currentQty - qty) / originalQty) * 100;

            inventoryRepo.save(item);
            return (int) currentQtyPer;
        }
        throw new NotFoundException("Not Found Item Named : " + inventoryId);
    }

    @Override
    public void updateStockStatus(String inventoryId, String status) {
        Optional<InventoryEntity> itemOptional = inventoryRepo.findById(inventoryId);
        if (itemOptional.isPresent()) {
            itemOptional.get().setStatus(status);
        }
    }
}
