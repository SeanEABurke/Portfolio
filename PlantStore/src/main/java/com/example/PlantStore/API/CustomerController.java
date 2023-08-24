package com.example.PlantStore.API;

import com.example.PlantStore.Model.Customer;
import com.example.PlantStore.Repository.CustomerRepository;
import com.example.PlantStore.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Transactional
    @PostMapping(path = "/add")
    public void addNewCustomer(@RequestParam String name, @RequestParam String username, @RequestParam String password, @RequestParam String address) {
        customerService.newCustomer(name, username, password, address);
    }

    @GetMapping
    public @ResponseBody Iterable<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @Transactional
    @PutMapping(path = "/login")
    public void logIn(@RequestParam String username, @RequestParam String password) {
        customerService.logInCustomer(username, password);

}
    @Transactional
    @PutMapping(path = "/logout")
    public @ResponseBody void logOut() {
        customerService.logOutAll();
    }

    @GetMapping(path="/findLoggedin")
    public @ResponseBody Customer findLoggedin() {
        return customerService.findLoggedIn();
    }

}
