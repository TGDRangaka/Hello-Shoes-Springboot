package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryEntity, String> {
    List<InventoryEntity> findByStatus(String status);

    @Query("SELECT i.colors FROM InventoryEntity i WHERE i.item.itemCode = :itemCode GROUP BY i.colors")
    String[] getAvailableColorsById(@Param("itemCode") String itemCode);
}
