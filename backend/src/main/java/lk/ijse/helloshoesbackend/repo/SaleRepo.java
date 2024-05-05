package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection;
import lk.ijse.helloshoesbackend.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity, String> {
    int countByOrderDate(LocalDate date);
    List<SaleEntity> findAllByOrderDate(LocalDate date);
    @Query("SELECT NEW lk.ijse.helloshoesbackend.dto.projection.DailySalesProjection(s.orderDate, COUNT(s.orderId)) FROM SaleEntity s " +
            "WHERE s.orderDate >= :from AND s.orderDate <= :to " +
            "GROUP BY s.orderDate " +
            "ORDER BY s.orderDate ASC")
    List<DailySalesProjection> findDailyTotalSales(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
