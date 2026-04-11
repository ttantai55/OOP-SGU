package DTO;

public class LaptopDTO extends ProductsDTO {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String screenSize;
    private String screenResol;// do phan giai
    private String battery; //pin

    public LaptopDTO(){

    }

    public LaptopDTO(String productIMEI, String productID, String categoryID, String brandID, String productName, double price, int warrantyPeriod, String origin, String cpu, String ram, String storage, String gpu, String screenSize, String screenResol, String battery){
        super(productIMEI,productID,categoryID,brandID,productName,price,warrantyPeriod,origin);
        this.battery = battery;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.screenResol = screenResol;
        this.screenSize = screenSize;
        this.storage = storage;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getScreenResol() {
        return screenResol;
    }

    public void setScreenResol(String screenResol) {
        this.screenResol = screenResol;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    

    @Override
    public String toString() {
        String laptopFormat = " %-20s | %-15s | %-20s | %-20s | %-15s | %-15s | %-15s |";
        
        return super.toString() + String.format(laptopFormat, 
                cpu, ram, storage, gpu, screenSize, screenResol, battery);
    }
    
    @Override
    public void displayInfo(){
        System.out.println(toString());
    }
    
    
}
