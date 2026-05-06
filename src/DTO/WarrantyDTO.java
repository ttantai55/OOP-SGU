package DTO;
import java.util.Date;

public class WarrantyDTO {

    private String warrantyId;
    private String invoiceId;
    private ProductsDTO product;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private int repairCount;

    public WarrantyDTO() {
        this.repairCount = 0;
        this.status = true;
    }

    public WarrantyDTO(String warrantyId, String invoiceId, ProductsDTO product,
                       Date startDate, Date endDate, boolean status) {
        this.warrantyId = warrantyId;
        this.invoiceId = invoiceId;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.repairCount = 0;
    }

    public String getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(String warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ProductsDTO getProduct() {
        return product;
    }

    public void setProduct(ProductsDTO product) {
        this.product = product;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public int getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(int repairCount) {
        this.repairCount = repairCount;
    }
}