package lk.ijse.helloshoesbackend.api;

import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lk.ijse.helloshoesbackend.entity.enums.UserRole;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class UserAPI {

    private final AuthenticationBO authenticationBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Employee Health Good";
    }

//    SignIn
    @PutMapping
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signIn){
        return ResponseEntity.accepted().body(authenticationBO.signIn(signIn));
    }

//    SignUp
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(
            @RequestPart ("name") String name,
            @RequestPart ("profilePic") String profilePic,
            @RequestPart ("gender") String gender,
            @RequestPart ("status") String status,
            @RequestPart ("designation") String designation,
            @RequestPart ("role") String role,
            @RequestPart ("dob") String dob,
            @RequestPart ("joinedDate") String joinedDate,
            @RequestPart ("branch") String branch,
            @RequestPart ("addressNo") String addressNo,
            @RequestPart ("addressLane") String addressLane,
            @RequestPart ("addressCity") String addressCity,
            @RequestPart ("addressState") String addressState,
            @RequestPart ("postalCode") String postalCode,
            @RequestPart ("email") String email,
            @RequestPart ("phone") String phone,
            @RequestPart ("password") String password,
            @RequestPart ("guardianOrNominatedPerson") String guardianOrNominatedPerson,
            @RequestPart ("emergencyContact") String emergencyContact
    ) throws ParseException {
        SignUp signUp = new SignUp(
                name,
                UtilMatter.convertBase64(profilePic),
//                profilePic,
                Gender.valueOf(gender),
                status,
                designation,
                UserRole.valueOf(role),
                new SimpleDateFormat("yyyy-MM-dd").parse(dob.substring(0,10)),
                new SimpleDateFormat("yyyy-MM-dd").parse(joinedDate.substring(0,10)),
                branch,
                addressNo,
                addressLane,
                addressCity,
                addressState,
                postalCode,
                email,
                phone,
                password,
                guardianOrNominatedPerson,
                emergencyContact
        );
        return ResponseEntity.accepted().body(authenticationBO.signUp(signUp));
    }
}
