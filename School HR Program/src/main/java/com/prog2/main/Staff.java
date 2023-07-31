/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog2.main;



/**
 *
 * @author Seanzilla
 */
public class Staff extends Person implements PayRoll {
    
    private String duty;
    private double workload;
    private int departmentID;
    private String status; 

    public Staff(int ID, String name, String birthdate, String address, char gender) {
        super(ID, name, birthdate, address, gender);
    }

    public Staff(int ID, String name, String birthdate, String address, char gender, String duty, double workload, int departmentID) {
        super(ID, name, birthdate, address, gender);
        this.duty = duty;
        this.workload = workload;
        this.departmentID = departmentID;
        this.status = defineWorkload(this.workload);
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {
        this.workload = workload;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String defineWorkload(double workload) {
        if (workload >= 32.0) {
            return "full-time";
        } 
        else {
            return "part-time";
        }
    }

    @Override
    public double computePayRoll() {
        return (double)Math.round((workload * 32 * 2) * 0.75 * 100) / 100;
    }

    @Override
    public String toString() {
        return "Staff {" + super.toString() + "duty=" + duty + ", workload=" + workload + ", departmentID=" + departmentID + ", status=" + status + '}';
    }


}
