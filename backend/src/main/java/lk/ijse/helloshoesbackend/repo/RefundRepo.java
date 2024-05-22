package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.RefundEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RefundRepo extends JpaRepository<RefundEntity, String> {
    List<RefundEntity> findAllBySaleItem(SaleItemEntity saleItem);

    @Query("SELECT SUM(r.refundTotal) FROM RefundEntity r where r.refundDate = :date")
    Double getTotalRefundByDate(@Param("date") LocalDate date);

}
