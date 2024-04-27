package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;

public interface EmployeeBO {
    EmployeeDTO getEmployee(String token);
}
