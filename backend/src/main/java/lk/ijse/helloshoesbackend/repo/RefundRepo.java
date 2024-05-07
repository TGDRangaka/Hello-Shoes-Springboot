package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.RefundEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepo extends JpaRepository<RefundEntity, String> {
    List<RefundEntity> findAllBySaleItem(SaleItemEntity saleItem);
}
