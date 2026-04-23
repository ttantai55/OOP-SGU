package DTO;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String contactPhone;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String contactPhone) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactPhone = contactPhone;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public void displayInfo() {
        System.out.println("Supplier ID: " + supplierId + " | Name: " + supplierName + " | Phone: " + contactPhone);
    }
}