package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface EmployeeBO {
    EmployeeDTO getEmployee(String token);
    List<EmployeeDTO> getAllEmployees();
    String saveEmployee(EmployeeDTO employee);
    EmployeeDTO updateEmployee(EmployeeDTO employee, String employeeCode);

    boolean isEmployeeCredentialsValid(String email, String password, Authentication authentication);
}
