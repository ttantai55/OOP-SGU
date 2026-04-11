package DTO;

public class BrandDTO {
    private String brandID;
    private String brandName;
    private int manufacturingYear;

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

    @Override
    public String toString() {
        return String.format("| %-15s | %-30s | %10d |", brandID, brandName, manufacturingYear);
    }

    public void displayInfo(){
        System.out.println(toString());
    }

}
