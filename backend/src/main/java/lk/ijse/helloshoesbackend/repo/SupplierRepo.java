package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepo extends JpaRepository<SupplierEntity, String> {
    Optional<SupplierEntity> findByName(String name);
}
