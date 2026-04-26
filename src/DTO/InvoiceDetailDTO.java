package DTO;

public class InvoiceDetailDTO {

    private ProductsDTO product;
    private int quantity;
    private WarrantyDTO warranty;
    private PromotionDTO promotion; 

    public InvoiceDetailDTO() {
    }

    public InvoiceDetailDTO(ProductsDTO product, int quantity,
                            WarrantyDTO warranty, PromotionDTO promotion) {
        this.product = product;
        this.quantity = quantity;
        this.warranty = warranty;
        this.promotion = promotion;
    }

    public ProductsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductsDTO product) {
        this.product = product;
    }



    // lấy tên và id của sản phẩm đó theo ProductsDTO
    public String getProductName(){
        return product.getProductName();
    }
    public String getProductId(){
        return product.getProductID();
    }


    // số lượng của chi tiết đó
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // lấy giá của sản phẩm đó theo ProductsDTO
    public double getUnitPrice() {
        return product.getPrice();
    }



    // phần bảo hành sẽ lấy theo Id của mã bảo hành 
    public WarrantyDTO getWarranty() {
        return warranty;

    }

    public String getWarrantyId() {
        return warranty.getWarrantyId();
    }


    // phần này tương tự mã bảo hành ( khuyến mãi)
    public PromotionDTO getPromotion() {
        return promotion;
    }

    public String getPromotionId() {
        return promotion.getPromotionId();
    }

    public double getDiscountPercent(){
        return promotion.getDiscountPercent();
    }
    

  
// tính tổng tiền các chi tiết
// ví dụ: quantity = 1, unitPrice=100,000, discountPercent=10
// subTotal = 1 * 100,000 * (1 - 10/100) = 90,000

    public double getSubTotal() {
        double total = quantity * product.getPrice();
        if (promotion != null) {
            total = total * (1 - promotion.getDiscountPercent() / 100.0);
        }
        return total;
    }

}