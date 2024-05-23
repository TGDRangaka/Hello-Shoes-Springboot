package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
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

    private final SaleBO saleBO;

    @GetMapping("/health")
    public String healthCheck() {
        log.info("Health check endpoint called");
        return "Sale Health Good";
    }

    @GetMapping
    public ResponseEntity getAllSales() {
        log.info("Fetching all sales");
        try {
            return ResponseEntity.ok(saleBO.getSales());
        } catch (Exception e) {
            log.error("Error occurred while fetching sales", e);
            return ResponseEntity.status(500).body("An error occurred while fetching sales");
        }
    }

    @PostMapping
    public ResponseEntity saveSale(@RequestBody SaleDTO saleDTO, Authentication authentication){
        String user = authentication.getName();
        log.info("Saving sale for user: {}", user);
        log.debug("SaleDTO: {}", saleDTO);
        try {
            return ResponseEntity.ok(saleBO.saveSale(saleDTO, user));
        } catch (InvalidDataException e) {
            log.warn("Invalid data provided for saving sale", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while saving sale", e);
            return ResponseEntity.status(500).body("An unexpected error occurred while saving the sale");
        }
    }
}
