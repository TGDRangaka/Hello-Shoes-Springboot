package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployee(String email);
    String saveEmployee(EmployeeDTO employee);
    EmployeeDTO updateEmployee(EmployeeDTO employee, String employeeCode);
}
