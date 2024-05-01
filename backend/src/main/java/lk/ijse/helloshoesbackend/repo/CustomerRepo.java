package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByNameAndEmail(String name, String email);
}
