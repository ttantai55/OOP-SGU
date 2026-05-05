package BUS;

import DAO.SupplierDAO;
import DTO.Supplier;
import java.util.Scanner;

// [OOP] Class: Lớp Nghiệp vụ (Business Logic) xử lý logic của Nhà cung cấp
public class SupplierService {

    // [OOP] Composition (Mối quan hệ "Has-A") & Delegation (Ủy quyền):
    // Service chứa một đối tượng DAO để giao tiếp với file dữ liệu.
    private SupplierDAO supplierDAO;
    private Scanner sc;

    public SupplierService() {
        this.supplierDAO = new SupplierDAO();
        this.sc = new Scanner(System.in);
        supplierDAO.readFile("OOP-SGU/src/data/suppliers.txt");
    }

    // --- CÁC HÀM ĐỒNG BỘ DỮ LIỆU ---
    public void loadFromFile() {
        // [OOP] Delegation: Giao việc đọc file cho DAO
        supplierDAO.readFile("OOP-SGU/src/data/suppliers.txt");
    }

    public void saveToFile() {
        supplierDAO.writeFile("OOP-SGU/src/data/suppliers.txt");
    }

    // --- MENU QUẢN LÝ NHÀ CUNG CẤP ---
    public void showMenu() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     QUAN LY NHA CUNG CAP (SUPPLIER SERVICE)");
            System.out.println("=".repeat(50));
            System.out.println("  1. Them Nha cung cap moi");
            System.out.println("  2. Sua thong tin Nha cung cap");
            System.out.println("  3. Xoa Nha cung cap");
            System.out.println("  4. Tim kiem Nha cung cap theo ten");
            System.out.println("  5. Hien thi danh sach Nha cung cap");
            System.out.println("  0. Quay lai Menu Chinh");
            System.out.println("=".repeat(50));
            System.out.print("Vui long chon chuc nang (0-5): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1: addSupplier(); break;
                case 2: updateSupplier(); break;
                case 3: deleteSupplier(); break;
                case 4: searchSupplier(); break;
                case 5: showAllSuppliers(); break;
                case 0:
                    System.out.println("\n[Thong bao] Dang quay lai Menu Chinh...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le. Vui long thu lai!");
            }
        } while (choice != 0);
    }

    // --- CÁC HÀM NGHIỆP VỤ (CRUD) ---

    private void addSupplier() {
        System.out.println("\n--- THEM NHA CUNG CAP ---");
        // [OOP] Object Creation: Khởi tạo đối tượng mới
        Supplier newSupplier = new Supplier();
        // Gọi hàm input() từ DTO để tận dụng tính Đóng gói (Encapsulation)
        newSupplier.input();

        // [Logic Nghiệp vụ] Kiểm tra xem ID đã tồn tại trong hệ thống chưa
        if (supplierDAO.findById(newSupplier.getSupplierId()) != null) {
            System.out.println("[Loi] Ma Nha cung cap '" + newSupplier.getSupplierId() + "' da ton tai! Vui long chon ma khac.");
            return; // Dừng tiến trình thêm
        }

        // Nếu ID chưa tồn tại, ủy quyền cho DAO lưu vào danh sách
        supplierDAO.add(newSupplier);
        saveToFile();
    }

    private void updateSupplier() {
        System.out.println("\n--- SUA THONG TIN NHA CUNG CAP ---");
        String id = Validation.getNonEmptyString("Nhap Ma NCC can sua thong tin: ");
        Supplier existingSupplier = supplierDAO.findById(id);
        if (existingSupplier == null) {
            System.out.println("[Loi] Khong tim thay Nha cung cap mang ma: " + id);
            return;
        }

        System.out.println("[Thong bao] Dang sua thong tin cho NCC: " + existingSupplier.getSupplierName());
        System.out.println("(Luu y: ID khong duoc phep thay doi)");
        // Cập nhật các trường thông tin khác
        existingSupplier.setSupplierName(Validation.getNonEmptyString("Moi nhap Ten NCC moi: "));
        existingSupplier.setContactPhone(Validation.getValidPhone("Moi nhap So dien thoai moi (10-11 so): "));
        existingSupplier.setEmail(Validation.getValidEmail("Moi nhap Email moi (@gmail.com): "));

        supplierDAO.update(existingSupplier);
        saveToFile();
    }

    private void deleteSupplier() {
        System.out.println("\n--- XOA NHA CUNG CAP ---");
        String id = Validation.getNonEmptyString("Nhap Ma NCC can xoa: ");
        if (supplierDAO.findById(id) == null) {
            System.out.println("[Loi] Khong tim thay Nha cung cap mang ma: " + id);
            return;
        }

        System.out.print("Ban co chac chan muon xoa khong? (Y/N): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            supplierDAO.remove(id);
            saveToFile();
        } else {
            System.out.println("[Thong bao] Da huy thao tac xoa.");
        }
    }

    private void searchSupplier() {
        System.out.println("\n--- TIM KIEM NHA CUNG CAP ---");
        String keyword = Validation.getNonEmptyString("Nhap ten (hoac mot phan ten) NCC can tim: ");

        Object[] results = supplierDAO.findByName(keyword);

        if (results.length == 0) {
            System.out.println("[Thong bao] Khong tim thay ket qua nao phu hop voi tu khoa: " + keyword);
        } else {
            System.out.println("[Thong bao] Tim thay " + results.length + " ket qua:");
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.printf("%-10s | %-25s | %-15s | %-30s\n", "Ma NCC", "Ten Nha Cung Cap", "So Dien Thoai", "Email");
            System.out.println("-----------------------------------------------------------------------------------------");
            for (Object obj : results) {
                Supplier sup = (Supplier) obj; // Ép kiểu từ Object về Supplier
                sup.displayInfo();
            }
        }
    }

    private void showAllSuppliers() {
        System.out.println("\n--- DANH SACH NHA CUNG CAP ---");
        supplierDAO.displayAll();
    }
}