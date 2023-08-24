package com.example.PlantStore.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odid;

    @Column
    private int plantid;
    @Column
    private int qty;

    @Column
    private int orderid;

    public OrderDetails(int plantid, int qty, int orderid) {
        this.plantid = plantid;
        this.qty = qty;
        this.orderid = orderid;
    }
    public OrderDetails() {
    }

    public int getOdid() {
        return odid;
    }

    public int getPlantid() {
        return plantid;
    }

    public void setPlantid(int plantid) {
        this.plantid = plantid;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getOrderid() {
        return orderid;
    }
    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "odid=" + odid +
                ", plantid=" + plantid +
                ", qty=" + qty +
                ", orderid=" + orderid +
                '}';
    }
}
