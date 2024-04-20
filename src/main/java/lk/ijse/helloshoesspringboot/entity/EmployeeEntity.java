package lk.ijse.helloshoesspringboot.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesspringboot.entity.enums.Gender;
import lk.ijse.helloshoesspringboot.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    private String employeeCode;
    private String name;
    @Column(columnDefinition="LONGTEXT")
    private String profilePic;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String status;
    private String designation;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Date dob;
    private Date joinedDate;
    private String branch;
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    @Column(unique = true)
    private String email;
    private String phone;
    private String password;
    private String guardianOrNominatedPerson;
    private String emergencyContact;
}
