package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    JwtAuthResponse signIn(UserDTO dto);

    boolean isCredentialsValid(String email, String password);

    JwtAuthResponse signUp(UserDTO dto);
    JwtAuthResponse refreshToken(String accessToken);
}
