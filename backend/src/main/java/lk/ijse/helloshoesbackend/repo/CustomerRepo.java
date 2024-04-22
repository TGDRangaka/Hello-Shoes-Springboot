package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
}
