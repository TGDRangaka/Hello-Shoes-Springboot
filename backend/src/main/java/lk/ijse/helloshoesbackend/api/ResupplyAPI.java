package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.ResupplyBO;
import lk.ijse.helloshoesbackend.dto.ResupplyDTO;
import lk.ijse.helloshoesbackend.entity.ResupplyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resupply")
@CrossOrigin
@RequiredArgsConstructor
public class ResupplyAPI {
    private final ResupplyBO resupplyBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Resupply Health Good";
    }

    @GetMapping
    public ResponseEntity getAllResupplies(){
        try {
            return ResponseEntity.ok(resupplyBO.getAllResupplies());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity saveResupply(@RequestBody ResupplyDTO resupplyDTO){
        try {
            boolean isSaved = resupplyBO.saveResupply(resupplyDTO);
            return ResponseEntity.ok(isSaved);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
