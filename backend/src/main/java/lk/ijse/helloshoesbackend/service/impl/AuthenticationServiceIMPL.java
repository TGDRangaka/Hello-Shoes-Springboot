package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.entity.EmployeeEntity;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignIn;
import lk.ijse.helloshoesbackend.reqAndResp.request.SignUp;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.service.JWTService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceIMPL implements AuthenticationService {

    private final EmployeeRepo employeeRepo;
    private final JWTService jwtService;

//    Utils
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword())
        );

        EmployeeEntity byEmail = employeeRepo.findByEmail(signIn.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

        String token = jwtService.generateToken(byEmail);

        return new JwtAuthResponse(token, Conversion.toEmployeeDTO(byEmail));
    }

    @Override
    public JwtAuthResponse signUp(SignUp signUp) {
        EmployeeEntity employeeEntity = new ModelMapper().map(signUp, EmployeeEntity.class);
        employeeEntity.setEmployeeCode(UtilMatter.generateUUID());
        employeeEntity.setPassword(passwordEncoder.encode(signUp.getPassword()));

        EmployeeEntity savedEmployee = employeeRepo.save(employeeEntity);
        String token = jwtService.generateToken(savedEmployee);

        return new JwtAuthResponse(token, Conversion.toEmployeeDTO(savedEmployee));
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        var username = jwtService.extractUsername(accessToken);
        var employeeEntity = employeeRepo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

        String token = jwtService.generateToken(employeeEntity);
        return new JwtAuthResponse(token, Conversion.toEmployeeDTO(employeeEntity));
    }
}
