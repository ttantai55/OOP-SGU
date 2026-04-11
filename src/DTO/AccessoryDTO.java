package DTO;

public class AccessoryDTO extends ProductsDTO {
    private String type;
    private String description;

    public AccessoryDTO() {

    } 
    public AccessoryDTO(String productIMEI, String productID, String categoryID, String brandID, String productName, double price, int warrantyPeriod, String origin, String type, String description) {
        super(productIMEI,productID,categoryID,brandID,productName,price,warrantyPeriod,origin);
        this.description = description;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void input() {
        super.input();
        System.out.println("Moi nhap loai san pham:");
        setType(sc.nextLine());
        System.out.println("Moi nhap Mo ta ve san pham:");
        setDescription(sc.nextLine());
    }

    @Override
    public String toString() {
        String accessoryFormat = " %-20s | %-40s |";
        
        return super.toString() + String.format(accessoryFormat, type, description);
    }


    @Override
    public void displayInfo() {
        System.out.println(toString());
    }
}
