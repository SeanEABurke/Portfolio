package com.example.PlantStore.Repository;

import com.example.PlantStore.Model.Customer;
import com.example.PlantStore.Model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    Order findOrderByCustidAndOrderplacedFalse(int custid);

    Order getByOrderid(int orderid);
}
