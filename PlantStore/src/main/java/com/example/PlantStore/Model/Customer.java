package com.example.PlantStore.Model;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custid;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String address;

    @Column
    private Boolean loggedin;

    public Customer(String username, String password, String name, String address, boolean loggedin) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.loggedin = loggedin;
    }
    public Customer() {
        loggedin = false;
    }

    public int getId() {
        return custid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getLoggedin() {return loggedin;}

    public void setLoggedin(Boolean loggedin) {this.loggedin = loggedin;}
}
