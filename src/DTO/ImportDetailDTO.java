package DTO;

public class ImportDetailDTO {

    private ProductsDTO product;
    private int quantity;
    private double importPrice; // giá nhập 

    public ImportDetailDTO() {
    }

    public ImportDetailDTO(ProductsDTO product, int quantity, double importPrice) {
        this.product = product;
        this.quantity = quantity;
        this.importPrice = importPrice;
    }

    public ProductsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductsDTO product) {
        this.product = product;
    }

    // lấy  thông tin từ ProductsDTO
    public String getProductId() {
        return  product.getProductID();
    }

    public String getProductName() {
        return product.getProductName();
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

    // Tính thành tiền nhập của dòng này
    public double getSubTotal() {
        return quantity * importPrice;
    }
}
