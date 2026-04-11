package DAO; // Khai báo địa chỉ hiện tại
import DTO.Supplier;

public class SupplierDAO {
    private Supplier[] supplierList; 
    private int count; 

    public SupplierDAO() {
        this.supplierList = new Supplier[100];
        this.count = 0;
    }

    public SupplierDAO(Supplier[] supplierList, int count) {
        this.supplierList = supplierList;
        this.count = count;
    }

    public Supplier[] getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(Supplier[] supplierList) {
        this.supplierList = supplierList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}