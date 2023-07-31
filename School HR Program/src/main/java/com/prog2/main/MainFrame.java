/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.prog2.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Seanzilla
 */
//Run the program by running this file!!

public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    //Stores the department data
    static Department admin = new Department("Admin", 1);
    static Department science = new Department("Science", 11);
    static Department humanities = new Department("Humanities", 12);
    static Department math = new Department("Math", 13);
    static Department[] departmentList = {admin, science, humanities, math};
    
    //Puts all of the employees into array lists
    static ArrayList<Teacher> teacherList = new ArrayList<>(readTeachers("TeacherList.txt"));
    static ArrayList<Staff> staffList = new ArrayList<>(readStaff("StaffList.txt"));
    static ArrayList<Dean> deanList = new ArrayList<>(readDeans("TeacherList.txt"));

    //Puts the teachers in their departments and sets the dean for each department
    public static void setLists() {
        admin.setStaffList(staffList);

        for (Department department : departmentList) {
            for (Teacher teacher : teacherList) {
                if (teacher.getDepartmentID() == department.getDepartmentID()) {
                    department.teacherList.add(teacher);
                }
            }
        }

        for (Department department : departmentList) {
            for (Dean dean : deanList) {
                if (dean.getDepartmentID() == department.getDepartmentID()) {
                    department.setDean(dean);
                }
            }
        }
    }

    //reads all of the teachers
    public static ArrayList<Teacher> readTeachers(String teacherPath) {
        File file = new File(teacherPath);
        ArrayList<Teacher> teacherList = new ArrayList<>();
        try (Scanner input = new Scanner(file)) {
            while (input.hasNext()) {
                String row = input.nextLine();			// read the entire row
                String[] strs = row.split(",");
                if (strs[9].equalsIgnoreCase("teacher")) {
                    teacherList.add(new Teacher(Integer.parseInt(strs[0]), strs[1], strs[2], strs[3], strs[4].charAt(0), strs[5], strs[6], Double.parseDouble(strs[7]), Integer.parseInt(strs[8])));
                }
            }
        } catch (IOException e) {
            System.out.println(String.format("File %s does not exist", teacherPath));
        }

        return teacherList;
    }
    //Reads the deans
    public static ArrayList<Dean> readDeans(String teacherPath) {
        File file = new File(teacherPath);
        ArrayList<Dean> deanList = new ArrayList<>();
        try (Scanner input = new Scanner(file)) {
            while (input.hasNext()) {
                String row = input.nextLine();			// read the entire row
                String[] strs = row.split(",");
                if (strs[9].equalsIgnoreCase("dean")) {
                    deanList.add(new Dean(Integer.parseInt(strs[0]), strs[1], strs[2], strs[3], strs[4].charAt(0), strs[5], strs[6], Double.parseDouble(strs[7]), Integer.parseInt(strs[8])));
                } 
            }
        } catch (IOException e) {
            System.out.println(String.format("File %s does not exist", teacherPath));
        }

        return deanList;
    }

    //Reads all of the staff
    public static ArrayList<Staff> readStaff(String staffPath) {
        File file = new File(staffPath);
        ArrayList<Staff> staffList = new ArrayList<>();
        try (Scanner input = new Scanner(file)) {
            while (input.hasNext()) {
                String row = input.nextLine();			// read the entire row
                String[] strs = row.split(",");			// extract each part
                staffList.add(new Staff(Integer.parseInt(strs[0]), strs[1].toString(), strs[2].toString(), strs[3].toString(), strs[4].charAt(0), strs[5], Double.parseDouble(strs[6]), Integer.parseInt(strs[7])));
            }
        } catch (IOException e) {
            System.out.println(String.format("File %s does not exist", staffPath));
        }

        return staffList;
    }

    public static void writeFileTeacher(String path, Teacher teacher) {
        File file = new File(path);

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(teacher.getID() + "," + teacher.getName() + "," + teacher.getBirthdate() + "," + teacher.getAddress() + "," + teacher.getGender() + "," + teacher.getSpecialty() + "," + teacher.getDegree() + "," + teacher.getWorkload() + "," + teacher.getDepartmentID() + ",teacher,\n");

        } catch (IOException e) {
            System.out.println("Writing file failed");
        }
    }

    public static void writeFileDean(String path, Dean dean) {
        File file = new File(path);

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(dean.getID() + "," + dean.getName() + "," + dean.getBirthdate() + "," + dean.getAddress() + "," + dean.getGender() + "," + dean.getSpecialty() + "," + dean.getDegree() + "," + dean.getWorkload() + "," + dean.getDepartmentID() + ",dean,\n");

        } catch (IOException e) {
            System.out.println("Writing file failed");
        }
    }

    public static void writeFileStaff(String path, Staff staff) {
        File file = new File(path);

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(staff.getID() + "," + staff.getName() + "," + staff.getBirthdate() + "," + staff.getAddress() + "," + staff.getGender() + "," + staff.getDuty() + "," + staff.getWorkload() + "," + staff.getDepartmentID() + ",\n");

        } catch (IOException e) {
            System.out.println("Writing file failed");
        }
    }

    public MainFrame() {
        initComponents();
        setLists();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        viewEmpBTN = new javax.swing.JButton();
        addTeacherBTN = new javax.swing.JButton();
        addStaffBTN = new javax.swing.JButton();
        removeEmployeeBTN = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Add Staff");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome");
        setBackground(new java.awt.Color(255, 102, 102));

        viewEmpBTN.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N
        viewEmpBTN.setText("View Employees");
        viewEmpBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewEmpBTNActionPerformed(evt);
            }
        });

        addTeacherBTN.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N
        addTeacherBTN.setText("Add Teacher");
        addTeacherBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTeacherBTNActionPerformed(evt);
            }
        });

        addStaffBTN.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N
        addStaffBTN.setText("Add Staff");
        addStaffBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStaffBTNActionPerformed(evt);
            }
        });

        removeEmployeeBTN.setFont(new java.awt.Font("Bookman Old Style", 0, 18)); // NOI18N
        removeEmployeeBTN.setText("Remove Employee");
        removeEmployeeBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEmployeeBTNActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Century", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("School HR Application");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeEmployeeBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStaffBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addTeacherBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewEmpBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(viewEmpBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(addTeacherBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(addStaffBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(removeEmployeeBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void viewEmpBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewEmpBTNActionPerformed
        // TODO add your handling code here:
        new Employees().setVisible(true);
    }//GEN-LAST:event_viewEmpBTNActionPerformed

    private void addTeacherBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTeacherBTNActionPerformed
        // TODO add your handling code here:
        new NewTeacher().setVisible(true);
    }//GEN-LAST:event_addTeacherBTNActionPerformed

    private void addStaffBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStaffBTNActionPerformed
        // TODO add your handling code here:
        new NewStaff().setVisible(true);
    }//GEN-LAST:event_addStaffBTNActionPerformed

    private void removeEmployeeBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEmployeeBTNActionPerformed
        // TODO add your handling code here:
        new RemoveEmployee().setVisible(true);
    }//GEN-LAST:event_removeEmployeeBTNActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStaffBTN;
    private javax.swing.JButton addTeacherBTN;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JButton removeEmployeeBTN;
    private javax.swing.JButton viewEmpBTN;
    // End of variables declaration//GEN-END:variables
}
