/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog2.main;

/**
 *
 * @author Seanzilla
 */
public class Dean extends Teacher {

    public Dean(int ID, String name, String birthdate, String address, char gender, String specialty, String degree, double workload, int departmentID) {
        super(ID, name, birthdate, address, gender, specialty, degree, workload, departmentID);
    }

    @Override
    public String defineWorkload(double workload) {
          if (workload >= 32) {
            return "full-time";
        } else {
            return "part-time";
        }
    }
    
}
