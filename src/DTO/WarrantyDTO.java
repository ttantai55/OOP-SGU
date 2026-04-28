package DTO;
import java.util.Date;

public class WarrantyDTO {

    private String warrantyId;
    private String invoiceId;
    private ProductsDTO product;
    private Date startDate;
    private Date endDate;
    private String status;
    private RepairRecordDTO[] repairRecordList; 
    //số lần bảo hành. 
    // Cái này cũng giống như Invoice, tạo 1 mảng để lưu nhiều lần bảo hành, không phải list quản lí Warranty
    private int repairCount;

    public WarrantyDTO() { 
        this.repairRecordList = new RepairRecordDTO[0];
        this.repairCount = 0;
    }

    public WarrantyDTO(String warrantyId, String invoiceId, ProductsDTO product,
                       Date startDate, Date endDate, String status) {
        this.warrantyId = warrantyId;
        this.invoiceId = invoiceId;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.repairRecordList = new RepairRecordDTO[0];
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public RepairRecordDTO[] getRepairRecordList() {
        return repairRecordList;
    }

    public void setRepairRecordList(RepairRecordDTO[] repairRecordList) {
        this.repairRecordList = repairRecordList;
    }

    public int getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(int repairCount) {
        this.repairCount = repairCount;
    }
}