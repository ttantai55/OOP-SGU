package DTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Customer extends Person {
    private String customerId;
    private int loyaltyPoints; // điểm tích lũy
    private String customerType; // loại khách hàng (Kim cương , bạch kim, vàng)
    private Date registeredDate; //ngày đăng ký
    private String username;
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
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    //****
    @Override
    public void input(){
        super.input();
        setCustomerId(BUS.Validation.getNonEmptyString("Nhap ma khach hang: "));
        while (true) {
            try {
                String pointStr = BUS.Validation.getNonEmptyString("Nhap diem tich luy: ");
                int points = Integer.parseInt(pointStr);
                if (points < 0) {
                    System.out.println("[Loi] Diem tich luy phai >= 0!");
                    continue;
                }
                setLoyaltyPoints(points);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Diem tich luy phai la so nguyen! Vui long nhap lai.");
            }
        }
        while (true) {
            String type = BUS.Validation.getNonEmptyString("Loai khach hang (VIP/Thuong/Moi): ");
            if (type.equalsIgnoreCase("VIP") || type.equalsIgnoreCase("Thuong") || type.equalsIgnoreCase("Moi")) {
                setCustomerType(type);
                break;
            } else {
                System.out.println("[Loi] Loai khach hang phai la: VIP, Thuong hoac Moi!");
            }
        }
        System.out.println("Ngay dang ky: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
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
        return super.toString() + String.format(" | %-10s | %-10d | %-10s | %-12s",
                customerId, loyaltyPoints, customerType, sdf.format(registeredDate));
    }
    public void displayInfo() {
        System.out.println(toString());
    }
}
