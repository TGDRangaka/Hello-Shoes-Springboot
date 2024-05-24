package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.AdminPanelDTO;
import lk.ijse.helloshoesbackend.dto.AlertDTO;
import lk.ijse.helloshoesbackend.dto.projection.DailyProfitProjection;
import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection;

import java.time.LocalDate;
import java.util.List;

public interface AdminPanelService {
    AdminPanelDTO getAdminPanelData(LocalDate date);

    int getTotalSalesCount(LocalDate date);
    Double getTotalProfits(LocalDate date);
    int getTotalSoldProductsCount(LocalDate date);
    List<DailySalesProjection> getDailyTotalSales(LocalDate date);

    List<DailyProfitProjection> getDailyTotalProfits(LocalDate date);

    List<Object[]> getStocksCounts(LocalDate date);
    List<MostSoldItemProjection> getMostSoldItems(LocalDate date);

    List<AlertDTO> saveAlert(AlertDTO alertDTO);
}
