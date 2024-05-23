package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.AdminPanelBO;
import lk.ijse.helloshoesbackend.dto.AlertDTO;
import lk.ijse.helloshoesbackend.service.AdminPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/admin-panel")
@CrossOrigin
@RequiredArgsConstructor
public class AdminPanelAPI {
    private final AdminPanelBO adminPanelBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Admin Panel Health Good";
    }

    @GetMapping("/{date:\\d{4}-\\d{2}-\\d{2}}")
    public ResponseEntity getAdminPanelData(@PathVariable String date){
        try {
            date = date.replaceAll("(\\b\\d\\b)", "0$1");
            return ResponseEntity.accepted().body(adminPanelBO.getAdminPanelDate(LocalDate.parse(date)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/alert")
    public ResponseEntity recordAlert(@RequestBody AlertDTO alertDTO){
        try{
            return ResponseEntity.ok(adminPanelBO.recordAlert(alertDTO));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/alert")
    public ResponseEntity getAllAlerts(){
        try{
            return ResponseEntity.ok(adminPanelBO.getAllAlerts());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
