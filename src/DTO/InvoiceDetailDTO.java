package DTO;
public class InvoiceDetailDTO {

    private ProductDTO product;
    private int quantity;
    private double unitPrice;
    private WarrantyDTO warranty;
    private PromotionDTO promotion;

    public InvoiceDetailDTO() {

    }
    public InvoiceDetailDTO(ProductDTO product, int quantity, double unitPrice, WarrantyDTO warranty, PromotionDTO promotion) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warranty = warranty;
        this.promotion = promotion;
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

public double getUnitPrice() {
    return unitPrice;
 }

public void setUnitPrice(double unitPrice) {
     this.unitPrice = unitPrice; 
    }

public WarrantyDTO getWarranty() {
     return warranty; 
    }

public void setWarranty(WarrantyDTO warranty) { 
    this.warranty = warranty;
 }

public PromotionDTO getPromotion() {
     return promotion;
     }

public void setPromotion(PromotionDTO promotion) { 
    this.promotion = promotion; 
}

public double getSubTotal() {
    return quantity * unitPrice;
}

}