package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity, String> {
}
