package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/employee")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class EmployeeAPI {
    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity getAllEmployees(){
        log.info("Get all employees endpoint called");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/validate/{email}/{password}")
    public ResponseEntity isValidCredentials(@PathVariable String email, @PathVariable String password, Authentication authentication){
        log.info("isValidCredentials is called");
        return ResponseEntity.ok(authenticationService.isCredentialsValid(email, password, authentication));
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
    ) throws IOException {
        log.info("saveEmployee save is called");
        @Valid
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
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
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
    ) throws IOException {
        log.info("Employee update is called");
        @Valid
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

        return ResponseEntity.ok().body(employeeService.updateEmployee(employee,employeeCode));
    }
}
