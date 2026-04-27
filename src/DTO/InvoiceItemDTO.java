package DTO;

public class InvoiceItemDTO {

    private ProductsDTO product;
    private int quantity;
    private WarrantyDTO warranty;
    private PromotionDTO promotion; 

    public InvoiceItemDTO() {
    }

    public InvoiceItemDTO(ProductsDTO product, int quantity,
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
    


}
