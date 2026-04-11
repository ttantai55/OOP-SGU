package DTO;

import java.util.Scanner;

public class RamDTO {
    private int capacity; // Dung luong tinh bang GB (VD: 8, 16)
    private String type;  // Chuẩn RAM (VD: DDR4, DDR5)

    Scanner sc = new Scanner(System.in);

    public RamDTO() {
    }

    public RamDTO(int capacity, String type) {
        this.capacity = capacity;
        this.type = type;
    }

    // --- Getters & Setters ---
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    


    // --- Nhập liệu ---
    public void input() {
        System.out.println("--- Nhập thông tin RAM ---");
        System.out.print("Moi nhap dung luong ram (GB - VD: 16): ");
        setCapacity(Integer.parseInt(sc.nextLine()));
        System.out.print("moi nhap loai Ram(VD: DDR5): ");
        setType(sc.nextLine());
    }

   /*  // --- Xuất chuỗi ---
    @Override
    public String toString() {
        // Kết quả VD: "16GB DDR5"
        return capacity + "GB " + type; 
    }
        */
}