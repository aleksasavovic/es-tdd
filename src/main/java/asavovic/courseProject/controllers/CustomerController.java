package asavovic.courseProject.controllers;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.register(customer);
        if (savedCustomer != null)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody Customer customer) {
        Long sessionId = customerService.login(customer.getEmail(), customer.getPassword());
        if (sessionId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(sessionId);

    }
}
