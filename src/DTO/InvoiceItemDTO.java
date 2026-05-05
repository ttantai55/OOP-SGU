package DTO;

public class InvoiceItemDTO {

    private ProductsDTO product;
    private String invoiceId;
    private int quantity;
    private WarrantyDTO warranty;
    private PromotionDTO promotion; 

    public InvoiceItemDTO() {
    }

    public InvoiceItemDTO(ProductsDTO product, String invoiceId, int quantity,
                            WarrantyDTO warranty, PromotionDTO promotion) {
        this.product = product;
        this.invoiceId = invoiceId;
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
        if (this.product != null) return product.getProductName();
        return "N/A";
    }
    public String getProductId(){
        if (this.product != null) return product.getProductID();
        return "N/A";
    }

    public String getInvoiceId(){
        return invoiceId;
    }
    public void setInvoiceId(String invoiceId){
        this.invoiceId = invoiceId;
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
        if (this.product != null) return product.getPrice();
        return 0;
    }

    // phần bảo hành sẽ lấy theo Id của mã bảo hành 

    public WarrantyDTO getWarranty() {
        return warranty;
    }
    public void setWarranty(WarrantyDTO warranty){
        this.warranty = warranty;
    }

    public String getWarrantyId(){
        if (this.warranty != null) {
            return this.warranty.getWarrantyId();
        }
        return "N/A";
    }

   public PromotionDTO getPromotion(){
    return promotion;
   }
   public void setPromotion(PromotionDTO promotion){
    this.promotion = promotion;
   }
   public String getPromotionId(){
    if (this.promotion != null) {
        return this.promotion.getPromotionId();
    }
    return "N/A";
   }

    public double getDiscountPercent(){
        if (promotion != null) {
            return promotion.getDiscountPercent();
        }
        return 0;
    }
    


}
