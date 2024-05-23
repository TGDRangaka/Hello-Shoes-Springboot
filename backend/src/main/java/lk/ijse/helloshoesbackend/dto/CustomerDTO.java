package lk.ijse.helloshoesbackend.dto;

import jakarta.validation.constraints.*;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String customerCode;
    @NotEmpty(message = "name shouldn't be empty")
    @NotNull(message = "name shouldn't be null")
    private String name;
    private Gender gender;
    private LocalDate joinedDateAsLoyalty;
    private CustomerLevel level;
    private Integer totalPoints;
    private LocalDate dob;
    @NotNull(message = "addressNo shouldn't be null")
    private String addressNo;
    @NotNull(message = "addressLane shouldn't be null")
    private String addressLane;
    @NotNull(message = "addressCity shouldn't be null")
    private String addressCity;
    private String addressState;
    @NotNull(message = "postalCode shouldn't be null")
    private String postalCode;
    @NotNull(message = "email shouldn't be null")
    @Email(message = "must be valid email address")
    private String email;
    @NotNull(message = "phone shouldn't be null")
    @Size(min = 10, message = "must be 10 numbers")
    private String phone;
    private LocalDateTime recentPurchaseDateTime;
}
