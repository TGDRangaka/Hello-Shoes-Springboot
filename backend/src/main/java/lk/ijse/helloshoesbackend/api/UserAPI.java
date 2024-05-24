package lk.ijse.helloshoesbackend.api;

import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserAPI {

    private final AuthenticationService authenticationService;

    @GetMapping("/health")
    public String healthCheck() {
        log.info("Health check endpoint called");
        return "User Health Good";
    }

    // SignIn
    @PutMapping
    public ResponseEntity signIn(@Valid @RequestBody SignIn signIn) {
        log.info("SignIn request received for email: {}", signIn.getEmail());
        return ResponseEntity.accepted().body(authenticationService.signIn(signIn));
    }

    // SignUp
    @PostMapping
    public ResponseEntity signUp(@Valid @RequestBody SignUp signUp) {
        log.info("SignUp request received for email: {}", signUp.getEmail());
        return ResponseEntity.accepted().body(authenticationService.signUp(signUp));
    }
}
