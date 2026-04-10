public class Laptop extends Products {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String screenSize;
    private String screenResol;// do phan giai
    private String battery; //pin

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
    public void displayInfo(){

    }
    
}
