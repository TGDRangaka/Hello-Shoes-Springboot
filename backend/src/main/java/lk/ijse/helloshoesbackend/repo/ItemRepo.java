package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<ItemEntity, String> {
}
