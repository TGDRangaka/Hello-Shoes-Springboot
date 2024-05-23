package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.entity.UserEntity;
import lk.ijse.helloshoesbackend.exception.DataDuplicationException;
import lk.ijse.helloshoesbackend.repo.UserRepo;
import lk.ijse.helloshoesbackend.reqAndResp.response.JwtAuthResponse;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
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

    //    Utils
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(UserDTO dto) {
        log.info("Attempting to authenticate user: {}", dto.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        UserEntity byEmail = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        log.info("Authentication successful for user: {}", dto.getEmail());

        String token = jwtService.generateToken(byEmail);

        log.info("Generated JWT token for user: {}", dto.getEmail());

        return new JwtAuthResponse(token, Conversion.toUserDTO(byEmail));
    }

    @Override
    public boolean isCredentialsValid(String email, String password) {
        log.info("Validating credentials for email: {}", email);
        UserEntity byEmail = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        boolean isValid = passwordEncoder.matches(password, byEmail.getPassword());
        log.info("Credentials valid for email: {}: {}", email, isValid);
        return isValid;
    }

    @Override
    public JwtAuthResponse signUp(UserDTO dto) {
        log.info("Attempting to sign up user: {}", dto.getEmail());

        //        Throw exception if user already authenticated
        if (userRepo.findByEmail(dto.getEmail()).isPresent())
            throw new DataDuplicationException("User already exists");

        UserEntity userEntity = new ModelMapper().map(dto, UserEntity.class);
        userEntity.setId(UtilMatter.generateUUID());
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));

        UserEntity save = userRepo.save(userEntity);
        String token = jwtService.generateToken(save);

        log.info("User signed up and JWT token generated for user: {}", dto.getEmail());

        return new JwtAuthResponse(token, Conversion.toUserDTO(save));
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        log.info("Attempting to refresh JWT token");

        var username = jwtService.extractUsername(accessToken);
        var userEntity = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        String token = jwtService.generateToken(userEntity);

        log.info("Refreshed JWT token for user: {}", username);

        return new JwtAuthResponse(token, Conversion.toUserDTO(userEntity));
    }
}
