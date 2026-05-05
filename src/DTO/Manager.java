package DTO;

import java.util.Date;

public class Manager extends Employee{
    private float allowance; // phụ cấp
    private String department; // phòng ban
    public Manager(){}
    public Manager(String employeeId, String CCCD, String position, float baseSalary, Date startDate, float allowance, String department) {
        super(employeeId,CCCD, position, baseSalary, startDate);
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
        while (true) {
            try {
                String str = BUS.Validation.getNonEmptyString("Nhap phu cap: ");
                float a = Float.parseFloat(str);
                if (a < 0) { System.out.println("[Loi] Phu cap phai >= 0!"); continue; }
                setAllowance(a);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Phai nhap so! Vui long nhap lai.");
            }
        }
        setDepartment(BUS.Validation.getNonEmptyString("Phong ban: "));
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
        return super.toString() + String.format(" | %-15s | %,12.0f | %,15.0f",
                department, allowance, calculateSalary());
    }
    
    @Override
    public void displayInfo() {
        System.out.println(toString());
    }
}