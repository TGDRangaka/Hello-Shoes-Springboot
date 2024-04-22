package lk.ijse.helloshoesbackend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeAPI {
    @GetMapping
    public String healthCheck(){
        return "Employee Health Good";
    }
}
