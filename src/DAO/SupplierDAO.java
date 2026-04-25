package DAO;

import DTO.SupplierDTO;

import java.util.Arrays;

public class SupplierDAO implements IRepository<SupplierDTO> {
    // 1. Mảng quản lý Nhà cung cấp
    private SupplierDTO[] suppliers;
    private int count;

    public SupplierDAO() {
        this.suppliers = new SupplierDTO[100]; // Khởi tạo mảng chứa tối đa 100 nhà cung cấp
        this.count = 0;
    }

    // 2. Thao tác THÊM nhà cung cấp
    @Override
    public void add(SupplierDTO obj) {
        if (count < suppliers.length) {
            suppliers[count++] = obj;
            System.out.println("Thêm nhà cung cấp thành công!");
        } else {
            System.out.println("Danh sách nhà cung cấp đã đầy!");
        }
    }

    // 3. Thao tác XÓA nhà cung cấp theo ID
    @Override
    public void remove(String id) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(id)) {
                // Dồn mảng để lấp chỗ trống
                for (int j = i; j < count - 1; j++) {
                    suppliers[j] = suppliers[j + 1];
                }
                suppliers[--count] = null;
                System.out.println("Đã xóa nhà cung cấp: " + id);
                return;
            }
        }
        System.out.println("Không tìm thấy nhà cung cấp cần xóa!");
    }

    // 4. Thao tác SỬA nhà cung cấp
    @Override
    public void update(SupplierDTO obj) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(obj.getSupplierId())) {
                suppliers[i] = obj; // Ghi đè thông tin mới
                System.out.println("Cập nhật nhà cung cấp thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy nhà cung cấp để cập nhật!");
    }

    // 5. Hàm tìm kiếm theo ID
    @Override
    public SupplierDTO findById(String id) {
        for (int i = 0; i < count; i++) {
            if (suppliers[i].getSupplierId().equals(id)) {
                return suppliers[i];
            }
        }
        return null;
    }

    // 6. Tìm kiếm tương đối theo Tên nhà cung cấp
    @Override
    public Object[] findByName(String name) {
        SupplierDTO[] result = new SupplierDTO[count];
        int size = 0;
        for (int i = 0; i < count; i++) {
            // Tìm những tên nhà cung cấp có chứa chuỗi name (không phân biệt hoa thường)
            if (suppliers[i].getSupplierName().toLowerCase().contains(name.toLowerCase())) {
                result[size++] = suppliers[i];
            }
        }
        // Cắt mảng cho vừa khít với số lượng tìm được
        return Arrays.copyOf(result, size); 
    }

    // 7. Hiển thị danh sách
    @Override
    public void displayAll() {
        if (count == 0) {
            System.out.println("Danh sách nhà cung cấp đang trống!");
            return;
        }
        for (int i = 0; i < count; i++) {
            // Gọi hàm displayInfo() đã được viết sẵn trong file Supplier.java
            suppliers[i].displayInfo(); 
        }
    }

    // 8. Các hàm đọc/ghi file tạm thời để trống
    @Override
    public void readFile(String filePath) {
        System.out.println("Chức năng đọc file Supplier đang phát triển...");
    }

    @Override
    public void writeFile(String filePath) {
        System.out.println("Chức năng ghi file Supplier đang phát triển...");
    }
}