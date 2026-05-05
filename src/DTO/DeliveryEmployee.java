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

    public DeliveryEmployee(String employeeId,String CCCD, String position, float baseSalary, Date startDate, String deliveryArea, String licensePlate, float feePerOrder, int deliveryCount) {
        super(employeeId,CCCD, position, baseSalary, startDate);
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
        setLicensePlate(BUS.Validation.getNonEmptyString("Bien so xe: "));
        setDeliveryArea(BUS.Validation.getNonEmptyString("Khu vuc giao hang: "));
        while (true) {
            try {
                String str = BUS.Validation.getNonEmptyString("So don giao: ");
                int count = Integer.parseInt(str);
                if (count < 0) { System.out.println("[Loi] So don phai >= 0!"); continue; }
                setDeliveryCount(count);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Phai nhap so nguyen! Vui long nhap lai.");
            }
        }
        while (true) {
            try {
                String str = BUS.Validation.getNonEmptyString("Phi moi don: ");
                float fee = Float.parseFloat(str);
                if (fee < 0) { System.out.println("[Loi] Phi phai >= 0!"); continue; }
                setFeePerOrder(fee);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Phai nhap so! Vui long nhap lai.");
            }
        }
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