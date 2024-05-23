package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.AuthenticationBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.entity.enums.UserRole;
import lk.ijse.helloshoesbackend.exception.DataDuplicationException;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationBOIMPL implements AuthenticationBO {

    private final AuthenticationService authenticationService;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        log.info("Attempting to sign in user with email: {}", signIn.getEmail());
        JwtAuthResponse jwtAuthResponse = authenticationService.signIn(modelMapper.map(signIn, UserDTO.class));
        jwtAuthResponse.getUser().setPassword(null);
        return jwtAuthResponse;
    }

    @Override
    public JwtAuthResponse signUp(SignUp signUp) {
        log.info("Attempting to sign up user with email: {}", signUp.getEmail());
        UserDTO userDTO = modelMapper.map(signUp, UserDTO.class);

        // Get Employee if exists
        EmployeeDTO employee = employeeService.getEmployee(userDTO.getEmail());
        if (employee == null) {
            log.warn("Employee not found for email: {}", userDTO.getEmail());
            throw new IllegalArgumentException("Employee not found for email: " + userDTO.getEmail());
        }

        // Set Role
        if ("manager".equalsIgnoreCase(employee.getDesignation())) {
            userDTO.setRole(UserRole.ADMIN);
        } else {
            userDTO.setRole(UserRole.USER);
        }

        // Set relationship
        userDTO.setEmployee(employee);

        JwtAuthResponse jwtAuthResponse = authenticationService.signUp(userDTO);
        jwtAuthResponse.getUser().setPassword(null);
        return jwtAuthResponse;
    }
}
