package DTO;

// [OOP] Class: Ban thiet ke cho doi tuong Nha Cung Cap
public class Supplier {

    // [OOP] Encapsulation: Bao ve thuoc tinh bang 'private'
    private String supplierId;
    private String supplierName;
    private String contactPhone;
    private String email;

    // 1. Constructor mac dinh
    public Supplier() {
    }

    // 2. Constructor day du tham so
    public Supplier(String supplierId, String supplierName, String contactPhone, String email) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactPhone = contactPhone;
        this.email = email;
    }

    // [OOP] Encapsulation: Getter va Setter
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayInfo() {
        System.out.println(toString());
    }

    // [OOP] Polymorphism: Ghi de phuong thuc toString() cua lop cha Object
    @Override
    public String toString() {
        // Canh le trai bang dau tru (-), giu khung hien thi co dinh
        String supplierFormat = "%-10s|%-25s|%-15s|%-30s";
        return String.format(supplierFormat, supplierId, supplierName, contactPhone, email);
    }

    public void input() {
        System.out.println("\n--- NHAP THONG TIN NHA CUNG CAP ---");

        // Dung Validation de ep nguoi dung khong duoc bo trong va nhap dung dinh dang
        setSupplierId(BUS.Validation.getNonEmptyString("Moi nhap ID nha cung cap: "));
        setSupplierName(BUS.Validation.getNonEmptyString("Moi nhap ten nha cung cap: "));
        setContactPhone(BUS.Validation.getValidPhone("Moi nhap so dien thoai (10 hoac 11 so): "));
        setEmail(BUS.Validation.getValidEmail("Moi nhap email (Bat buoc duoi @gmail.com): "));
    }
}