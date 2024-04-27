package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.EmployeeBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeBOIMPL implements EmployeeBO {

    private final EmployeeService employeeService;
    private final JWTService jwtService;

    @Override
    public EmployeeDTO getEmployee(String token) {
        EmployeeDTO employee = employeeService.getEmployee(jwtService.extractUsername(token));
        employee.setPassword(null);
        return employee;
    }
}
