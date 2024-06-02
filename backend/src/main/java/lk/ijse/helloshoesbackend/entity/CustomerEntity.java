package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String customerCode;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;  // MALE FEMALE
    private LocalDate joinedDateAsLoyalty;
    @Enumerated(EnumType.STRING)
    private CustomerLevel level; //NEW BRONZE SILVER GOLD
    private Integer totalPoints; //NEW(0-49) BRONZE(50-99) SILVER(100-199) GOLD(200+)
    private LocalDate dob;
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    @Column(unique = true)
    private String email;
    private String phone;
    private LocalDateTime recentPurchaseDateTime;
}
