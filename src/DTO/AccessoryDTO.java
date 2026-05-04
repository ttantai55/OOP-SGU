package DTO;

public class AccessoryDTO extends ProductsDTO {
    private String type;
    private String description;

    public AccessoryDTO() {
        //Tu dong gan phu kien vao phan Danh Muc
        CategoryDTO cate = new CategoryDTO();
        cate.setCategoryName("Phukien");
    } 
    public AccessoryDTO(String productIMEI, String productID, CategoryDTO category, BrandDTO brand, String productName, double price, int warrantyPeriod, String origin, String type, String description, boolean status) {
        super(productIMEI,productID,category,brand,productName,price,warrantyPeriod,origin,status);
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

    @Override
    public String getSpecSummary(){
        return this.type +"-"+ this.description;
    }

    @Override
    public String toFileString() {
        // Chữ "Accessory" ở đầu tiên chính là Cờ hiệu phân loại
        return "Accessory," + super.toFileString() + "," + type + "," + description;
    }
}