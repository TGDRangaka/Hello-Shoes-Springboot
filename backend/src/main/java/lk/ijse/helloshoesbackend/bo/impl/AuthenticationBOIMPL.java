package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthenticationBOIMPL implements AuthenticationBO {

    private final AuthenticationService authenticationService;

    public JwtAuthResponse signIn(SignIn signIn){
        for(int i = 0; i < 10; i++){
            signIn.setEmail(new String(Base64.getDecoder().decode(signIn.getEmail())));
            signIn.setPassword(new String(Base64.getDecoder().decode(signIn.getPassword())));
        }
        return authenticationService.signIn(signIn);
    }

    public JwtAuthResponse signUp(SignUp signUp){
        return authenticationService.signUp(signUp);
    }
}
