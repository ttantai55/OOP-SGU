package DTO;

import java.util.Date;
import java.util.Scanner;

public class TechnicianEmployee extends Employee {
    private int repairCount;// số lượng sửa chữa
    private float expenditure;//tiền chỉ tiêu
    static Scanner sc = new Scanner(System.in);
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
    @Override
    public void input(){
        super.input();
        System.out.println("so luong san pham da sua chua: ");
        setRepairCount(Integer.parseInt(sc.nextLine()));
        System.out.println(" Tien chi tieu");//cần thêm method check chỉ tiêu
        setExpenditure(Float.parseFloat(sc.nextLine()));

    }
    //****
    @Override
    public float calculateSalary(){
        return getBaseSalary() + (getRepairCount()*50000f) + expenditure;
    }
    //****
    @Override
    public String getRole(){
        return "nhan vien ky thuat";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %-10d | %,12.0f | %,15.0f",
                repairCount, expenditure, calculateSalary());
    }
    public void displayInfo() {
        System.out.println(toString());
    }
}
