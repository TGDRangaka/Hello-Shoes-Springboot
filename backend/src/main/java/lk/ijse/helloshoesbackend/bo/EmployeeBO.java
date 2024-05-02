package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeBO {
    EmployeeDTO getEmployee(String token);
    List<EmployeeDTO> getAllEmployees();
    String saveEmployee(EmployeeDTO employee);
}
