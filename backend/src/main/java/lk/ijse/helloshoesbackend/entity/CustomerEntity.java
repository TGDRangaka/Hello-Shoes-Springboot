package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
    private Gender gender;
    private Date joinedDateAsLoyalty;
    @Enumerated(EnumType.STRING)
    private CustomerLevel level;
    private Integer totalPoints;
    private Date dob;
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    private String email;
    private String phone;
    private Timestamp recentPurchaseDateTime;
}
