package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class SupplierAPI {
    private final SupplierService supplierService;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Supplier health endpoint called");
        return "Supplier Health Good";
    }

    @GetMapping
    public ResponseEntity getSuppliers(){
        log.info("get all suppliers endpoint called");
        return ResponseEntity.ok(supplierService.getSuppliers());
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<SupplierDTO> saveSupplier(@Valid @RequestBody SupplierDTO dto){
        log.info("save supplier endpoint called");
        return ResponseEntity.ok(supplierService.save(dto));
    }

    @PutMapping("/{supplierCode}")
    @RolesAllowed("ADMIN")
    public ResponseEntity updateSupplier(@Valid @RequestBody SupplierDTO dto, @PathVariable String supplierCode){
        log.info("update supplier endpoint called");
        supplierService.update(dto, supplierCode);
        return ResponseEntity.accepted().body("Success");
    }
}
