package DTO;

import java.util.Scanner;

public class ScreenDTO {
    private double size;       // Kich thuoc inch (VD: 15.6)
    private String resolution; // Do phan giai (VD: Full HD, 2K)

    Scanner sc = new Scanner(System.in);

    public ScreenDTO() {

    }

    public ScreenDTO(double size, String resolution) {
        this.size = size;
        this.resolution = resolution;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

   
    public void input() {
        System.out.println("--- Nhập thông tin Màn hình ---");
        System.out.print("Moi nhap kich thuoc (Inch - VD: 15.6): ");
        setSize(Double.parseDouble(sc.nextLine()));
        
        System.out.print("Moi nhap do phan giai (VD: 1920x1080 or Full HD): ");
        setResolution(sc.nextLine());
    }

    /*// --- Xuất chuỗi ---
    @Override
    public String toString() {
        // Kết quả VD: "15.6 inch (Full HD)"
        return size + " inch (" + resolution + ")";
    }
        */
}