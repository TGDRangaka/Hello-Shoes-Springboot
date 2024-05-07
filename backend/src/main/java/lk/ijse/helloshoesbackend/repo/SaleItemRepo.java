package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection;
import lk.ijse.helloshoesbackend.dto.projection.SaleItemProjection;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleItemRepo extends JpaRepository<SaleItemEntity, SaleItemId> {
    @Query("SELECT NEW lk.ijse.helloshoesbackend.dto.projection.SaleItemProjection(si.qty, item.expectedProfit) FROM SaleItemEntity si " +
            "INNER JOIN InventoryEntity i ON i.inventoryCode = si.saleItemId.item.inventoryCode " +
            "INNER JOIN ItemEntity item ON item.itemCode = i.item.itemCode " +
            "INNER JOIN SaleEntity s ON s.orderId = si.saleItemId.sale.orderId WHERE s.orderDate = :date")
    List<SaleItemProjection> getTotalProfit(@Param("date") LocalDate orderDate);

    @Query("SELECT SUM(si.qty) FROM SaleItemEntity si " +
            "INNER JOIN SaleEntity s ON s.orderId = si.saleItemId.sale.orderId WHERE s.orderDate = :date")
    Integer getTotalQtySold(@Param("date") LocalDate orderDate);

    @Query("SELECT NEW lk.ijse.helloshoesbackend.dto.projection.MostSoldItemProjection(si.qty, i.inventoryCode, i.item.description, i.itemImage.image) FROM SaleItemEntity si " +
            "INNER JOIN InventoryEntity i ON i.inventoryCode = si.saleItemId.item.inventoryCode " +
            "INNER JOIN SaleEntity s ON s.orderId = si.saleItemId.sale.orderId " +
            "WHERE s.orderDate = :date " +
            "GROUP BY i.inventoryCode " +
            "ORDER BY SUM(si.qty) DESC") // You may want to order by the sum of quantities to find the most sold items
    List<MostSoldItemProjection> findMostSoldItems(@Param("date") LocalDate orderDate);
}
