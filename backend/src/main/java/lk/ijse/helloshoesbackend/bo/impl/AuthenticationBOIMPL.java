package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.entity.enums.UserRole;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AuthenticationBOIMPL implements AuthenticationBO {

    private final AuthenticationService authenticationService;
    private final EmployeeService employeeService;

    public JwtAuthResponse signIn(SignIn signIn){
//        for(int i = 0; i < 10; i++){
//            signIn.setEmail(new String(Base64.getDecoder().decode(signIn.getEmail())));
//            signIn.setPassword(new String(Base64.getDecoder().decode(signIn.getPassword())));
//        }
        JwtAuthResponse jwtAuthResponse = authenticationService.signIn(new ModelMapper().map(signIn, UserDTO.class));
        jwtAuthResponse.getUser().setPassword(null);
        return jwtAuthResponse;
    }

    public JwtAuthResponse signUp(SignUp signUp){
        UserDTO userDTO = new ModelMapper().map(signUp, UserDTO.class);

//        Get Employee if exist
        EmployeeDTO employee = employeeService.getEmployee(userDTO.getEmail());

//        Set Role
        if(employee.getDesignation().toLowerCase().equals("manager")){
            userDTO.setRole(UserRole.ADMIN);
        }else{
            userDTO.setRole(UserRole.USER);
        }

//        Ser relationship
        userDTO.setEmployee(employee);

        JwtAuthResponse jwtAuthResponse = authenticationService.signUp(userDTO);
        jwtAuthResponse.getUser().setPassword(null);
        return jwtAuthResponse;
    }
}
