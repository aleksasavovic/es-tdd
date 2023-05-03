package asavovic.courseProject.services;

import asavovic.courseProject.entities.Customer;
import asavovic.courseProject.repositories.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer register(Customer newCustomer) {
        Customer customer = customerRepository.findByEmail(newCustomer.getEmail()).orElse(null);
        if (customer != null)
           return null;

        Customer customerToSave = new Customer();
        customerToSave.setEmail(newCustomer.getEmail());
        customerToSave.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        return customerRepository.save(customerToSave);
    }

    public Long login(String email, String password){
        return null;
    }
}
