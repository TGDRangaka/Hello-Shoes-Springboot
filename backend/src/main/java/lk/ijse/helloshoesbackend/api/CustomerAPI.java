package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.helloshoesbackend.bo.CustomerBO;
import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin
@RequiredArgsConstructor
public class CustomerAPI {
    private final CustomerBO customerBO;


    @GetMapping("/health")
    public String healthCheck(){
        return "Customer Health Good";
    }
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        try{
            return ResponseEntity.ok(customerBO.getAllCustomers());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customer){
        try{
            return ResponseEntity.ok(customerBO.saveCustomer(customer));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
