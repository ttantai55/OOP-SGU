package DTO;

import java.util.Date;

public class PromotionDTO {
   
    private String promotionId;
    private String programName;
    private String productID;
    private Date startDate;
    private Date endDate;
    private String condition;       // điều kiện áp dụng
    private double discountPercent;  // nhập theo số ví dụ như 10 = 10%
    private boolean status;

    public PromotionDTO() {
        this.status = true;
    }

    public PromotionDTO(String promotionId, String programName, String productID, Date startDate, Date endDate,
                        String condition, double discountPercent, boolean status) {
        this.promotionId = promotionId;
        this.programName = programName;
        this.productID = productID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.condition = condition;
        this.discountPercent = discountPercent;
        this.status = status;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}