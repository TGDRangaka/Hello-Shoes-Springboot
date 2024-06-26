package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.EmployeeEntity;
import lk.ijse.helloshoesbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmployee(EmployeeEntity employee);
}
