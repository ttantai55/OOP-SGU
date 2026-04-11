package DTO;
import java.util.Scanner;

public class ProductsDTO {
    private String productIMEI;
    private String productID;
    private String categoryID;
    private String brandID;
    private String productName;
    private double price;
    private int warrantyPeriod;
    private String origin;

    Scanner sc = new Scanner(System.in);

    public ProductsDTO(){

    }

    public ProductsDTO(String productIMEI, String productID, String categoryID, String brandID, String productName, double price, int warrantyPeriod, String origin){
        this.brandID = brandID;
        this.categoryID = categoryID;
        this.origin = origin;
        this.price = price;
        this.productID = productID;
        this.productIMEI = productIMEI;
        this.productName = productName;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getProductIMEI() {
        return productIMEI;
    }

    public void setProductIMEI(String productIMEI) {
        this.productIMEI = productIMEI;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    
    
    public void input(){
        System.out.println("Moi nhap Thong tin cho san pham: ");
        System.out.println("Moi nhap ma IMEI:");
        setProductIMEI(sc.nextLine());
        System.out.println("Moi nhap ma San Pham:");
        setProductID(sc.nextLine());
        System.out.println("Moi nhap ten San Pham:");
        setProductName(sc.nextLine());
        System.out.println("Moi nhap ma Danh Muc:");
        setCategoryID(sc.nextLine());
        System.out.println("Moi nhap ma Thuong Hieu:");
        setBrandID(sc.nextLine());
        System.out.println("Moi nhap Gia San Pham:");
        setPrice(Double.parseDouble(sc.nextLine()));
        System.out.println("Moi nhap nam San Xuat:");
        setWarrantyPeriod(Integer.parseInt(sc.nextLine()));
        System.out.println("Moi nhap nguon goc san pham: ");
        setOrigin(sc.nextLine());

    }
    
    @Override
    public String toString() {
        String productsFormat = " %-16s | %-10s | %-11s | %-10s | %-30s | %,15.2f | %10d | %-15s |";
        // Dùng %,15.2f cho price (double) và %10d cho warrantyPeriod (int)
        return String.format(productsFormat, 
                productIMEI, productID, categoryID, brandID, productName, price, warrantyPeriod, origin);
    }

    public void displayInfo(){
        System.out.println(toString());
    }

    
}
