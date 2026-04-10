package DTO;

import java.util.Date;

public class Manager extends Employee{
    private float allowance; // phụ cấp
    private String department; // phòng ban
    public Manager(){}
    public Manager(String employeeId, String position, float baseSalary, Date startDate, float allowance, String department) {
        super(employeeId, position, baseSalary, startDate);
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
    public void input(){}
    //****
    public float calculateSalary(){
        return allowance;
    }
    //***
    public String getRole(){
        return department;
    }
    //***
    @Override
    public String toString() {
        return "Manager{" +
                "allowance=" + allowance +
                ", department='" + department + '\'' +
                '}';
    }
}
