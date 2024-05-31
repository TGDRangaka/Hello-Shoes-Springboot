package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.AdminPanelDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminPanelServiceIMPL implements AdminPanelService {
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;
    private final AlertRepo alertRepo;


    @Override
    public AdminPanelDTO getAdminPanelData(LocalDate date){
        log.info("Attempting to get admin panel data");
        AdminPanelDTO adminPanelDTO = new AdminPanelDTO(
                getTotalSalesCount(date),
                getTotalProfits(date),
                getTotalSoldProductsCount(date),
                0,
                getMostSoldItems(date),
                getDailyTotalSales(date),
                getDailyTotalProfits(date)
        );

        log.info("Returning admin panel data");
        return adminPanelDTO;
    }

    @Override
    public int getTotalSalesCount(LocalDate date) {
        log.info("Fetching total sales count for {}", date.toString());
        Integer integer = saleRepo.countByOrderDate(date);
        log.info("Fetched total sales count {}", integer);
        return integer == null? 0 : integer;
    }

    @Override
    public Double getTotalProfits(LocalDate date) {
        log.info("Fetching total profits for {}", date.toString());
        List<SaleItemProjection> sales = saleItemRepo.getTotalProfit(date);
//        Double totalRefunds = refundRepo.getTotalRefundByDate(date);
        Double totalProfit = 0.0;
        for (SaleItemProjection sale : sales) {
            totalProfit += sale.getQty() * sale.getExpectedProfit();
        }
//        return totalProfit - (totalRefunds == null ? 0 : totalRefunds);
        log.info("Fetched total profits amount {}", totalProfit);
        return totalProfit;
    }

    @Override
    public int getTotalSoldProductsCount(LocalDate date) {
        log.info("Fetching total sold products count for {}", date.toString());
        Integer totalQtySold = saleItemRepo.getTotalQtySold(date);
        log.info("Fetched total sold products count {}", totalQtySold);
        return totalQtySold == null? 0 : totalQtySold;
    }

    @Override
    public List<DailySalesProjection> getDailyTotalSales(LocalDate date) {
        log.info("Fetching daily total sales for {}", date.toString());
        List<DailySalesProjection> dailyTotSales = new ArrayList<>();
        for(int i = 6; i >= 0; i--){
            dailyTotSales.add(new DailySalesProjection(date.minusDays(i), saleRepo.countByOrderDate(date.minusDays(i))));
        }
        log.info("Fetched daily total sales for {}", date.toString());
        return dailyTotSales;
    }

    @Override
    public List<DailyProfitProjection> getDailyTotalProfits(LocalDate date){
        log.info("Fetching daily total profits for {}", date.toString());
        List<DailyProfitProjection> dailyTotProfits = new ArrayList<>();
        for(int i = 6; i >= 0; i--){
            dailyTotProfits.add(new DailyProfitProjection(date.minusDays(i), getTotalProfits(date.minusDays(i))));
        }
        log.info("Fetched daily total profits for {}", date.toString());
        return dailyTotProfits;
    }

    @Override
    public List<Object[]> getStocksCounts(LocalDate date) {
        return null;
    }

    @Override
    public List<MostSoldItemProjection> getMostSoldItems(LocalDate date) {
        log.info("Fetching most sold items for {}", date.toString());
        List<MostSoldItemProjection> mostSoldItems = new ArrayList<>();
        List<MostSoldItemProjection> allSoldItems = saleItemRepo.findMostSoldItems(date);

        // Add up to 3 items from allSoldItems to mostSoldItems
        for (int i = 0; i < 3 && i < allSoldItems.size(); i++) {
            mostSoldItems.add(allSoldItems.get(i));
        }

        // Sort by mostSoldItemProjection.qty
        mostSoldItems.sort(Comparator.comparingInt(MostSoldItemProjection::getQty).reversed());

        return mostSoldItems;
    }

    @Override
    public List<AlertDTO> saveAlert(AlertDTO alertDTO){
        log.info("Saving alert");

        alertDTO.setId(UtilMatter.generateUUID());
        alertDTO.setDate(LocalDate.now());
        alertDTO.setTime(LocalTime.now());

        alertRepo.save(Conversion.toAlertEntity(alertDTO));
        log.info("Saved alert and returning all alerts");
        return Conversion.toAlertDTOList(alertRepo.findAllByOrderByDateDescTimeDesc());
    }
}
