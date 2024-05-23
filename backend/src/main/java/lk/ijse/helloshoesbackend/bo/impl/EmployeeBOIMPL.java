package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.EmployeeBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeBOIMPL implements EmployeeBO {

    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Attempting to get all employees");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return employees;
    }

    @Override
    public String saveEmployee(EmployeeDTO employee) {
        log.info("Attempting to save employee: {}", employee.getEmail());
        employee.setEmployeeCode(UtilMatter.generateUUID());
        String employeeCode = employeeService.saveEmployee(employee);
        return employeeCode;
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee, String employeeCode) {
        log.info("Attempting to update employee: {}", employee.getEmail());
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employee, employeeCode);
        return updatedEmployee;
    }

    @Override
    public boolean isEmployeeCredentialsValid(String email, String password, Authentication authentication) {
        log.info("Attempting to validate employee credentials: {}", email);
        password = UtilMatter.decodeCredentials(password, 0);
        if (!email.equals(authentication.getName())) {
            log.warn("Email mismatch: {} != {}", email, authentication.getName());
            return false;
        }
        boolean valid = authenticationService.isCredentialsValid(email, password);
        log.info("Credentials valid: {}", valid);
        return valid;
    }
}
