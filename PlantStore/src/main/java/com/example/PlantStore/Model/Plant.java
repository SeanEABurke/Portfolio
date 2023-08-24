package com.example.PlantStore.Model;

import jakarta.persistence.*;

@Entity
public class Plant {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int plantid;

    @Column
    private String name;

    @Column
    private int qty;
    @Column
    private double price;

    public Plant(String name, int qty, double price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public Plant() {
    }

    public int getPlantid() {
        return plantid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
