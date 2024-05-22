package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.AlertDTO;
import lk.ijse.helloshoesbackend.dto.projection.DailyProfitProjection;
import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection;
import lk.ijse.helloshoesbackend.dto.projection.SaleItemProjection;
import lk.ijse.helloshoesbackend.repo.AlertRepo;
import lk.ijse.helloshoesbackend.repo.RefundRepo;
import lk.ijse.helloshoesbackend.repo.SaleItemRepo;
import lk.ijse.helloshoesbackend.repo.SaleRepo;
import lk.ijse.helloshoesbackend.service.AdminPanelService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
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
    private final AlertRepo alertRepo;
    private final RefundRepo refundRepo;


    @Override
    public int getTotalSalesCount(LocalDate date) {
        Integer integer = saleRepo.countByOrderDate(date);
        return integer == null? 0 : integer;
    }

    @Override
    public Double getTotalProfits(LocalDate date) {
        List<SaleItemProjection> sales = saleItemRepo.getTotalProfit(date);
//        Double totalRefunds = refundRepo.getTotalRefundByDate(date);
        Double totalProfit = 0.0;
        for (SaleItemProjection sale : sales) {
            totalProfit += sale.getQty() * sale.getExpectedProfit();
        }
//        return totalProfit - (totalRefunds == null ? 0 : totalRefunds);
        return totalProfit;
    }

    @Override
    public int getTotalSoldProductsCount(LocalDate date) {
        Integer totalQtySold = saleItemRepo.getTotalQtySold(date);
        return totalQtySold == null? 0 : totalQtySold;
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
    public List<DailyProfitProjection> getDailyTotalProfits(LocalDate date){
        List<DailyProfitProjection> dailyTotProfits = new ArrayList<>();
        for(int i = 6; i >= 0; i--){
            dailyTotProfits.add(new DailyProfitProjection(date.minusDays(i), getTotalProfits(date.minusDays(i))));
        }
        return dailyTotProfits;
    }

    @Override
    public List<Object[]> getStocksCounts(LocalDate date) {
        return null;
    }

    @Override
    public List<MostSoldItemProjection> getMostSoldItems(LocalDate date) {
        return saleItemRepo.findMostSoldItems(date);
    }

    @Override
    public List<AlertDTO> saveAlert(AlertDTO alertDTO){
        alertDTO.setId(UtilMatter.generateUUID());
        alertRepo.save(Conversion.toAlertEntity(alertDTO));
        return Conversion.toAlertDTOList(alertRepo.findAll());
    }

    @Override
    public List<AlertDTO> getAllAlerts() {
        return Conversion.toAlertDTOList(alertRepo.findAll());
    }
}
