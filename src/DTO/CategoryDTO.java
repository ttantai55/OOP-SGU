package DTO;

public class CategoryDTO {
    private String categoryID;
    private String categoryName;

    public CategoryDTO(){

    }

    public CategoryDTO(String categoryID, String categoryName){
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}
