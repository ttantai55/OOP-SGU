package DTO;

public class ImportDetailDTO {

    private ProductDTO product;
    private int quantity;
    private double importPrice;


    public ImportDetailDTO() {
    }

    public ImportDetailDTO(ProductDTO product, int quantity, double importPrice) {
        this.product = product;
        this.quantity = quantity;
        this.importPrice = importPrice;
    }

    public ProductDTO getProduct() {
        return product;
    }

     public void setProduct(ProductDTO product) {
        this.product = product;
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
// tổng tiền nhập hàng
    public double getSubTotal() {
        return quantity * importPrice;
    }
}