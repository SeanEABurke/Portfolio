package com.example.PlantStore.API;

import com.example.PlantStore.Model.Customer;
import com.example.PlantStore.Model.Order;
import com.example.PlantStore.Model.OrderDetails;
import com.example.PlantStore.Model.Plant;
import com.example.PlantStore.Repository.CustomerRepository;
import com.example.PlantStore.Repository.OrderDetailsRepository;
import com.example.PlantStore.Repository.OrderRepository;
import com.example.PlantStore.Repository.PlantRespository;
import com.example.PlantStore.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/newOrder")
    public @ResponseBody void createNewOrder(@RequestParam int plantid, @RequestParam int qty) {
        orderService.addPlantToOrder(plantid, 1);
    }

    @GetMapping
    public @ResponseBody Iterable<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(path = "/cart")
    public @ResponseBody List<OrderDetails> getCurrentOrderDetails() {
        return orderService.getCart();
    }

//    @PutMapping(path = "/addToCart")
//    public @ResponseBody void addToCart(@RequestParam int plantid, @RequestParam int qty) {
//        int custid = customerRepository.findByLoggedinTrue().get(0).getId();
//        //Find current order
//        Order current = orderRepository.findOrderByCustidAndOrderplacedFalse(custid);
//        //Add new order details
//        OrderDetails newod = new OrderDetails(plantid, qty, current.getOrderid());
//        orderDetailsRepository.save(newod);
//        //Update current order
//        double currentprice = current.getPrice();
//        current.setPrice((plantRespository.findById(plantid).get().getPrice() * qty) + currentprice);
//        orderRepository.save(current);
//
//    }

    @PutMapping(path = "/placeOrder")
    public @ResponseBody void placeOrder() {
        orderService.placeOrder();
    }

    @GetMapping(path="/getTotalPrice")
    public @ResponseBody Double getTotalPrice(@RequestParam int orderid) {
        return orderService.getTotalPrice(orderid);
    }
    @Transactional
    @DeleteMapping(path="/removeItem")
    public void removeItem(int plantid) {
        orderService.removeFromCart(plantid);
    }

//    @Transactional
//    @PutMapping(path="/updateQty")
//    public void updateQty(int plantid, int newQty) {
//        orderService.setODQuantity(plantid, newQty);
//    }

}
