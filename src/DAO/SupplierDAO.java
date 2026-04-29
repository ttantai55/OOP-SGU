package DAO;

import DTO.Supplier; // Nhớ import đúng tên class Supplier
import java.util.Arrays;
import java.io.*;

public class SupplierDAO implements IRepository<Supplier> {
    private Supplier[] suppliers;
    private int count;
    private final String defaultPath = "src/data/suppliers.txt";

    public SupplierDAO() {
        this.suppliers = new Supplier[100]; 
        this.count = 0;
        readFile(defaultPath); // Tải dữ liệu khi khởi động
    }

    @Override
    public void add(Supplier obj) {
        if (count < suppliers.length) {
            suppliers[count++] = obj;
            writeFile(defaultPath); // Lưu ra file
            System.out.println("Thêm nhà cung cấp thành công!");
        } else {
            System.out.println("Danh sách nhà cung cấp đã đầy!");
        }
    }

    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(id)) {
                for (int j = i; j < count - 1; j++) {
                    suppliers[j] = suppliers[j + 1];
                }
                suppliers[--count] = null;
                writeFile(defaultPath); // Lưu ra file
                System.out.println("Đã xóa nhà cung cấp: " + id);
                return;
            }
        }
        System.out.println("Không tìm thấy nhà cung cấp cần xóa!");
    }

    @Override
    public void update(Supplier obj) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(obj.getSupplierId())) {
                suppliers[i] = obj;
                writeFile(defaultPath); // Lưu ra file
                System.out.println("Cập nhật nhà cung cấp thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy nhà cung cấp để cập nhật!");
    }

    @Override
    public Supplier findById(String id) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(id)) {
                return suppliers[i];
            }
        }
        return null;
    }

    @Override
    public Object[] findByName(String name) {
        Supplier[] result = new Supplier[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierName().toLowerCase().contains(name.toLowerCase())) {
                result[size++] = suppliers[i];
            }
        }
        return Arrays.copyOf(result, size); 
    }

    @Override
    public void displayAll() {
        if (count == 0) {
            System.out.println("Danh sách nhà cung cấp đang trống!");
            return;
        }
        // In tiêu đề bảng cho đẹp
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-25s | %-15s | %-30s\n", "Mã NCC", "Tên Nhà Cung Cấp", "Số Điện Thoại", "Email");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            suppliers[i].displayInfo(); 
        }
    }

    // --- CẬP NHẬT HÀM ĐỌC FILE ---
    @Override
    public void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Cấu trúc file TXT mới: id,name,phone,email
                String[] data = line.split(",");
                if (data.length >= 4) { // Đảm bảo đủ 4 trường
                    Supplier sup = new Supplier(
                        data[0].trim(), // ID
                        data[1].trim(), // Tên
                        data[2].trim(), // SDT
                        data[3].trim()  // Email (Trường mới thêm)
                    );
                    if (count < suppliers.length) {
                        suppliers[count++] = sup;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Chưa có file dữ liệu Nhà cung cấp (Sẽ tự tạo khi thêm mới).");
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file Supplier: " + e.getMessage());
        }
    }

    // --- CẬP NHẬT HÀM GHI FILE ---
    @Override
    public void writeFile(String filePath) {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); 

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                Supplier sup = suppliers[i];
                // Ghi 4 trường dữ liệu, cách nhau bằng dấu phẩy
                String line = String.format("%s,%s,%s,%s",
                    sup.getSupplierId(),
                    sup.getSupplierName(),
                    sup.getContactPhone(),
                    sup.getEmail() // Thêm email vào cuối
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file Supplier: " + e.getMessage());
        }
    }
}