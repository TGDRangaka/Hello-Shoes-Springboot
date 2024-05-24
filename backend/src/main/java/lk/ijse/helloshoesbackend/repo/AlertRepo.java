package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepo extends JpaRepository<AlertEntity, String> {
    List<AlertEntity> findAllByOrderByDateDescTimeDesc();
}
