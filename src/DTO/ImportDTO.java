package DTO;

import java.util.Date;

public class ImportDTO{
    private String importId;
    private Date createdDate;
    private String creator; // người tạo hóa đơn ( thường là người bên cung cấp tạo)
    private String note;
    private String courier; // bên giao hàng
    private String consignee; // bên nhận hàng
    private String phoneOfCourier;
    private String phoneOfConsignee;
    private SupplierDTO supplier;
    private EmployeeDTO receiver; // nhân viên nhận hàng
    private double totalPrice; // tổng tiền hàng
    private ImportDetailDTO[] importDetails;

    public ImportDTO() {
    }

    public ImportDTO(String importId, Date createdDate, String creator, String note, String courier, String consignee, String phoneOfCourier, String phoneOfConsignee, SupplierDTO supplier, EmployeeDTO receiver, double totalPrice, ImportDetailDTO[] importDetails) {
        this.importId = importId;
        this.createdDate = createdDate;
        this.creator = creator;
        this.note = note;
        this.courier = courier;
        this.consignee = consignee;
        this.phoneOfCourier = phoneOfCourier;
        this.phoneOfConsignee = phoneOfConsignee;
        this.supplier = supplier;
        this.receiver = receiver;
        this.totalPrice = totalPrice;
        this.importDetails = importDetails;
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

public String getCreator() {
    return creator;
}

public void setCreator(String creator) {
    this.creator = creator;
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

public SupplierDTO getSupplier() {
    return supplier;
}

public void setSupplier(SupplierDTO supplier) {
    this.supplier = supplier;
}

public EmployeeDTO getReceiver() {
    return receiver;
}

public void setReceiver(EmployeeDTO receiver) {
    this.receiver = receiver;
}




// tính tổng tất cả trên hóa đơn, tui chưa code phần tự tính nên để như này
public double getTotalPrice() {
    return totalPrice;
}

public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
}




public ImportDetailDTO[] getImportDetails() {
    return importDetails;
}

public void setImportDetails(ImportDetailDTO[] importDetails) {
    this.importDetails = importDetails;
}

}