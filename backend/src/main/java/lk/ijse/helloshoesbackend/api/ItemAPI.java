package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ItemAPI {
    private final ItemService itemService;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Item Health Check endpoint called");
        return "Item Health Good";
    }

    @GetMapping
    public ResponseEntity getAllItems(){
        log.info("Get All Items endpoint called");
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Boolean> saveItem(@Valid @RequestBody ItemDTO dto){
        log.info("Save Item endpoint called");
        return ResponseEntity.ok(itemService.save(dto));
    }

    @PutMapping("/{itemCode}")
    @RolesAllowed("ADMIN")
    public ResponseEntity updateItem(@Valid @RequestBody ItemDTO dto, @PathVariable String itemCode){
        log.info("Update Item endpoint called");
        itemService.update(dto, itemCode);
        return ResponseEntity.ok("Success");
    }
}
