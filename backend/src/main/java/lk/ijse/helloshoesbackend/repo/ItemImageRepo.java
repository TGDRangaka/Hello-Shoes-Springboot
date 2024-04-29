package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.ItemImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepo extends JpaRepository<ItemImageEntity, String> {
}
