package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.SupplierBO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
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
    private final SupplierBO supplierBO;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Supplier health endpoint called");
        return "Supplier Health Good";
    }

    @GetMapping
    public ResponseEntity getSuppliers(){
        log.info("get all suppliers endpoint called");
        return ResponseEntity.ok(supplierBO.getSuppliers());
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<SupplierDTO> saveSupplier(@Valid @RequestBody SupplierDTO dto){
        log.info("save supplier endpoint called");
        return ResponseEntity.ok(supplierBO.saveSupplier(dto));
    }

    @PutMapping("/{supplierCode}")
    @RolesAllowed("ADMIN")
    public ResponseEntity updateSupplier(@Valid @RequestBody SupplierDTO dto, @PathVariable String supplierCode){
        log.info("update supplier endpoint called");
        supplierBO.updateSupplier(dto, supplierCode);
        return ResponseEntity.accepted().body("Success");
    }
}
