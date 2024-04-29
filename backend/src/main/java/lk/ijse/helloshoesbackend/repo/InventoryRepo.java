package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryEntity, String> {
}
