package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepo extends JpaRepository<AlertEntity, String> {
}
