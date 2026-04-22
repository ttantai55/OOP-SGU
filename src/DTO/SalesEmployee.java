package DTO;

import java.util.Date;
import java.util.Scanner;

public class SalesEmployee extends Employee {
    private float bonus; // tiền thưởng
    private float commissionAmount;//tiền hoa hồng
    static Scanner sc = new Scanner(System.in);
    public SalesEmployee(){}
    public SalesEmployee(String employeeId, String position, float baseSalary, Date startDate, float bonus, float commissionAmount) {
        super(employeeId, position, baseSalary, startDate);
        this.bonus = bonus;
        this.commissionAmount = commissionAmount;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public float getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(float commissionAmount) {
        this.commissionAmount = commissionAmount;
    }
    @Override
    public void input(){
        super.input();
        System.out.println("Tien thuong: ");
        setBonus(Float.parseFloat(sc.nextLine()));
        System.out.println("Tien hoa hong: ");
        setCommissionAmount(Float.parseFloat(sc.nextLine()));

    }
    //****
    @Override
    public float calculateSalary(){
        return getBaseSalary() + commissionAmount;
    }
    //****
    @Override
    public String getRole(){
        return "nhan vien ban hang";
    }

    @Override
    public String toString() {
        return super.toString() + String.format("|%-15.2f | %-15.2f | %-20.2f |",
                bonus, commissionAmount, calculateSalary());
    }

    @Override

    public void displayInfo(){
        System.out.println(toString());
    }
}
