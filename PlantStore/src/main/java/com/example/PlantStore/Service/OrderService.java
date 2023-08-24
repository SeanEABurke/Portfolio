package com.example.PlantStore.Service;

import com.example.PlantStore.Model.Order;
import com.example.PlantStore.Model.OrderDetails;
import com.example.PlantStore.Model.Plant;
import com.example.PlantStore.Repository.CustomerRepository;
import com.example.PlantStore.Repository.OrderDetailsRepository;
import com.example.PlantStore.Repository.OrderRepository;
import com.example.PlantStore.Repository.PlantRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PlantService plantService;

    public void addPlantToOrder(int plantid, int qty) {
        int custid = customerService.findLoggedIn().getId();
        Order current = orderRepository.findOrderByCustidAndOrderplacedFalse(custid);
        //Creates new order if the customer doesn't already have one
        if (current == null) {
            Order o = new Order();
            o.setCustid(custid);
            o.setPrice(plantService.getPlant(plantid).getPrice() * qty);
            o.setOrderplaced(false);
            orderRepository.save(o);

            OrderDetails newod = new OrderDetails(plantid, qty, o.getOrderid());
            orderDetailsRepository.save(newod);

            plantService.plantSale(plantid, 1);
        }
        //Adds to the order if they already have an order
        if (current!= null) {
            //Checks if that plant is already in the order
            if (orderDetailsRepository.findByOrderidAndPlantid(current.getOrderid(), plantid) == null) {
                OrderDetails newod = new OrderDetails(plantid, qty, current.getOrderid());
                orderDetailsRepository.save(newod);
            }
            else {
                OrderDetails od = orderDetailsRepository.findByOrderidAndPlantid(current.getOrderid(), plantid);
                od.setQty(od.getQty() + 1);
                orderDetailsRepository.save(od);
            }
            double currentprice = current.getPrice();
            current.setPrice((plantService.getPlant(plantid).getPrice() * qty) + currentprice);
            orderRepository.save(current);

            plantService.plantSale(plantid, 1);
        }
    }

    public List<OrderDetails> getCart() {
        if (customerService.findLoggedIn() == null) {
            return null;
        }

        int custid = customerService.findLoggedIn().getId();
        Order current = orderRepository.findOrderByCustidAndOrderplacedFalse(custid);
        if (current == null) {
            return null;
        }

        Iterable<OrderDetails> orderDetails = orderDetailsRepository.findAllByOrderid(current.getOrderid());
        List<OrderDetails> list = new ArrayList<>();
        orderDetails.forEach(list::add);
        return list;
    }

    public void placeOrder() {
        int custid = customerService.findLoggedIn().getId();
        //Find current order and set Orderplaced to true
        Order current = orderRepository.findOrderByCustidAndOrderplacedFalse(custid);
        current.setOrderplaced(true);
        orderRepository.save(current);

        Iterable<OrderDetails> od = orderDetailsRepository.findAllByOrderid(current.getOrderid());
    }

    public double getTotalPrice(int orderid) {
        return orderRepository.getByOrderid(orderid).getPrice();
    }

    public void removeFromCart(int plantid) {
        int custid = customerService.findLoggedIn().getId();
        Order current = orderRepository.findOrderByCustidAndOrderplacedFalse(custid);
        OrderDetails od = orderDetailsRepository.findByOrderidAndPlantid(current.getOrderid(), plantid);
        plantService.removeFromCart(plantid);
        //Removes the item from the total price
        double currentprice = current.getPrice();
        current.setPrice(currentprice - (plantService.getPlant(plantid).getPrice()));
        orderRepository.save(current);

        //Lowers the qty of that plant, or removes if at 0
        if (od.getQty() > 1) {
            od.setQty(od.getQty() -1 );
            orderDetailsRepository.save(od);
        }
        else {
            orderDetailsRepository.deleteByOdid(od.getOdid());
        }
    }

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

//    public Boolean setODQuantity(int plantid, int newQty) {
//        if (newQty < 0) {
//            return false;
//        }
//
//        int custid = customerService.findLoggedIn().getId();
//        Order current = orderRepository.findOrderByCustidAndOrderplacedFalse(custid);
//        OrderDetails od = orderDetailsRepository.findByOrderidAndPlantid(current.getOrderid(), plantid);
//        int oldQty = od.getQty();
//
//        if ((plantService.getQty(plantid) - (newQty - oldQty)) >= 0) {
//
//            od.setQty(newQty);
//            orderDetailsRepository.save(od);
//            double currentprice = current.getPrice();
//            current.setPrice((plantService.getPlant(plantid).getPrice() * (newQty - oldQty)) + currentprice);
//            orderRepository.save(current);
//
//            plantService.updateQty(plantid, (newQty - oldQty));
//
//            return true;
//        } else {
//            return false;
//        }
//
//    }

}
