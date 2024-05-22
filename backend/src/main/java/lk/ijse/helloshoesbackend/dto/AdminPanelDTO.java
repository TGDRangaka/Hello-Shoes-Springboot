package lk.ijse.helloshoesbackend.dto;

import lk.ijse.helloshoesbackend.dto.projection.DailyProfitProjection;
import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPanelDTO {
    private int totalSalesCount;
    private double totalProfitAmount;
    private int totalQtySold;
    private int tot;
    private List<MostSoldItemProjection> mostSoldItems;
    private List<DailySalesProjection> dailySales;
    private List<DailyProfitProjection> dailyProfits;
}
