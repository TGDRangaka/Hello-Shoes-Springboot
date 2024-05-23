package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.InventoryRepo;
import lk.ijse.helloshoesbackend.repo.ItemRepo;
import lk.ijse.helloshoesbackend.service.InventoryService;
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
public class InventoryServiceIMPL implements InventoryService {
    private final InventoryRepo inventoryRepo;
    private final ItemRepo itemRepo;

    @Override
    public int updateCurrentQty(String inventoryId, int qty){
        log.info("Updating current quantity for inventory item with ID: {}", inventoryId);
        Optional<InventoryEntity> itemOptional = inventoryRepo.findById(inventoryId);
        if (itemOptional.isPresent()) {
            InventoryEntity item = itemOptional.get();
            int currentQty = item.getCurrentQty();
            int originalQty = item.getOriginalQty();

            if(currentQty <= 0 ) {
                log.error("No stock available in inventory for item with ID: {}", inventoryId);
                throw new InvalidDataException("No stock available in inventory for : " + inventoryId);
            }

            item.setCurrentQty(currentQty - qty);
            double currentQtyPer = ((double) (currentQty - qty) / originalQty) * 100;

            inventoryRepo.save(item);
            log.info("Current quantity updated successfully for inventory item with ID: {}", inventoryId);
            return (int) currentQtyPer;
        }
        log.error("Inventory item with ID: {} not found", inventoryId);
        throw new NotFoundException("Not Found Item Named : " + inventoryId);
    }

    @Override
    public void updateStockStatus(String inventoryId, String status) {
        log.info("Updating stock status for inventory item with ID: {}", inventoryId);
        Optional<InventoryEntity> itemOptional = inventoryRepo.findById(inventoryId);
        if (itemOptional.isPresent()) {
            itemOptional.get().setStatus(status);
            log.info("Stock status updated successfully for inventory item with ID: {}", inventoryId);
        }
        log.error("Inventory item with ID: {} not found", inventoryId);
        throw new NotFoundException("Not Found Item Named : " + inventoryId);
    }

    @Override
    public List<InventoryDTO> getAll() {
        log.info("Fetching all inventory items");
        List<InventoryEntity> all = inventoryRepo.findAll();
        log.info("Fetched {} inventory items successfully", all.size());
        return Conversion.toInventoryDTOList(all);
    }

    @Override
    public List<InventoryDTO> getAllAvailableItems() {
        log.info("Fetching all available inventory items");
        List<InventoryEntity> allAvailable = inventoryRepo.findByStatus("available");
        List<InventoryEntity> allLow = inventoryRepo.findByStatus("low");
        allAvailable.addAll(allLow);
        log.info("Fetched {} available inventory items successfully", allAvailable.size());
        return Conversion.toInventoryDTOList(allAvailable);
    }

    @Override
    public void restock(String inventoryId, int qty){
        log.info("Restocking inventory item with ID: {}, quantity: {}", inventoryId, qty);
        if(qty == 0) return;
        if(qty < 0) {
            log.error("Invalid quantity for restocking inventory item with ID: {}, quantity: {}", inventoryId, qty);
            throw new InvalidDataException("Qty must be greater than 1 : " + inventoryId);
        }
        InventoryEntity item = inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> {
                    log.error("Inventory item with ID: {} not found", inventoryId);
                    return new NotFoundException("Not found inventory item with id " + inventoryId);
                });
        int totQty = item.getCurrentQty() + qty;
        item.setCurrentQty(totQty);
        item.setOriginalQty(totQty);
        item.setStatus("available");
        log.info("Inventory item restocked successfully with ID: {}, quantity: {}", inventoryId, qty);
    }

    @Override
    public String[] getAvailableColorsById(String id){
        log.info("Fetching available colors for inventory item with ID: {}", id);
        if(itemRepo.existsById(id)){
            String[] availableColors = inventoryRepo.getAvailableColorsById(id);
            log.info("Fetched available colors successfully for inventory item with ID: {}", id);
            return availableColors;
        }
        log.error("Inventory item with ID: {} not found", id);
        throw new NotFoundException("Not found inventory item with id " + id);
    }
}
