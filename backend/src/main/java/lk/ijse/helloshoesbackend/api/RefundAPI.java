package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.helloshoesbackend.dto.RefundDTO;
import lk.ijse.helloshoesbackend.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/refund")
@CrossOrigin
@RequiredArgsConstructor
public class RefundAPI {
    private final RefundService refundService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Refund Health Good";
    }

    @GetMapping
    public ResponseEntity getAllRefunds(){
        try {
            return ResponseEntity.ok(refundService.getAllRefunds());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity saveRefund(@RequestBody RefundDTO refundDTO){
        try {
            refundService.refundSaleItem(refundDTO);
            return ResponseEntity.ok("Ok");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
