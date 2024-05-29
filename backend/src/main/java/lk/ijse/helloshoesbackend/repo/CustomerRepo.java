package lk.ijse.helloshoesbackend.repo;

import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByNameAndEmail(String name, String email);

    @Query("SELECT c FROM CustomerEntity c WHERE FUNCTION('MONTH', c.dob) = :month AND FUNCTION('DAY', c.dob) = :day")
    List<CustomerEntity> findByBirthdayMonthAndDay(@Param("month") int month, @Param("day") int day);
}
