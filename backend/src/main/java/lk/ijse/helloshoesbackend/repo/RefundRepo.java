package lk.ijse.helloshoesbackend.repo;

import jdk.jfr.Registered;
import lk.ijse.helloshoesbackend.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepo extends JpaRepository<RefundEntity, String> {
}
