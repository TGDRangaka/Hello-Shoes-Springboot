package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.AdminPanelBO;
import lk.ijse.helloshoesbackend.dto.AlertDTO;
import lk.ijse.helloshoesbackend.service.AdminPanelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/admin-panel")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class AdminPanelAPI {
    private final AdminPanelBO adminPanelBO;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Admin Panel Health Check endpoint called");
        return "Admin Panel Health Good";
    }

    @GetMapping("/{date:\\d{4}-\\d{1,2}-\\d{1,2}}")
    public ResponseEntity getAdminPanelData(@PathVariable String date){
        log.info("Get Admin Panel Data endpoint called - Date: {}", date);
        date = date.replaceAll("(\\b\\d\\b)", "0$1");
        return ResponseEntity.accepted().body(adminPanelBO.getAdminPanelDate(LocalDate.parse(date)));
    }

    @PostMapping("/alert")
    public ResponseEntity recordAlert(@RequestBody AlertDTO alertDTO){
        log.info("Record Alert endpoint called - Alert: {}", alertDTO);
        return ResponseEntity.ok(adminPanelBO.recordAlert(alertDTO));
    }

}
