package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployee(String email);
    String saveEmployee(EmployeeDTO employee);
    void updateEmployee(EmployeeDTO employee, String employeeCode);
}
