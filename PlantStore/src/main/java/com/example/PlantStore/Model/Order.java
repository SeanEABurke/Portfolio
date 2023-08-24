package com.example.PlantStore.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderid;
    @Column
    private int custid;
    @Column
    private double price;
    @Column
    private Boolean orderplaced;

    public Order(int custid, double price) {
        this.custid = custid;
        this.price = price;
        orderplaced = false;
    }

    public Order() {
        orderplaced = false;
    }

    public int getOrderid() {
        return orderid;
    }

    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getOrderplaced() {
        return orderplaced;
    }

    public void setOrderplaced(Boolean orderplaced) {
        this.orderplaced = orderplaced;
    }
}
