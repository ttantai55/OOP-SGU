package DTO;

import java.util.Scanner;

public class CategoryDTO {
    private String categoryID;
    private String categoryName;

    Scanner sc = new Scanner(System.in);

    public CategoryDTO(){
        
    }

    public CategoryDTO(String categoryID, String categoryName){
        this.categoryID = categoryID;
        this.categoryName = categoryName;

    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public void input(){
        System.out.println("====Moi nhap Thong tin danh muc===");
        System.out.println("Moi nhap ma Danh Muc:");
        setCategoryID(sc.nextLine());
    }

    @Override
    public String toString() {
        // Căn trái 15 ký tự cho ID và 30 ký tự cho Name
        return String.format("| %-15s | %-30s |", categoryID, categoryName);
    }

    // Đổi kiểu trả về thành void để in ra giống các class trước
    public void displayInfo() {
        System.out.println(toString());
    }

    public String toFileString() {
        return categoryID + "," + categoryName;
    }
}
