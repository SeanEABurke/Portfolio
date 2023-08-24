package com.example.PlantStore.Repository;

import com.example.PlantStore.Model.Customer;
import com.example.PlantStore.Model.OrderDetails;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Integer> {
    Iterable<OrderDetails> findAllByOrderid(int orderid);
    OrderDetails findByOrderidAndPlantid(int orderid, int plantid);
    void deleteByOdid(int odid);
}
