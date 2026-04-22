package DTO;

import java.util.Date;
import java.util.Scanner;

public class Manager extends Employee{
    private float allowance; // phụ cấp
    private String department; // phòng ban
    static Scanner sc = new Scanner(System.in);
    public Manager(){}
    public Manager(String employeeId, String position, float baseSalary, Date startDate, float allowance, String department) {
        super(employeeId, position, baseSalary, startDate);
        this.allowance = allowance;
        this.department = department;
    }

    public float getAllowance() {
        return allowance;
    }

    public void setAllowance(float allowance) {
        this.allowance = allowance;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    //****
    @Override
    public void input(){
        super.input();
        System.out.println("Nhap phu cap: ");
        setAllowance(Float.parseFloat(sc.nextLine()));
        System.out.println("Phong ban: ");
        setDepartment(sc.nextLine());
    }
    //****
    @Override
    public float calculateSalary(){
        return getBaseSalary() + allowance;
    }
    //***
    @Override
    public String getRole(){
        return "Quan ly";
    }
    //***
    @Override
    public String toString() {
        return super.toString() + String.format("| %-15.2f | %-10s | %-20.2f | ",
                allowance, department, calculateSalary());
    }
    public void displayInfo(){
        System.out.println(toString());
    }
}
