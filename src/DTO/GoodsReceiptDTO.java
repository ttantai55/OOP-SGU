package DTO;

import java.util.Date;

public class GoodsReceiptDTO {

    private String receiptId;
    private Date createdDate;
    private String creator;           // người tạo phiếu
    private String note;
    private String courier;           // bên giao hàng
    private String consignee;         // bên nhận hàng
    private String phoneOfCourier;
    private String phoneOfConsignee;
    private Supplier supplier;
    private Employee receiver;        // nhân viên nhận hàng
    private GoodsReceiptItemDTO[] items;
    private boolean status;


    public GoodsReceiptDTO() {
        this.items = new GoodsReceiptItemDTO[0]; // một chi tiết Sẽ là 1 phần tử trong mảng
        this.status = true;
    }

    public GoodsReceiptDTO(String receiptId, Date createdDate, String creator, String note,
                           String courier, String consignee,
                           String phoneOfCourier, String phoneOfConsignee,
                           Supplier supplier, Employee receiver, boolean status) {
        this.receiptId = receiptId;
        this.createdDate = createdDate;
        this.creator = creator;
        this.note = note;
        this.courier = courier;
        this.consignee = consignee;
        this.phoneOfCourier = phoneOfCourier;
        this.phoneOfConsignee = phoneOfConsignee;
        this.supplier = supplier;
        this.receiver = receiver;
        this.status = status;
        this.items = new GoodsReceiptItemDTO[0];
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
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

    // nhà cung cấp ( lây id , tên)
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getSupplierId() {
        if (this.supplier != null) return this.supplier.getSupplierId();
        return "N/A";
    }

    public String getSupplierName() {
        if (this.supplier != null) return this.supplier.getSupplierName();
        return "N/A";
    }

    // nhân viên nhận hàng (id và tên)
    public Employee getReceiver() {
        return receiver;
    }

    public void setReceiver(Employee receiver) {
        this.receiver = receiver;
    }

    public String getReceiverId() {
        if (this.receiver != null) return this.receiver.getEmployeeId();
        return "N/A";
    }

    public String getReceiverName(){
<<<<<<< HEAD
<<<<<<< HEAD
        return receiver.getEmployeeName();
=======
        return receiver.getFullName();
>>>>>>> 4ecd6559923f5f69a0c620bc55b27768888167e5
=======
        if (this.receiver != null) return this.receiver.getFullName();
        return "N/A";
>>>>>>> origin
    }


    // danh sách các mặt hàng nhập vào
    public GoodsReceiptItemDTO[] getItems() {
        return items;
    }

    public void setItems(GoodsReceiptItemDTO[] items) {
        this.items = items;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
