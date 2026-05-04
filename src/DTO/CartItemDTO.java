package DTO;

public class CartItemDTO {
    private String username;
    private String productID;
    private String productName;
    private double price;
    private int quantity;

    public CartItemDTO() {
    }

    public CartItemDTO(String username, String productID, String productName, double price, int quantity) {
        this.username = username;
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return price*quantity;
    }

    public String toFileString() {
        return username + " ," + productID + " ," + productName + " ," + price + " ," + quantity;
    }
}
