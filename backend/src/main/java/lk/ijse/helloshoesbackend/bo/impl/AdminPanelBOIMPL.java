package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.AdminPanelBO;
import lk.ijse.helloshoesbackend.dto.AdminPanelDTO;
import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection;
import lk.ijse.helloshoesbackend.service.AdminPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
}
