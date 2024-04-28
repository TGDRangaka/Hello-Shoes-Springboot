package lk.ijse.helloshoesbackend.api;

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

    @PostMapping
    public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO dto){
        return ResponseEntity.ok(supplierBO.saveSupplier(dto));
    }
}
