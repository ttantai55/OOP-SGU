package DTO;

import java.util.Date;
import java.util.Scanner;

public class DeliveryEmployee extends Employee{
    private String licensePlate;
    private String deliveryArea;
    private int deliveryCount;
    private float feePerOrder;
    static Scanner sc = new Scanner(System.in);
    public DeliveryEmployee(){}

    public DeliveryEmployee(String employeeId, String position, float baseSalary, Date startDate, String deliveryArea, String licensePlate, float feePerOrder, int deliveryCount) {
        super(employeeId, position, baseSalary, startDate);
        this.deliveryArea = deliveryArea;
        this.licensePlate = licensePlate;
        this.feePerOrder = feePerOrder;
        this.deliveryCount = deliveryCount;
    }

    public String getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public int getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public float getFeePerOrder() {
        return feePerOrder;
    }

    public void setFeePerOrder(float feePerOrder) {
        this.feePerOrder = feePerOrder;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    //***
    @Override
    public void input(){
        super.input();
        System.out.println("Bien so xe: ");
        setLicensePlate(sc.nextLine());
        System.out.println("khu vuc giao hang: ");
        setDeliveryArea(sc.nextLine());
        System.out.println("so don giao: ");
        setDeliveryCount(Integer.parseInt(sc.nextLine()));
        System.out.println("phi moi don");
        setFeePerOrder(Float.parseFloat(sc.nextLine()));
    }
    //***
    @Override
    public float calculateSalary(){
        return getBaseSalary()+(feePerOrder*deliveryCount);
    }
    //****
    @Override
    public String getRole(){
        return "nhan vien giao hang";
    }
    //***
    @Override
    public String toString() {
        return super.toString() + String.format(" | %-15s | %-20s | %-10d | %,12.0f | %,15.0f",
                licensePlate, deliveryArea, deliveryCount, feePerOrder, calculateSalary());
    }
    public void displayInfo() {
        System.out.println(toString());
    }
}