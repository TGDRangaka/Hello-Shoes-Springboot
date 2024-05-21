package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.AdminPanelBO;
import lk.ijse.helloshoesbackend.dto.AdminPanelDTO;
import lk.ijse.helloshoesbackend.dto.AlertDTO;
import lk.ijse.helloshoesbackend.service.AdminPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class AdminPanelBOIMPL implements AdminPanelBO{
    private final AdminPanelService adminPanelService;

    @Override
    public AdminPanelDTO getAdminPanelDate(LocalDate date) {
        return new AdminPanelDTO(
                adminPanelService.getTotalSalesCount(date),
                adminPanelService.getTotalProfits(date),
                adminPanelService.getTotalSoldProductsCount(date),
                0,
                adminPanelService.getMostSoldItems(date),
                adminPanelService.getDailyTotalSales(date),
                null
        );
    }

    @Override
    public List<AlertDTO> recordAlert(AlertDTO alert){
        alert.setDate(LocalDate.now());
        alert.setTime(LocalTime.now());

        return adminPanelService.saveAlert(alert);
    }

    @Override
    public List<AlertDTO> getAllAlerts(){
        return adminPanelService.getAllAlerts();
    }
}
