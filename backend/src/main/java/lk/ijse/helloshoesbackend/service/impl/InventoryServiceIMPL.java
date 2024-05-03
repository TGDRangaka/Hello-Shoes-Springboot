package lk.ijse.helloshoesbackend.service.impl;

import jakarta.persistence.EntityManager;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.InventoryRepo;
import lk.ijse.helloshoesbackend.repo.ItemRepo;
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
    private final ItemRepo itemRepo;

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
        return Conversion.toInventoryDTOList(all);
    }

    @Override
    public List<InventoryDTO> getAllAvailableItems() {
        List<InventoryEntity> allAvailable = inventoryRepo.findByStatus("available");
        List<InventoryEntity> allLow = inventoryRepo.findByStatus("low");
        allAvailable.addAll(allLow);
        return Conversion.toInventoryDTOList(allAvailable);
    }

    @Override
    public void restock(String inventoryId, int qty) {
        if(qty == 0) return;
        if(qty < 0) throw new InvalidDataException("Qty must be greater than 1 : " + inventoryId);
        InventoryEntity item = inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new NotFoundException("Not found inventory item with id " + inventoryId));
        int totQty = item.getCurrentQty() + qty;
        item.setCurrentQty(totQty);
        item.setOriginalQty(totQty);
        item.setStatus("available");
    }
}
