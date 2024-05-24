package lk.ijse.helloshoesbackend.api;

import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.ResupplyBO;
import lk.ijse.helloshoesbackend.dto.ResupplyDTO;
import lk.ijse.helloshoesbackend.entity.ResupplyEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resupply")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ResupplyAPI {
    private final ResupplyBO resupplyBO;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Resupply health check endpoint called");
        return "Resupply Health Good";
    }

    @GetMapping
    public ResponseEntity getAllResupplies(){
        log.info("Get all resupplies endpoint called");
        return ResponseEntity.ok(resupplyBO.getAllResupplies());
    }

    @PostMapping
    public ResponseEntity saveResupply(@Valid @RequestBody ResupplyDTO resupplyDTO){
        log.info("Save resupply endpoint called");
        boolean isSaved = resupplyBO.saveResupply(resupplyDTO);
        return ResponseEntity.ok(isSaved);
    }
}
