package lk.ijse.helloshoesbackend.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySalesProjection {
    private LocalDate date;
    private long totalSales;
}
