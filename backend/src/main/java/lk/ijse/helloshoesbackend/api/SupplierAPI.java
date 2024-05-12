package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.helloshoesbackend.bo.SupplierBO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier")
@CrossOrigin
@RequiredArgsConstructor
public class SupplierAPI {
    private final SupplierBO supplierBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Supplier Health Good";
    }

    @GetMapping
    public ResponseEntity getSuppliers(){
        try {
            return ResponseEntity.ok(supplierBO.getSuppliers());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO dto){
        return ResponseEntity.ok(supplierBO.saveSupplier(dto));
    }

    @PutMapping("/{supplierCode}")
    @RolesAllowed("ADMIN")
    public ResponseEntity updateSupplier(@RequestBody SupplierDTO dto, @PathVariable String supplierCode){
        try {
            supplierBO.updateSupplier(dto, supplierCode);
            return ResponseEntity.accepted().body("Success");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
