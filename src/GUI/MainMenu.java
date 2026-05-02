package GUI;

import BUS.AccountService; // [BỔ SUNG] Import Service quản lý tài khoản
import BUS.CustomerService;
import BUS.EmployeeService;
import BUS.SupplierService;
import java.util.Scanner;

// [OOP] Class: Giao diện Menu Chính dành cho Quản trị viên (Admin)
public class MainMenu {

    // [OOP] Method: Hàm tiện ích hỗ trợ xóa màn hình console
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    @SuppressWarnings("resource")
    public void showMenu() {
        Scanner sc = new Scanner(System.in);

        // [OOP] Object Creation: Khởi tạo các Service xử lý nghiệp vụ
        EmployeeService employeeService = new EmployeeService();
        CustomerService customerService = new CustomerService();
        SupplierService supplierService = new SupplierService();
        AccountService accountService = new AccountService(); // [BỔ SUNG]

        System.out.println("[Thong bao] Dang tai du lieu he thong vao bo nho (RAM)...");
        
        // [OOP] Delegation: Ủy quyền cho các Service tải dữ liệu từ file
        employeeService.loadFromFile();
        customerService.loadFromFile();
        supplierService.loadFromFile();
        accountService.loadFromFile(); // [BỔ SUNG]

        int choice = -1;
        do {
            clearScreen();
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("     CHUONG TRINH QUAN LY CUA HANG (MAIN MENU)");
            System.out.println("=".repeat(50));
            System.out.println("  1. Quan ly Nhan Vien");
            System.out.println("  2. Quan ly Khach Hang");
            System.out.println("  3. Quan ly Nha Cung Cap");
            System.out.println("  4. Quan ly Tai Khoan He Thong"); // [BỔ SUNG]
            System.out.println("  5. Luu toan bo du lieu ra file"); // [DỊCH CHUYỂN TỪ 4 SANG 5]
            System.out.println("  9. Thoat chuong trinh (Tat toan bo he thong)");
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(50));
            System.out.print("Vui long chon chuc nang (0-5, 9): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1; // Bắt lỗi an toàn nếu người dùng nhập chữ
            }

            switch (choice) {
                case 1:
                    employeeService.showMenu();
                    break;
                case 2:
                    customerService.showMenu();
                    break;
                case 3:
                    supplierService.showMenu();
                    break;
                case 4:
                    // [OOP] Dependency Injection: Truyền tham chiếu accountService vào Menu phụ
                    // Đảm bảo thao tác trên cùng một vùng nhớ dữ liệu
                    AccountMenu accountMenu = new AccountMenu(accountService);
                    accountMenu.showMenu();
                    break;
                case 5:
                    System.out.println("\n[Thong bao] Dang ghi toan bo du lieu xuong o cung...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    supplierService.saveToFile();
                    accountService.saveToFile(); // [BỔ SUNG]
                    System.out.println("[Thong bao] Dong bo du lieu thanh cong!");
                    System.out.print("Nhan Enter de tiep tuc...");
                    sc.nextLine();
                    break;
                case 9:
                    System.out.println("\n[Thong bao] Dang luu an toan du lieu truoc khi thoat...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    supplierService.saveToFile();
                    accountService.saveToFile(); // [BỔ SUNG]
                    System.out.println("[Thong bao] He thong dang tat...");
                    System.exit(0);
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Dang xuat. Dang luu an toan du lieu...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    supplierService.saveToFile();
                    accountService.saveToFile(); // [BỔ SUNG]
                    return; // Thoát hàm showMenu(), trả luồng điều khiển về lại cho LoginUI
                default:
                    System.out.println("[Loi] Lua chon khong hop le. Vui long thu lai!");
                    System.out.print("Nhan Enter de tiep tuc...");
                    sc.nextLine();
            }
        } while (choice != 0 && choice != 9);
    }
}