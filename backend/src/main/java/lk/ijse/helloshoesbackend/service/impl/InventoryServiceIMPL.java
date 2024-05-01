package lk.ijse.helloshoesbackend.service.impl;

import jakarta.persistence.EntityManager;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.InventoryRepo;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceIMPL implements InventoryService {
    private final InventoryRepo inventoryRepo;
    private final EntityManager entityManager;

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

    @Override
    public List<InventoryDTO> getAll() {
        List<InventoryEntity> all = inventoryRepo.findAll();
        all.forEach(entity -> {
            entity.setItem(null);
            entity.setResupplyItems(null);
            entity.setSaleItems(null);
        });
        return Conversion.toInventoryDTOList(all);
    }

    @Override
    public List<InventoryDTO> getAllAvailableItems() {
        List<InventoryEntity> allAvailable = inventoryRepo.findByStatus("available");
        List<InventoryEntity> allLow = inventoryRepo.findByStatus("low");
        allAvailable.addAll(allLow);
        allAvailable.forEach(entity -> {
//            entity.getItem().setInventoryItems(new ArrayList<>());
//            entity.setItem(null);
//            entityManager.detach(entity);
        });
        return Conversion.toInventoryDTOList(allAvailable);
    }
}
