package lk.ijse.helloshoesbackend.api;

import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sale")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j // Lombok annotation to generate a logger
public class SaleAPI {
    private final SaleService saleService;

    @GetMapping("/health")
    public String healthCheck() {
        log.info("Health check endpoint called");
        return "Sale Health Good";
    }

    @GetMapping
    public ResponseEntity getAllSales() {
        log.info("Get all sales endpoint called");
        return ResponseEntity.ok(saleService.getSales());
    }

    @PostMapping
    public ResponseEntity saveSale(@Valid @RequestBody SaleDTO saleDTO, Authentication authentication){
        String user = authentication.getName();
        log.info("Save sale endpoint called");
        return ResponseEntity.ok(saleService.save(saleDTO, user));
    }
}
