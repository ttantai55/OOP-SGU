package DTO;

import java.util.Date;

public class TechnicianEmployee extends Employee {
    private int repairCount;// số lượng sửa chữa
    private float expenditure;//tiền chỉ tiêu

    public TechnicianEmployee() {}

    public TechnicianEmployee(String employeeId, String position, float baseSalary, Date startDate, float expenditure, int repairCount) {
        super(employeeId, position, baseSalary, startDate);
        this.expenditure = expenditure;
        this.repairCount = repairCount;
    }

    public float getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(float expenditure) {
        this.expenditure = expenditure;
    }

    public int getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(int repairCount) {
        this.repairCount = repairCount;
    }

    //****
    public void input(){}
    //****
    public float calculateSalary(){
        return repairCount;
    }
    //****
    public String getRole(){
        return "Technician";
    }

    @Override
    public String toString() {
        return "TechnicianEmployee{" +
                "expenditure=" + expenditure +
                ", repairCount=" + repairCount +
                '}';
    }
}
