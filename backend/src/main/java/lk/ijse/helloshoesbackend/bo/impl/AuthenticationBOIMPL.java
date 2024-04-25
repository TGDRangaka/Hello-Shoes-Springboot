package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationBOIMPL implements AuthenticationBO {

    private final AuthenticationService authenticationService;

    public JwtAuthResponse signIn(SignIn signIn){
        return authenticationService.signIn(signIn);
    }

    public JwtAuthResponse signUp(SignUp signUp){
        return authenticationService.signUp(signUp);
    }
}
