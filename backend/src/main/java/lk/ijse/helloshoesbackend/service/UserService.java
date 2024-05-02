package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    UserDTO getUser(String email);
}
