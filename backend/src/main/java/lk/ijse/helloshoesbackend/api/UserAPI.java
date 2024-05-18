package lk.ijse.helloshoesbackend.api;

import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.entity.enums.Gender;
import lk.ijse.helloshoesbackend.entity.enums.UserRole;
import lk.ijse.helloshoesbackend.exception.DataDuplicationException;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserAPI {

    private final AuthenticationBO authenticationBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "User Health Good";
    }

//    SignIn
    @PutMapping
    public ResponseEntity signIn(@RequestBody SignIn signIn){
        log.info("User sign in by email: " + signIn.getEmail());
        try{
            return ResponseEntity.accepted().body(authenticationBO.signIn(signIn));
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

//    SignUp
    @PostMapping
    public ResponseEntity signUp(@RequestBody SignUp signUp){
        log.info("User sign up by email: " + signUp.getEmail());
        try{
            return ResponseEntity.accepted().body(authenticationBO.signUp(signUp));
        }catch (DataDuplicationException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
