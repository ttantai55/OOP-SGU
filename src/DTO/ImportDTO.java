package DTO;

import java.util.Date;

public class ImportDTO {

    private String importId;
    private Date createdDate;
    private String note;
    private String courier;          // bên giao hàng
    private String consignee;        // bên nhận hàng
    private String phoneOfCourier;
    private String phoneOfConsignee;
    private Supplier supplier;
    private Employee receiver;    // nhân viên nhận hàng
    private ImportDetailDTO[] importDetails;

    public ImportDTO() {
    }

    public ImportDTO(String importId, Date createdDate, String note,
                     String courier, String consignee,
                     String phoneOfCourier, String phoneOfConsignee,
                     Supplier supplier, Employee receiver) {
        this.importId = importId;
        this.createdDate = createdDate;
        this.note = note;
        this.courier = courier;
        this.consignee = consignee;
        this.phoneOfCourier = phoneOfCourier;
        this.phoneOfConsignee = phoneOfConsignee;
        this.supplier = supplier;
        this.receiver = receiver;
        this.importDetails = new ImportDetailDTO[0]; 
    }

 
    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhoneOfCourier() {
        return phoneOfCourier;
    }

    public void setPhoneOfCourier(String phoneOfCourier) {
        this.phoneOfCourier = phoneOfCourier;
    }

    public String getPhoneOfConsignee() {
        return phoneOfConsignee;
    }

    public void setPhoneOfConsignee(String phoneOfConsignee) {
        this.phoneOfConsignee = phoneOfConsignee;
    }


    // nhà cung cấp ( lấy tên , id và sdt)
    public SupplierDTO getSupplier() {
        return supplier;
    }

    public String getSupplierId() {
        return supplier.getSupplierId();
    }

    public String getSupplierName() {
        return supplier.getSupplierName(); 
    }

    public String getSupplierPhone() {
        return supplier.getContactPhone();
    }



    // nhân viên nhận hàng (tên, mã , sdt)
    public EmployeeDTO getReceiver() {
        return receiver;
    }

    public String getReceiverId() {
        return receiver.getEmployeeId();
    }

    public String getReceiverName() {
        return receiver.getFullName();
    }

    public ImportDetailDTO[] getImportDetails() {
        return importDetails;
    }

    public void setImportDetails(ImportDetailDTO[] importDetails) {
        this.importDetails = importDetails;
    }

    // Tính tổng tiền nhập: chạy vòng lặp cộng tất cả subTotal của từng dòng
    // VD: detail[0].subTotal=30tr + detail[1].subTotal=20tr => totalPrice=50tr
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < importDetails.length; i++) {
            total += importDetails[i].getSubTotal();
        }
        return total;
    }
}