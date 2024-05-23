package lk.ijse.helloshoesbackend.api;

import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.exception.DataDuplicationException;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@Validated
public class UserAPI {

    private final AuthenticationBO authenticationBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "User Health Good";
    }

//    SignIn
    @PutMapping
    public ResponseEntity signIn(@Valid @RequestBody SignIn signIn){
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
