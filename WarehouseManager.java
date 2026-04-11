public class WarehouseManager implements IInventoryService {
    private Warehouse[] warehouseList;
    private int count;

    public WarehouseManager() {
        this.warehouseList = new Warehouse[100];
        this.count = 0;
    }

    public WarehouseManager(Warehouse[] warehouseList, int count) {
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

    @Override
    public int checkStock(String productId) {
        return 0; 
    }
}