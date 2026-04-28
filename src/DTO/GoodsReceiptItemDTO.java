package DTO;

public class GoodsReceiptItemDTO {

    private ProductsDTO product;
    private String receiptId;
    private int quantity;
    private double importPrice; // giá nhập

    public GoodsReceiptItemDTO() {
    }

    public GoodsReceiptItemDTO(ProductsDTO product, String receiptId, int quantity, double importPrice) {
        this.product = product;
        this.receiptId = receiptId;
        this.quantity = quantity;
        this.importPrice = importPrice;
    }

    public ProductsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductsDTO product) {
        this.product = product;
    }

    // lấy thông tin từ ProductsDTO
    public String getProductId() {
        return product.getProductID();
    }

    public String getProductName() {
        return product.getProductName();
    }

    public String getReceiptId(){
        return receiptId;
    }
    public void setReceiptId(String receiptId){
        this.receiptId = receiptId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }
}
