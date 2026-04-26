package DTO;

import java.util.Date;

public class WarrantyDTO {

    private String warrantyId;
    private ProductsDTO product;
    private Date startDate;
    private Date endDate;
    private WarrantyHistoryDTO[] warrantyHistory;

    public WarrantyDTO() {
    }

    public WarrantyDTO(String warrantyId, ProductsDTO product, Date startDate, Date endDate) {
        this.warrantyId = warrantyId;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.warrantyHistory = new WarrantyHistoryDTO[0];
    }

  
    public String getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(String warrantyId) {
        this.warrantyId = warrantyId;
    }

    public ProductsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductsDTO product) {
        this.product = product;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public WarrantyHistoryDTO[] getWarrantyHistory() {
        return warrantyHistory;
    }

    public void setWarrantyHistory(WarrantyHistoryDTO[] warrantyHistory) {
        this.warrantyHistory = warrantyHistory;
    }
}