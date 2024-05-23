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

    @Autowired
    private CustomerService customerService;

    @GetMapping("/health")
    public String healthCheck(){
        log.info("Health check endpoint called");
        return "Customer Health Good";
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        log.info("Fetching all customers");
        List<CustomerDTO> customers = customerBO.getAllCustomers();
        log.debug("Fetched {} customers", customers.size());
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerDTO customer){
        log.info("Saving new customer with email: {}", customer.getEmail());
        CustomerDTO savedCustomer = customerBO.saveCustomer(customer);
        log.debug("Saved customer: {}", savedCustomer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PutMapping("/{customerId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> updateCustomer(@Valid @RequestBody CustomerDTO customer, @PathVariable String customerId){
        log.info("Updating customer with ID: {}", customerId);
        customerBO.updateCustomer(customer, customerId);
        log.debug("Updated customer with ID: {}", customerId);
        return ResponseEntity.accepted().body("Success");
    }
}
