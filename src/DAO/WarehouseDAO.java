package DAO; // Khai báo địa chỉ hiện tại
import DTO.Warehouse; // Import để gọi Warehouse từ DTO sang

public class WarehouseDAO {
    private Warehouse[] warehouseList;
    private int count;

    public WarehouseDAO() {
        this.warehouseList = new Warehouse[100];
        this.count = 0;
    }

    public WarehouseDAO(Warehouse[] warehouseList, int count) {
        this.warehouseList = warehouseList;
        this.count = count;
    }

    public Warehouse[] getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(Warehouse[] warehouseList) {
        this.warehouseList = warehouseList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}