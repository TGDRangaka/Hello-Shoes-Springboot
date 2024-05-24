package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.SaleBO;
import lk.ijse.helloshoesbackend.dto.RefundDTO;
import lk.ijse.helloshoesbackend.service.RefundService;
import lk.ijse.helloshoesbackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/refund")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class RefundAPI {
    private final SaleBO saleBO;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Refund health check endpoint called");
        return "Refund Health Good";
    }

    @GetMapping
    public ResponseEntity getAllRefunds(){
        log.info("Get all refunds endpoint called");
        return ResponseEntity.ok(saleBO.getAllRefunds());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity getSaleItemsByOrderId(@PathVariable String orderId){
        log.info("Get sale items by order id endpoint called");
        return ResponseEntity.ok(saleBO.getSaleItems(orderId));
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity saveRefund(@Valid @RequestBody List<RefundDTO> refunds, Authentication authentication){
        log.info("Save refund endpoint called");
        saleBO.refundSaleItems(refunds, authentication.getName());
        return ResponseEntity.ok("Ok");
    }
}
