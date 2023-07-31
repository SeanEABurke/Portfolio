package com.prog2.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Seanzilla
 */
abstract class Person {
    
    private int ID;
    private String name;
    private LocalDate birthdate;
    private String address;
    private char gender;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    

    public Person(int ID, String name, String birthdate, String address, char gender) {
        this.ID = ID;
        this.name = name;
        this.birthdate = LocalDate.parse(birthdate, formatter);
        this.address = address;
        this.gender = gender;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = LocalDate.parse(birthdate, formatter);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" + "ID=" + ID + ", name=" + name + ", birthdate=" + birthdate + ", address=" + address + ", gender=" + gender + '}';
    }


    public abstract String defineWorkload(double workload);

}
