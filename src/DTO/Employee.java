package DTO;

import java.util.Date;

public abstract class Employee extends Person {
    protected String employeeId;
    protected String position; //chức vụ
    protected float baseSalary; //lương cơ bản
    protected Date startDate; //ngày vào làm
    public Employee(){}
    public Employee(String employeeId, String position, float baseSalary, Date startDate){
        this.employeeId = employeeId;
        this.position = position;
        this.baseSalary = baseSalary;
        this.startDate = startDate;
    }
    public String getEmployeeId(){
        return employeeId;
    }
    public void setEmployeeId(String employeeId){
        this.employeeId = employeeId;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    //****
    public void input(){}
    public abstract float calculateSalary();//abstract method tính lương
    public abstract String getRole();//abstract method phân quyền

    @Override
    public String toString() {
        return "Employee{" +
                "baseSalary=" + baseSalary +
                ", employeeId='" + employeeId + '\'' +
                ", position='" + position + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
