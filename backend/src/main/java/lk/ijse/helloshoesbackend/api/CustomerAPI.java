package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import lk.ijse.helloshoesbackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerAPI {


    @GetMapping("/health")
    public String healthCheck(){
        return "Customer Health Good";
    }
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAll());
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> saveCustomer(@RequestBody CustomerEntity customer){
        return ResponseEntity.ok(customerService.save(customer));
    }
}
