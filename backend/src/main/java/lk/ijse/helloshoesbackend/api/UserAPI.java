package lk.ijse.helloshoesbackend.api;

import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.exception.DataDuplicationException;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserAPI {

    private final AuthenticationBO authenticationBO;

    @GetMapping("/health")
    public String healthCheck() {
        log.info("Health check endpoint called");
        return "User Health Good";
    }

    // SignIn
    @PutMapping
    public ResponseEntity<JwtAuthResponse> signIn(@Valid @RequestBody SignIn signIn) {
        log.info("SignIn request received for email: {}", signIn.getEmail());
        try {
            return ResponseEntity.accepted().body(authenticationBO.signIn(signIn));
        } catch (UsernameNotFoundException e) {
            log.warn("Username not found error during sign in: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during sign in", e);
            throw e;
        }
    }

    // SignUp
    @PostMapping
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody SignUp signUp) {
        log.info("SignUp request received for email: {}", signUp.getEmail());
        try {
            return ResponseEntity.accepted().body(authenticationBO.signUp(signUp));
        } catch (DataDuplicationException e) {
            log.warn("Data duplication error during sign up: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during sign up", e);
            throw e;
        }
    }
}
