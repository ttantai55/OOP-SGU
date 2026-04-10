package DTO;

import java.util.Date;

public class Customer extends Person {
    private String customerId;
    private int loyaltyPoints; // điểm tích lũy
    private String customerType; // loại khách hàng (Kim cương , bạch kim, vàng)
    private Date registeredDate; //ngày đăng ký
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
    public void input(){}
    //****
    public void updatePoints(int newPoints){}
    //****
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", customerType='" + customerType + '\'' +
                ", registeredDate=" + registeredDate +
                '}';
    }
}
