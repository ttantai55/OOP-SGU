package DTO;

public class LaptopDTO extends ProductsDTO {
    private CpuDTO cpu;
    private RamDTO ram;
    private StorageDTO storage;
    private GpuDTO gpu;
    private ScreenDTO screen;
    private String battery;     //pin

    public LaptopDTO() {
        super();
        this.cpu = new CpuDTO();
        this.ram = new RamDTO();
        this.storage = new StorageDTO();
        this.gpu = new GpuDTO();
        this.screen = new ScreenDTO();

        // Tu dong gan LapTop vao phan ten Danh Muc
        CategoryDTO cate = new CategoryDTO();
        cate.setCategoryName("Laptop");
    }

    public LaptopDTO(String productIMEI, String productID, CategoryDTO categoryID, BrandDTO brandID, String productName, double price, int warrantyPeriod, String origin, CpuDTO cpu, RamDTO ram, StorageDTO storage, GpuDTO gpu, ScreenDTO screen, String battery, boolean status){
        super(productIMEI,productID,categoryID,brandID,productName,price,warrantyPeriod,origin,status);
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.gpu = gpu;
        this.screen = screen;
        this.battery = battery;
    }

    public CpuDTO getCpu() {
        return cpu;
    }

    public void setCpu(CpuDTO cpu) {
        this.cpu = cpu;
    }

    public RamDTO getRam() {
        return ram;
    }

    public void setRam(RamDTO ram) {
        this.ram = ram;
    }

    public StorageDTO getStorage() {
        return storage;
    }

    public void setStorage(StorageDTO storage) {
        this.storage = storage;
    }

    public GpuDTO getGpu() {
        return gpu;
    }

    public void setGpu(GpuDTO gpu) {
        this.gpu = gpu;
    }

    public ScreenDTO getScreen() {
        return screen;
    }

    public void setScreen(ScreenDTO screen) {
        this.screen = screen;
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
        System.out.println("===Moi nhap cau hinh Laptop===:");

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
        
        return super.toString() +  
                cpu.toString() + ram.toString() + storage.toString() + gpu.toString() + screen.toString() + String.format("%5s", battery);
    }
    
    @Override
    public void displayInfo(){
        System.out.println(toString());
    }

    @Override
    // Thong so tom tat
    public String getSpecSummary(){
        return cpu.getShortSummary() + "|" +gpu.getShortSummary() + "|" + ram.getShortSummary() + "|" + screen.getShortSummary() +"|" + storage.getShortSummary();
    }

    @Override
    public String toFileString() {
        return "Laptop," + super.toFileString() + "," + cpu.toFileString() + "," + gpu.toFileString() + "," + ram.toFileString() + "," + screen.toFileString() + "," + storage.toFileString() + "," + battery ;
    }

    
}
