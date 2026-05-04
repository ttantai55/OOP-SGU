package DTO;

import java.util.Scanner;

public class BrandDTO {
    private String brandID;
    private String brandName;
    private int manufacturingYear;

    Scanner sc = new Scanner(System.in);

    public BrandDTO(){

    }

    public BrandDTO(String brandID, String brandName, int manufacturingYear){
        this.brandID = brandID;
        this.brandName = brandName;
        this.manufacturingYear = manufacturingYear;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }
    
    public void input() {
        System.out.println("===Moi nhap thong tin Thuong hieu===:");
        System.out.println("Moi nhap ma Thuong Hieu:");
        setBrandID(sc.nextLine());
        System.out.println("Moi nhap ten Thuong Hieu:");
        setBrandName(sc.nextLine());
        System.out.println("Moi nhap nam san xuat:");
        setManufacturingYear(Integer.parseInt(sc.nextLine()));
    }
    @Override
    public String toString() {
        return String.format("| %-15s | %-30s | %10d |", brandID, brandName, manufacturingYear);
    }

    public void displayInfo(){
        System.out.println(toString());
    }

    public String toFileString() {
        return brandID + "," + brandName + "," + manufacturingYear;
    }

}
