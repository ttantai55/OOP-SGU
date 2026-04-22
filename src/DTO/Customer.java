package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Customer extends Person {
    private String customerId;
    private int loyaltyPoints; // điểm tích lũy
    private String customerType; // loại khách hàng (Kim cương , bạch kim, vàng)
    private Date registeredDate; //ngày đăng ký
    static Scanner sc = new Scanner(System.in);
    public Customer(){}
    public Customer(String customerId, int loyaltyPoints, String customerType, Date registeredDate) {
        this.customerId = customerId;
        this.loyaltyPoints = loyaltyPoints;
        this.customerType = customerType;
        this.registeredDate = registeredDate;
    }
    public String getCustomerId(){
        return customerId;
    }
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    public int getLoyaltyPoints(){
        return loyaltyPoints;
    }
    public void setLoyaltyPoints(int loyaltyPoints){
        this.loyaltyPoints = loyaltyPoints;
    }
    public String getCustomerType(){
        return customerType;
    }
    public void setCustomerType(String customerType){
        this.customerType = customerType;
    }
    public Date getRegisteredDate(){
        return registeredDate;
    }
    public void setRegisteredDate(Date registeredDate){
        this.registeredDate = registeredDate;
    }


    //****
    @Override
    public void input(){
        super.input();
        System.out.println("Nhap ma khach hang: ");
        setCustomerId(sc.nextLine());
        System.out.println("nhap diem tich luy: ");
        setLoyaltyPoints(Integer.parseInt(sc.nextLine()));
        System.out.println("loai khach hang (VIP/thuong/moi): ");
        setCustomerType(sc.nextLine());
        System.out.println("Ngay dang ky: ");
        this.registeredDate = new Date();

    }
    //****
    public void updatePoints(int newPoints){
        this.loyaltyPoints += newPoints;
        // Tự động phân loại lại khách hàng theo điểm
        if (loyaltyPoints >= 1000) {
            customerType = "VIP";
        } else if (loyaltyPoints >= 200) {
            customerType = "Thuong";
        } else {
            customerType = "Moi";
        }
    }
    //****
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return super.toString() + String.format("|%-15s | %-10d | %-10s | %-20s",

                customerId, loyaltyPoints, customerType, sdf.format(registeredDate));
    }
    public void displayInfo(){
        System.out.println(toString());
    }
}
