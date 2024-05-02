package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.EmployeeBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.service.JWTService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeBOIMPL implements EmployeeBO {

    private final EmployeeService employeeService;

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
//        employee.setProfilePic(UtilMatter.convertBase64(employee.getProfilePic()));
        return employeeService.saveEmployee(employee);
    }
}
