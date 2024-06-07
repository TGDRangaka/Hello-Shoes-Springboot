package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.entity.UserEntity;
import lk.ijse.helloshoesbackend.entity.enums.UserRole;
import lk.ijse.helloshoesbackend.exception.DataDuplicationException;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.repo.UserRepo;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.service.JWTService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceIMPL implements AuthenticationService {

    private final UserRepo userRepo;
    private final JWTService jwtService;
    private final EmployeeService employeeService;

    //    Utils
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        log.info("Attempting to authenticate user: {}", signIn.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword())
        );

        UserEntity byEmail = userRepo.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        log.info("Authentication successful for user: {}", signIn.getEmail());

        String token = jwtService.generateToken(byEmail);

        log.info("Generated JWT token for user: {}", signIn.getEmail());

        return new JwtAuthResponse(token, Conversion.toUserDTO(byEmail));
    }

    @Override
    public boolean isCredentialsValid(String email, String password, Authentication authentication) {
        log.info("Validating credentials for email: {}", email);

        password = UtilMatter.decodeCredentials(password, 0);

        if (!email.equals(authentication.getName())) {
            log.warn("Email mismatch: {} != {}", email, authentication.getName());
            return false;
        }

        UserEntity byEmail = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        boolean isValid = passwordEncoder.matches(password, byEmail.getPassword());
        if(isValid){
            log.info("Credentials valid for email: {}", email);
            return true;
        }
        log.error("Credentials invalid for email: {}", email);
        throw new InvalidDataException("Credentials invalid for email: " + email);
    }

    @Override
    public JwtAuthResponse signUp(SignUp signUp) {
        log.info("Attempting to sign up user: {}", signUp.getEmail());

        // Get Employee if exists
        EmployeeDTO employee = employeeService.getEmployee(signUp.getEmail());
        if (employee == null) {
            log.warn("Employee not found for email: {}", signUp.getEmail());
            throw new IllegalArgumentException("Employee not found for email: " + signUp.getEmail());
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(signUp.getEmail());
        userDTO.setPassword(signUp.getPassword());

        // Set Role
        if ("manager".equalsIgnoreCase(employee.getDesignation())) {
            userDTO.setRole(UserRole.ADMIN);
        } else {
            userDTO.setRole(UserRole.USER);
        }

        // Set relationship
        userDTO.setEmployee(employee);

        //        Throw exception if user already authenticated
        if (userRepo.findByEmail(signUp.getEmail()).isPresent()) {
            log.info("User already exists for email: {}", signUp.getEmail());
            throw new DataDuplicationException("User already exists");
        }

        UserEntity userEntity = new ModelMapper().map(userDTO, UserEntity.class);
        userEntity.setId(UtilMatter.generateUUID());
        userEntity.setPassword(passwordEncoder.encode(signUp.getPassword()));

        UserEntity save = userRepo.save(userEntity);
        String token = jwtService.generateToken(save);

        log.info("User signed up and JWT token generated for user: {}", signUp.getEmail());

        return new JwtAuthResponse(token, Conversion.toUserDTO(save));
    }
}
