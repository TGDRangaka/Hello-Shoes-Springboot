package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;

import java.util.List;

public interface InventoryService {
    int updateCurrentQty(String inventoryId, int qty);
    void updateStockStatus(String inventoryId, String status);
    List<InventoryDTO> getAll();
    List<InventoryDTO> getAllAvailableItems();
    void restock(String inventoryId, int qty);

    String[] getAvailableColorsById(String id);
}
