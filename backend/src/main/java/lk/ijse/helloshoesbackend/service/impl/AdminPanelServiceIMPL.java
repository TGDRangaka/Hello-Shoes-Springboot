package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection;
import lk.ijse.helloshoesbackend.dto.projection.SaleItemProjection;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.repo.SaleItemRepo;
import lk.ijse.helloshoesbackend.repo.SaleRepo;
import lk.ijse.helloshoesbackend.service.AdminPanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPanelServiceIMPL implements AdminPanelService {
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;


    @Override
    public int getTotalSalesCount(LocalDate date) {
        return saleRepo.countByOrderDate(date);
    }

    @Override
    public Double getTotalProfits(LocalDate date) {
        List<SaleItemProjection> sales = saleItemRepo.getTotalProfit(date);
        Double totalProfit = 0.0;
        for (SaleItemProjection sale : sales) {
            totalProfit += sale.getQty() * sale.getExpectedProfit();
        }
        return totalProfit;
    }

    @Override
    public int getTotalSoldProductsCount(LocalDate date) {
        return saleItemRepo.getTotalQtySold(date);
    }

    @Override
    public List<DailySalesProjection> getDailyTotalSales(LocalDate date) {
        List<DailySalesProjection> dailyTotSales = new ArrayList<>();
        for(int i = 6; i >= 0; i--){
            dailyTotSales.add(new DailySalesProjection(date.minusDays(i), saleRepo.countByOrderDate(date.minusDays(i))));
        }
        return dailyTotSales;
    }

    @Override
    public List<Object[]> getStocksCounts(LocalDate date) {
        return null;
    }

    @Override
    public List<MostSoldItemProjection> getMostSoldItems(LocalDate date) {
        return saleItemRepo.findMostSoldItems(date);
    }
}
