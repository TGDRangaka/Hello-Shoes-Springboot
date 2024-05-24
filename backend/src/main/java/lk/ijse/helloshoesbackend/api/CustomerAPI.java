package lk.ijse.helloshoesbackend.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lk.ijse.helloshoesbackend.bo.CustomerBO;
import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class CustomerAPI {
    private final CustomerBO customerBO;
    private final CustomerService customerService;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Health check endpoint called");
        return "Customer Health Good";
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        log.info("Get all customers endpoint called");
        List<CustomerDTO> customers = customerBO.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerDTO customer){
        log.info("Save customer endpoint called");
        CustomerDTO savedCustomer = customerBO.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PutMapping("/{customerId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> updateCustomer(@Valid @RequestBody CustomerDTO customer, @PathVariable String customerId){
        log.info("Update customer endpoint called");
        customerBO.updateCustomer(customer, customerId);
        return ResponseEntity.accepted().body("Success");
    }
}
