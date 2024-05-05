package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection;

import java.time.LocalDate;
import java.util.List;

public interface AdminPanelService {
    int getTotalSalesCount(LocalDate date);
    Double getTotalProfits(LocalDate date);
    int getTotalSoldProductsCount(LocalDate date);
    List<DailySalesProjection> getDailyTotalSales(LocalDate date);
    List<Object[]> getStocksCounts(LocalDate date);
    List<MostSoldItemProjection> getMostSoldItems(LocalDate date);
}
