package DTO;

import java.util.Date;
import java.util.Scanner;

public class TechnicianEmployee extends Employee {
    private int repairCount;// số lượng sửa chữa
    private float expenditure;//tiền chỉ tiêu
    static Scanner sc = new Scanner(System.in);
    public TechnicianEmployee() {}

    public TechnicianEmployee(String employeeId,String CCCD, String position, float baseSalary, Date startDate, float expenditure, int repairCount) {
        super(employeeId,CCCD, position, baseSalary, startDate);
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
        while (true) {
            try {
                String str = BUS.Validation.getNonEmptyString("So luong san pham da sua chua: ");
                int count = Integer.parseInt(str);
                if (count < 0) { System.out.println("[Loi] So luong phai >= 0!"); continue; }
                setRepairCount(count);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Phai nhap so nguyen! Vui long nhap lai.");
            }
        }
        while (true) {
            try {
                String str = BUS.Validation.getNonEmptyString("Tien chi tieu: ");
                float exp = Float.parseFloat(str);
                if (exp < 0) { System.out.println("[Loi] Chi tieu phai >= 0!"); continue; }
                setExpenditure(exp);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Loi] Phai nhap so! Vui long nhap lai.");
            }
        }
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
