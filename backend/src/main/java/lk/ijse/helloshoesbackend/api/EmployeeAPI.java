package lk.ijse.helloshoesbackend.api;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.helloshoesbackend.bo.EmployeeBO;
import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeAPI {

    private final EmployeeBO employeeBO;

    @GetMapping
    public ResponseEntity getAllEmployees(){
        try {
            return ResponseEntity.ok(employeeBO.getAllEmployees());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<EmployeeDTO> getEmployee(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(employeeBO.getEmployee(token));
    }
}
