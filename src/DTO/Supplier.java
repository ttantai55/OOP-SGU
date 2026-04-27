package DTO;

import java.util.Scanner;

public class Supplier {
    

    private String supplierId;
    private String supplierName;
    private String contactPhone;
    private String email;

    static Scanner sc = new Scanner(System.in);

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String contactPhone, String email) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactPhone = contactPhone;
        this.email = email;
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
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayInfo() {
      System.out.println(toString());
    }

    public String toString(){
        String supplierFormat = "%-15s|%-15s|%-15s|%-20s|";
        return String.format(supplierFormat, supplierId, supplierName, contactPhone, email);
    }

    public void input(){
        System.out.println("Moi nhap thong tin nha cung cap:");
        System.out.println("Moi nhap ID nha cung cap:");    
        setSupplierId(sc.nextLine());
        System.out.println("Moi nhap ten nha cung cap:");
        setSupplierName(sc.nextLine());
        System.out.println("Moi nhap so dien thoai nha cung cap:");
        setContactPhone(sc.nextLine());
        System.out.println("Moi nhap email nha cung cap:");
        setEmail(sc.nextLine());
    }

}