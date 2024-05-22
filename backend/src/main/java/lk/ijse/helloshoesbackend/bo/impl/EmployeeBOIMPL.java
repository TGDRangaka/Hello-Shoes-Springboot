package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.EmployeeBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.entity.UserEntity;
import lk.ijse.helloshoesbackend.service.AuthenticationService;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeBOIMPL implements EmployeeBO {

    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;

    @Override
    public EmployeeDTO getEmployee(String token) {
        return null;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Override
    public String saveEmployee(EmployeeDTO employee) {
        employee.setEmployeeCode(UtilMatter.generateUUID());
        return employeeService.saveEmployee(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee, String employeeCode) {
        return employeeService.updateEmployee(employee, employeeCode);
    }

    @Override
    public boolean isEmployeeCredentialsValid(String email, String password, Authentication authentication) {
        password = UtilMatter.decodeCredentials(password, 0);
        if(!email.equals(authentication.getName())) return false;
        return authenticationService.isCredentialsValid(email, password);
    }
}
