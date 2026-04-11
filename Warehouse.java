import java.util.Date;

public class Warehouse {
    private String warehouseId;
    private int stockQuantity;
    private Date lastUpdated;
    
    public Warehouse(String warehouseId, int stockQuantity, Date lastUpdated) {
        this.warehouseId = warehouseId;
        this.stockQuantity = stockQuantity;
        this.lastUpdated = lastUpdated;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void displayInfo() {
        System.out.println("Warehouse ID: " + warehouseId + " | Stock: " + stockQuantity + " | Last Updated: " + lastUpdated);
    }
}