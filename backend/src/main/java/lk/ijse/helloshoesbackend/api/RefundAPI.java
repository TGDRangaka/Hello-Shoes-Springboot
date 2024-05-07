package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.RefundDTO;
import lk.ijse.helloshoesbackend.service.RefundService;
import lk.ijse.helloshoesbackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/refund")
@CrossOrigin
@RequiredArgsConstructor
public class RefundAPI {
    private final SaleBO saleBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Refund Health Good";
    }

    @GetMapping
    public ResponseEntity getAllRefunds(){
        try {
            return ResponseEntity.ok(saleBO.getAllRefunds());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity getSaleItemsByOrderId(@PathVariable String orderId){
        try{
            return ResponseEntity.ok(saleBO.getSaleItems(orderId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity saveRefund(@RequestBody List<RefundDTO> refunds, Authentication authentication){
        try {
            saleBO.refundSaleItems(refunds, authentication.getName());
            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
