package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Employee extends Person {
    protected String employeeId;
    protected String CCCD;
    protected String position; //chức vụ
    protected float baseSalary; //lương cơ bản
    protected Date startDate;//ngày vào làm


    public Employee(){}
    
    public Employee(String employeeId,String CCCD,String position, float baseSalary, Date startDate){
        this.employeeId = employeeId;
        this.CCCD = CCCD;
        this.position = position;
        this.baseSalary = baseSalary;
        this.startDate = startDate;
    }
    public String getEmployeeId(){
        return employeeId;
    }
    public String getCCCD(){
        return CCCD;
    }
    public void setCCCD(String CCCD){
        this.CCCD = CCCD;
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
    @Override
    public void input(){
        super.input();
        setEmployeeId(BUS.Validation.getNonEmptyString("Ma nhan vien: "));
        setCCCD(BUS.Validation.getValidCCCD("số CCCD: "));
        setPosition(BUS.Validation.getNonEmptyString("Chuc vu: "));
        while (true) {
            try {
                String salaryStr = BUS.Validation.getNonEmptyString("Nhap luong co ban: ");
                float salary = Float.parseFloat(salaryStr);
                if (salary < 0) {
                    System.out.println("[Loi] Luong phai lon hon hoac bang 0!");
                    continue;
                }
                setBaseSalary(salary);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Luong phai la so! Vui long nhap lai.");
            }
        }
        System.out.println("Ngay vao lam: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        this.startDate = new Date();
    }
    public abstract float calculateSalary();//abstract method tính lương
    public abstract String getRole();//abstract method phân quyền

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return super.toString() + String.format(" | %-10s | %-14s | %-15s | %,15.0f | %-12s",
                employeeId, CCCD, position, baseSalary, sdf.format(startDate));
    }
    // Returns only the common fields (Person + Employee), without subclass-specific fields
    public String toCommonString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return super.toString() + String.format(" | %-10s | %-14s | %-15s | %,15.0f | %-12s",
                employeeId, CCCD, position, baseSalary, sdf.format(startDate));
    }

    @Override
    public void displayInfo() {
        System.out.println(toString());
    }
}

