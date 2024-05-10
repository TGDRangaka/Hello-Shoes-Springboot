package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.helloshoesbackend.bo.EmployeeBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/employee")
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeAPI {

    private final EmployeeBO employeeBO;

    @GetMapping
    public ResponseEntity getAllEmployees(){
        try {
            return ResponseEntity.ok(employeeBO.getAllEmployees());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<EmployeeDTO> getEmployee(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(employeeBO.getEmployee(token));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed("ADMIN")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity saveEmployee(
            @RequestPart("name") String name,
            @RequestPart("profilePic") MultipartFile profilePic,
            @RequestPart("gender") String gender,
            @RequestPart("status") String status,
            @RequestPart("designation") String designation,
            @RequestPart("dob") String dob,
            @RequestPart("joinedDate") String joinedDate,
            @RequestPart("branch") String branch,
            @RequestPart("addressNo") String addressNo,
            @RequestPart("addressLane") String addressLane,
            @RequestPart("addressCity") String addressCity,
            @RequestParam("addressState") String addressState,
            @RequestPart("addressPostalCode") String postalCode,
            @RequestPart("email") String email,
            @RequestPart("phone") String phone,
            @RequestPart("guardian") String guardianOrNominatedPerson,
            @RequestPart("emergencyNumber") String emergencyContact
    ){
        try {

            EmployeeDTO employee = new EmployeeDTO();
            employee.setName(name);
            employee.setProfilePic(UtilMatter.convertBase64(profilePic.getBytes()));
            employee.setGender(Gender.valueOf(gender));
            employee.setStatus(status);
            employee.setDesignation(designation);
            employee.setDob(LocalDate.parse(dob));
            employee.setJoinedDate(LocalDate.parse(joinedDate));
            employee.setBranch(branch);
            employee.setAddressNo(addressNo);
            employee.setAddressLane(addressLane);
            employee.setAddressCity(addressCity);
            employee.setAddressState(addressState);
            employee.setPostalCode(postalCode);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setGuardianOrNominatedPerson(guardianOrNominatedPerson);
            employee.setEmergencyContact(emergencyContact);
            return ResponseEntity.ok(employeeBO.saveEmployee(employee));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "/{employeeCode}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed("ADMIN")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity updateEmployee(
            @RequestPart("name") String name,
            @RequestPart("profilePic") MultipartFile profilePic,
            @RequestPart("gender") String gender,
            @RequestPart("status") String status,
            @RequestPart("designation") String designation,
            @RequestPart("dob") String dob,
            @RequestPart("joinedDate") String joinedDate,
            @RequestPart("branch") String branch,
            @RequestPart("addressNo") String addressNo,
            @RequestPart("addressLane") String addressLane,
            @RequestPart("addressCity") String addressCity,
            @RequestParam("addressState") String addressState,
            @RequestPart("addressPostalCode") String postalCode,
            @RequestPart("email") String email,
            @RequestPart("phone") String phone,
            @RequestPart("guardian") String guardianOrNominatedPerson,
            @RequestPart("emergencyNumber") String emergencyContact,
            @PathVariable("employeeCode") String employeeCode
    ){
        try {

            EmployeeDTO employee = new EmployeeDTO();
            employee.setName(name);
            employee.setProfilePic(UtilMatter.convertBase64(profilePic.getBytes()));
            employee.setGender(Gender.valueOf(gender));
            employee.setStatus(status);
            employee.setDesignation(designation);
            employee.setDob(LocalDate.parse(dob));
            employee.setJoinedDate(LocalDate.parse(joinedDate));
            employee.setBranch(branch);
            employee.setAddressNo(addressNo);
            employee.setAddressLane(addressLane);
            employee.setAddressCity(addressCity);
            employee.setAddressState(addressState);
            employee.setPostalCode(postalCode);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setGuardianOrNominatedPerson(guardianOrNominatedPerson);
            employee.setEmergencyContact(emergencyContact);

            return ResponseEntity.ok().body(employeeBO.updateEmployee(employee,employeeCode));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
