package DTO;

import java.util.Scanner;

public class CpuDTO {
    private String brand;       // Hang san xuat (VD: Intel, AMD, Apple)
    private String model;       //  Ma chip(VD: Core i7-13700H, M3 Max)
    private int coreCount;      // So nhan (VD: 14)
    private int threadCount;    // So Luong (VD: 20)

    Scanner sc = new Scanner(System.in);

    public CpuDTO() {

    }

    public CpuDTO(String brand, String model, int coreCount, int threadCount) {
        this.brand = brand;
        this.model = model;
        this.coreCount = coreCount;
        this.threadCount = threadCount;
    }

    // --- Getters và Setters ---
    public String getBrand() { 
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand; 
    }

    public String getModel() {
        return model; 
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCoreCount() {
        return coreCount; 
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }


    public void input() {
        System.out.println("--- Nhập chi tiết thông số CPU ---");
        System.out.print("Moi nhap hang san xuat (VD: Intel, AMD): ");
        setBrand(sc.nextLine());
        
        System.out.print("Moi nhap ma CPU (VD: Core i7-13700H): ");
        setModel(sc.nextLine());
        
        System.out.print("Moi nhap so nhan (Core): ");
        setCoreCount(Integer.parseInt(sc.nextLine()));
        
        System.out.print("Moi nhap so luong (Thread): ");
        setThreadCount(Integer.parseInt(sc.nextLine()));
        
    }

    // --- Hàm xuất chuỗi ---
   /*  @Override
    public String toString() {
        // Gom tất cả lại thành một chuỗi đẹp mắt. 
        return String.format("%s %s (%dC/%dT, %.1fGHz)", 
                brand, model, coreCount, threadCount);
    }
    */
}