package com.example.PlantStore.Repository;

import com.example.PlantStore.Model.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findByLoggedinTrue();

    Customer findByUsername(String username);

    @Modifying
    @Query("UPDATE Customer c SET c.loggedin = false")
    void updateAllCustomersLoggedinStatus();
}
