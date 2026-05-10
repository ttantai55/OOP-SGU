package DTO;
import BUS.Validation;
import java.util.Scanner;

public class ProductsDTO {
    private String productIMEI; // duy nhat 
    private String productID;   // quan ly dong may
    private CategoryDTO category;
    private BrandDTO brand;
    private String productName;
    private double price;
    private int warrantyPeriod;
    private String origin;
    private boolean status;

    static Scanner sc = new Scanner(System.in);

    //Khoi tao de oj de tranh bi loi NULL khi goi ham input
    public ProductsDTO(){
        this.category = new CategoryDTO();
        this.brand = new BrandDTO();
    }

    public ProductsDTO(String productIMEI, String productID, CategoryDTO category, BrandDTO brand, String productName, double price, int warrantyPeriod, String origin, boolean status){
        this.brand = brand;
        this.category = category;
        this.origin = origin;
        this.price = price;
        this.productID = productID;
        this.productIMEI = productIMEI;
        this.productName = productName;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
  
    
    
     public void input(){

        System.out.println("Moi nhap ten San Pham:");
        setProductName(sc.nextLine());

        setPrice(Validation.inputPrice(sc));

        this.category.input();
        this.brand.input();
        
        System.out.println("Moi nhap thoi gian bao hanh cua san pham:");
        setWarrantyPeriod(Validation.inputPositiveInt(sc));
        System.out.println("Moi nhap nguon goc san pham: ");
        setOrigin(sc.nextLine());

    }
    
    @Override
    public String toString() {
        String statusText = this.status ? "Dang ban" : "Tam ngung";
        String productsFormat = "%-8s|%-8s|%-11s|%-10s|%-20s|%,15.2f|%8d|%-10s|%-10s";
        // Dùng %,15.2f cho price (double) và %10d cho warrantyPeriod (int)
        return String.format(productsFormat, 
                productIMEI, productID, getCategory().getCategoryName(), getBrand().getBrandName(), productName, price, warrantyPeriod, origin, statusText);
    }

    public void displayInfo(){
        System.out.println(toString());
    }


    public String getSpecSummary() {
        return "Khong co thong tin!";
    }

    // Noi chuoi de Doc hoac Xuat File
    public String toFileString() {
        String statusText = this.status ? "Dang ban" : "Tam ngung";
        return productIMEI + "," + productID + "," + category.toFileString() + "," + brand.toFileString() + "," + productName + "," + price + "," + warrantyPeriod + "," + origin + "," + statusText;
    }

   
}
