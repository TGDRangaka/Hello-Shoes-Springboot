package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.InventoryBO;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@CrossOrigin
@RequiredArgsConstructor
public class InventoryAPI {
    private final InventoryBO inventoryBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Inventory Health Good";
    }

    @GetMapping
    public ResponseEntity getAllAvailableItems(){
        try {
            List<InventoryDTO> allAvailableItems = inventoryBO.getAllAvailableItems();
//            allAvailableItems.forEach(item -> {
//                item.getItem().setInventoryItems(new ArrayList<>());
//            });
            return ResponseEntity.ok(allAvailableItems);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
