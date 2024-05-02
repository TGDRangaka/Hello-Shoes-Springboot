package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    JwtAuthResponse signIn(UserDTO dto);
    JwtAuthResponse signUp(UserDTO dto);
    JwtAuthResponse refreshToken(String accessToken);
}
