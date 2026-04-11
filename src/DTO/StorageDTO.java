package DTO;
import java.util.Scanner;

public class StorageDTO {
    private String type;     // Loai o cung (VD: SSD NVMe, SSD SATA, HDD)
    private int capacity;    // Dung luong tinh bang GB(VD: 512, 1024)

    Scanner sc = new Scanner(System.in);

    public StorageDTO() {

    }

    public StorageDTO(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    // --- Nhập liệu ---
    public void input() {
        System.out.println("--- Nhập thông tin Ổ cứng (Storage) ---");
        System.out.print("Moi nhap loai o cung (VD: SSD NVMe PCIe): ");
        setType(sc.nextLine());
        System.out.print("Moi nhap dung luong o cung (GB - VD: 512, 1024): ");
        setCapacity(Integer.parseInt(sc.nextLine()));
    }

    /*// --- Xuất chuỗi ---
    @Override
    public String toString() {
        // Nếu dung lượng >= 1024 thì có thể đổi hiển thị thành TB cho đẹp, nhưng tạm thời cứ để GB cho đơn giản nhé
        // Kết quả VD: "512GB SSD NVMe PCIe"
        return capacity + "GB " + type;
    }*/
}