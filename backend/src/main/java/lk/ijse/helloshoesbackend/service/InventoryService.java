package lk.ijse.helloshoesbackend.service;

public interface InventoryService {
    int updateCurrentQty(String inventoryId, int qty);
    void updateStockStatus(String inventoryId, String status);
}
