package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity, String> {
    Integer countByOrderDate(LocalDate date);
    List<SaleEntity> findAllByOrderDate(LocalDate date);
    @Query("SELECT NEW lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection(s.orderDate, COUNT(s.orderId)) FROM SaleEntity s " +
            "WHERE s.orderDate >= :from AND s.orderDate <= :to " +
            "GROUP BY s.orderDate " +
            "ORDER BY s.orderDate ASC")
    List<DailySalesProjection> findDailyTotalSales(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("SELECT si FROM SaleEntity s " +
            "INNER JOIN SaleItemEntity si ON si.saleItemId.sale.orderId = s.orderId " +
            "WHERE s.orderId = :orderId")
    List<SaleItemEntity> getSaleItemsByOrderId(@Param("orderId") String orderId);
}
