package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.helloshoesbackend.bo.ItemBO;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin
@RequiredArgsConstructor
public class ItemAPI {
    private final ItemBO itemBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Item Health Good";
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Boolean> saveItem(@RequestBody ItemDTO dto){
        try{
            return ResponseEntity.ok(itemBO.saveItem(dto));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(false);
        }
    }
}
