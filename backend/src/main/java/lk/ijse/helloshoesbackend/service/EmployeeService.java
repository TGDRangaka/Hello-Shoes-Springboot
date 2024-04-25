package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService {
    UserDetailsService userDetailsService();
    void saveUser(EmployeeDTO dto);
}
