package DTO;

import java.util.Date;

public class SalesEmployee extends Employee {
    private float bonus; // tiền thưởng
    private float commissionAmount;//tiền hoa hồng
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

    public void input(){}
    //****
    public float calculateSalary(){
        return baseSalary + commissionAmount;
    }
    //****
    public String getRole(){
        return "Sales";
    }
}
