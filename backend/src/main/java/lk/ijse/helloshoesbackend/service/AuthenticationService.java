package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.exception.DataDuplicationException;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    JwtAuthResponse signIn(SignIn signIn);

    boolean isCredentialsValid(String email, String password);

    JwtAuthResponse signUp(SignUp signUp) ;
}
