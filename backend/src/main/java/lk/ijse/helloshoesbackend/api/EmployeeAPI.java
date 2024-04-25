package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class EmployeeAPI {

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
    @PostMapping
    public ResponseEntity<JwtAuthResponse> signUp(@RequestBody SignUp signUp){
        return ResponseEntity.accepted().body(authenticationBO.signUp(signUp));
    }
}
