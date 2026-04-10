package DTO;

import java.util.Date;

public class DeliveryEmployee extends Employee{
    private String licensePlate;
    private String deliveryArea;
    private int deliveryCount;
    private float feePerOrder;
    public DeliveryEmployee(){}

    public DeliveryEmployee(String employeeId, String position, float baseSalary, Date startDate, String deliveryArea, String licensePlate, float feePerOrder, int deliveryCount) {
        super(employeeId, position, baseSalary, startDate);
        this.deliveryArea = deliveryArea;
        this.licensePlate = licensePlate;
        this.feePerOrder = feePerOrder;
        this.deliveryCount = deliveryCount;
    }

    //***
    public void input(){}
    //***
    public float calculateSalary(){
        return feePerOrder;
    }
    //****
    public String getRole(){
        return "chưa làm";
    }
//***
    @Override
    public String toString() {
        return "DeliveryEmployee{" +
                "deliveryArea='" + deliveryArea + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", deliveryCount=" + deliveryCount +
                ", feePerOrder=" + feePerOrder +
                '}';
    }
}
