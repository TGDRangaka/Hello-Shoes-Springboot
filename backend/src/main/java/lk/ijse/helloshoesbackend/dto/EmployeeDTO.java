package lk.ijse.helloshoesbackend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String name;
    private String profilePic;
    private Gender gender;
    private String status;
    private String designation;
    private LocalDate dob;
    private LocalDate joinedDate;
    private String branch;
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    private String email;
    private String phone;
    private String guardianOrNominatedPerson;
    private String emergencyContact;

    public EmployeeDTO(String name){
        this.name = name;
    }
}
