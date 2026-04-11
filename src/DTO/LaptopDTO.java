package DTO;

public class LaptopDTO extends ProductsDTO {
    private CpuDTO cpu;
    private RamDTO ram;
    private StorageDTO storage;
    private GpuDTO gpu;
    private ScreenDTO screen;
    private String battery; //pin

    public LaptopDTO() {
        super();
        this.cpu = new CpuDTO();
        this.ram = new RamDTO();
        this.storage = new StorageDTO();
        this.gpu = new GpuDTO();
        this.screen = new ScreenDTO();
    }

    public LaptopDTO(String productIMEI, String productID, String categoryID, String brandID, String productName, double price, int warrantyPeriod, String origin, CpuDTO cpu, RamDTO ram, StorageDTO storage, GpuDTO gpu, ScreenDTO screen, String battery, boolean status){
        super(productIMEI,productID,categoryID,brandID,productName,price,warrantyPeriod,origin,status);
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.gpu = gpu;
        this.screen = screen;
        this.battery = battery;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }


    @Override
    public void input(){
        super.input();
        System.out.println("Moi nhap cau hinh Laptop:");

        this.cpu.input();
        this.ram.input();
        this.storage.input();
        this.gpu.input();
        this.screen.input();
        
        System.out.println("Moi nhap Dung luong pin:");
        setBattery(sc.nextLine());
    }

    @Override
    public String toString() {
        String laptopFormat = " %-20s | %-15s | %-20s | %-20s | %-15s | %-15s | %-15s |";
        
        return super.toString() + String.format(laptopFormat, 
                cpu, ram, storage, gpu, screen, battery);
    }
    
    @Override
    public void displayInfo(){
        System.out.println(toString());
    }
    
    
}
