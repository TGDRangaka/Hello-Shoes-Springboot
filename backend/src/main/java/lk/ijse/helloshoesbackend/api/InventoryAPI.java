package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.InventoryBO;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class InventoryAPI {
    private final InventoryBO inventoryBO;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Health check endpoint accessed.");
        return "Inventory Health Good";
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllItems() {
        log.info("Get all inventories endpoint called");
        List<InventoryDTO> allItems = inventoryBO.getAllItems();
        return ResponseEntity.ok(allItems);
    }

    @GetMapping("/available")
    public ResponseEntity<List<InventoryDTO>> getAllAvailableItems(){
        log.info("Get all available inventories endpoint called");
        List<InventoryDTO> allAvailableItems = inventoryBO.getAllAvailableItems();
        return ResponseEntity.ok(allAvailableItems);
    }
}
