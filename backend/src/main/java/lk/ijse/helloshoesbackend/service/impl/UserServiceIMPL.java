package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.UserDTO;
import lk.ijse.helloshoesbackend.entity.UserEntity;
import lk.ijse.helloshoesbackend.repo.UserRepo;
import lk.ijse.helloshoesbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepo userRepo;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public UserDTO getUser(String email) {
        UserEntity userEntity = userRepo.findByEmail(email).get();
        return new ModelMapper().map(userEntity, UserDTO.class);
    }
}
