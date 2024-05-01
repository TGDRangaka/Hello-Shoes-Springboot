package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/sale")
@CrossOrigin
@RequiredArgsConstructor
public class SaleAPI {
    private final SaleBO saleBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Sale Health Good";
    }

    @PostMapping
    public ResponseEntity saveSale(@RequestBody SaleDTO saleDTO, Authentication authentication){
        try{
            String user = authentication.getName();
            return ResponseEntity.ok(saleBO.saveSale(saleDTO, user));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
