/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog2.main;



/**
 *
 * @author Seanzilla
 */
public class Teacher extends Person implements PayRoll {
    
    private String specialty;
    private String degree;
    private String status;
    private double workload;
    private int departmentID;

    public Teacher(int ID, String name, String birthdate, String address, char gender) {
        super(ID, name, birthdate, address, gender);
    }

    public Teacher( int ID, String name, String birthdate, String address, char gender, String specialty, String degree, double workload, int departmentID) {
        super(ID, name, birthdate, address, gender);
        this.specialty = specialty;
        this.degree = degree;
        this.workload = workload;
        this.departmentID = departmentID;
        this.status = defineWorkload(this.workload);
    }
  

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStatus() {
        return status;
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
    
    
    @Override
    public String defineWorkload(double workload) {
        if (workload >= 32) {
            return "full-time";
        } else {
            return "part-time";
        }
    }

    @Override
    public double computePayRoll() {
        if (workload >= 32) {
            return (double)Math.round((workload * getDegreeRate(this.degree) * 2) * 0.85 * 100) / 100;
        } else {
            return (double)Math.round((workload * getDegreeRate(this.degree) * 2) * 0.76 * 100) / 100;
        }
    }

    public int getDegreeRate(String degree) {
        switch (degree.toLowerCase()) {
            case "phd":
                return 112;
            case "master":
            case "masters":
            case "master's":
                return 82;
            case "bachelor":
            case "bachelors":
            case "bachelor's":
                return 42;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return "Teacher {" + super.toString() + "specialty=" + specialty + ", degree=" + degree + ", status=" + status + ", workload=" + workload + ", departmentID=" + departmentID + '}';
    }

    
    

}
