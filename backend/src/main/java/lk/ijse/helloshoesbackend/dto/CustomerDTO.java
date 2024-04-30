package lk.ijse.helloshoesbackend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CustomerDTO {
    private String customerCode;
    private String name;
    private Gender gender;
    private Date joinedDateAsLoyalty;
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
