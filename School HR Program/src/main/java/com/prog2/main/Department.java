/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prog2.main;

import java.util.ArrayList;

/**
 *
 * @author Seanzilla
 */
public class Department {
   
    private String name;
    private int departmentID;
    ArrayList<Staff> staffList = new ArrayList<Staff>();
    ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
    private Dean dean;
    
    public Department(String name, int departmentId) {
        this.name = name;
        this.departmentID = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public ArrayList<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(ArrayList<Staff> staffList) {
        this.staffList = staffList;
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(ArrayList<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Dean dean) {
        this.dean = dean;
    }
    
    @Override
    public String toString() {
        return "Department{" + "name=" + name + ", departmentID=" + departmentID + ", staffList=" + staffList + ", teacherList=" + teacherList + ", dean=" + dean + '}';
    }
    
    
}
