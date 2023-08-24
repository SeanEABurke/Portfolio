package com.example.PlantStore.Service;

import com.example.PlantStore.Model.Customer;
import com.example.PlantStore.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public void newCustomer(String name, String username, String password, String address) {
        customerRepository.updateAllCustomersLoggedinStatus();
        Customer c = new Customer(username, password, name, address, true);
        customerRepository.save(c);
    }

    public void logInCustomer(String username, String password) {
        //Logs out a customer if they are already logged in
        customerRepository.updateAllCustomersLoggedinStatus();

        Customer c = customerRepository.findByUsername(username);

            if (c.getPassword().contentEquals(password)) {
                c.setLoggedin(true);
                customerRepository.save(c);
            } else {
                // Return an error response indicating incorrect username or password
                throw new RuntimeException("Incorrect username or password");
            }
    }

    public void logOutAll() {
        customerRepository.updateAllCustomersLoggedinStatus();
    }

    public Customer findLoggedIn() {
        if (!customerRepository.findByLoggedinTrue().isEmpty()) {
            return customerRepository.findByLoggedinTrue().get(0);
        }
        return null;
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

}
