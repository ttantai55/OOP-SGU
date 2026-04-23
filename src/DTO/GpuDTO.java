package DTO;
import java.util.Scanner;

public class GpuDTO {
    private String brand; // Hang san xuat (VD: NVIDIA, AMD, Intel)
    private String model; // Ma GPU (VD: RTX 4060, RX 7600)
    private int vram;     // Dung luong bo nho VRAM tinh bang GB (VD: 8, 16)

    Scanner sc = new Scanner(System.in);

    public GpuDTO() {

    }

    public GpuDTO(String brand, String model, int vram) {
        this.brand = brand;
        this.model = model;
        this.vram = vram;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getVram() {
        return vram;
    }

    public void setVram(int vram) {
        this.vram = vram;
    }

    // --- Nhập liệu ---
    public void input() {
        System.out.println("--- Nhap thong tin Card đo hoa (GPU) ---");
        System.out.print("Moi nhap hang san xuat (VD: NVIDIA, AMD): ");
        setBrand(sc.nextLine());
        
        System.out.print("Moi nhap ma GPU (VD: RTX 4060, Arc A530M): ");
        setModel(sc.nextLine());
        
        System.out.print("Moi nhap dung luong VRAM (GB - VD: 8): ");
        setVram(Integer.parseInt(sc.nextLine()));
    }

    // --- Xuất chuỗi ---
    @Override
    public String toString() {
        // Kết quả VD: "NVIDIA RTX 4060 (8GB)"
        String gpuInfo = brand + " " + model + " (" + vram + "GB)";

        return String.format("%-20s|", gpuInfo);
    }

    public String getShortSummary(){
        return this.brand + " "+ this.model;
    }
}
    