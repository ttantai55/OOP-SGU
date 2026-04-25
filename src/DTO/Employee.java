package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class Employee extends Person {
    protected String employeeId;
    protected String position; //chức vụ
    protected float baseSalary; //lương cơ bản
    protected Date startDate;//ngày vào làm
    static Scanner sc = new Scanner(System.in);
    public Employee(){}
    public Employee(String employeeId, String position, float baseSalary, Date startDate){
        this.employeeId = employeeId;
        this.position = position;
        this.baseSalary = baseSalary;
        this.startDate = startDate;
    }
    public String getEmployeeId(){
        return employeeId;
    }
    public void setEmployeeId(String employeeId){
        this.employeeId = employeeId;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    //****
    @Override
    public void input(){
        super.input();
        System.out.println(" Ma nhan vien: ");
        setEmployeeId(sc.nextLine());
        System.out.println(" Chuc vu: ");
        setPosition(sc.nextLine());
        System.out.println("Nhap luong co ban: ");
        setBaseSalary(Float.parseFloat(sc.nextLine()));
        System.out.println("Ngay vao lam: ");
        this.startDate = new Date();

    }
    public abstract float calculateSalary();//abstract method tính lương
    public abstract String getRole();//abstract method phân quyền

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return super.toString() + String.format("%-20s | %-15s | %15.2f | %-15s |",
                employeeId, position, baseSalary,sdf.format(startDate) );
    }
    public void displayInfo(){
        System.out.println(toString());
    }
}
