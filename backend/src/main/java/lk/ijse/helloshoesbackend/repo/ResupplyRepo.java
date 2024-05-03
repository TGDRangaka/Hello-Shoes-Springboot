package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.ResupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResupplyRepo extends JpaRepository<ResupplyEntity, String> {
}
