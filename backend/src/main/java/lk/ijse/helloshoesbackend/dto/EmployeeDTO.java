package lk.ijse.helloshoesbackend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lk.ijse.helloshoesbackend.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String employeeCode;

    @NotEmpty(message = "name should not be empty")
    private String name;
    @NotEmpty(message = "profilePic should not be empty")
    private String profilePic;
    private Gender gender;
    @NotEmpty(message = "status should not be empty")
    private String status;
    @NotEmpty(message = "designation should not be empty")
    private String designation;
    @NotEmpty(message = "dob should not be empty")
    private LocalDate dob;
    @NotEmpty(message = "joined date should not be empty")
    private LocalDate joinedDate;
    @NotEmpty(message = "branch should not be empty")
    private String branch;
    @NotEmpty
    private String addressNo;
    @NotEmpty
    private String addressLane;
    @NotEmpty
    private String addressCity;
    private String addressState;
    private String postalCode;
    @Email(message = "must be a valid email address")
    @NotEmpty(message = "email should not be empty")
    private String email;
    @NotEmpty(message = "phone should not be empty")
    @Size(min = 10, message = "phone must be at least 10 numbers")
    private String phone;
    @NotEmpty(message = "guardian should not be empty")
    private String guardianOrNominatedPerson;
    @NotEmpty(message = "emergence contace should not be empty")
    private String emergencyContact;

    public EmployeeDTO(String name){
        this.name = name;
    }
}
