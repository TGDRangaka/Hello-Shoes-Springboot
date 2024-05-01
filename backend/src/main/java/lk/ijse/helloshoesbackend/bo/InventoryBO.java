package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.InventoryDTO;

import java.util.List;

public interface InventoryBO {
    List<InventoryDTO> getAllAvailableItems();
}
