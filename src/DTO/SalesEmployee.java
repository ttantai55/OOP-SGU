package DTO;

import java.util.Date;
import java.util.Scanner;

public class SalesEmployee extends Employee {
    private float bonus; // tiền thưởng
    private float commissionAmount;//tiền hoa hồng
    static Scanner sc = new Scanner(System.in);
    public SalesEmployee(){}
    public SalesEmployee(String employeeId, String CCCD, String position, float baseSalary, Date startDate, float bonus, float commissionAmount) {
        super(employeeId, CCCD,position, baseSalary, startDate);
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
        while (true) {
            try {
                String bonusStr = BUS.Validation.getNonEmptyString("Tien thuong: ");
                float b = Float.parseFloat(bonusStr);
                if (b < 0) { System.out.println("[Loi] Tien thuong phai >= 0!"); continue; }
                setBonus(b);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Phai nhap so! Vui long nhap lai.");
            }
        }
        while (true) {
            try {
                String commStr = BUS.Validation.getNonEmptyString("Tien hoa hong: ");
                float c = Float.parseFloat(commStr);
                if (c < 0) { System.out.println("[Loi] Tien hoa hong phai >= 0!"); continue; }
                setCommissionAmount(c);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Phai nhap so! Vui long nhap lai.");
            }
        }
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
        return super.toString() + String.format(" | %,12.0f | %,15.0f | %,15.0f",
                bonus, commissionAmount, calculateSalary());
    }
    @Override
    public void displayInfo() {
        System.out.println(toString());
    }
}