package GUI;

import BUS.AccountService;
import BUS.CustomerService;
import BUS.EmployeeService;
import BUS.SupplierService; // [BỔ SUNG] Import lớp dịch vụ Nhà cung cấp
import java.util.Scanner;

// [OOP] Class: Giao dien chinh danh cho Quan ly đóng vai trò Điều phối (Orchestrator)
public class ManagerMainMenu {
    static Scanner sc = new Scanner(System.in);

    public void showMenu() {
        // [OOP] Object Creation: Khởi tạo các đối tượng xử lý nghiệp vụ
        EmployeeService employeeService = new EmployeeService();
        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();
        SupplierService supplierService = new SupplierService(); // [BỔ SUNG] Khởi tạo SupplierService

        // Tu dong load du lieu khi khoi dong chuong trinh
        System.out.println("Dang tai du lieu tu file...");
        
        // [OOP] Delegation: Ủy quyền nạp dữ liệu từ ổ cứng lên RAM cho từng Service quản lý
        employeeService.loadFromFile();
        customerService.loadFromFile();
        accountService.loadFromFile();
        supplierService.loadFromFile(); // [BỔ SUNG] Load dữ liệu nhà cung cấp

        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  QUAN TRI HE THONG (MANAGER MENU)");
            System.out.println("=".repeat(55));
            System.out.println("  1. Quan ly Nhan Vien");
            System.out.println("  2. Quan ly Khach Hang");
            System.out.println("  3. Quan ly Tai Khoan");
            System.out.println("  4. Quan ly Nha Cung Cap"); // [BỔ SUNG] Thêm nhánh giao diện
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-4): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    employeeService.showMenu();
                    break;
                case 2:
                    customerService.showMenu();
                    break;
                case 3:
                    // AccountMenu sử dụng Dependency Injection, truyền AccountService có sẵn vào
                    AccountMenu accountMenu = new AccountMenu(accountService);
                    accountMenu.showMenu();
                    break;
                case 4:
                    // [OOP] Delegation: Ủy quyền cho SupplierService hiển thị giao diện chuyên biệt của nó
                    supplierService.showMenu(); // [BỔ SUNG]
                    break;
                case 0:
                    System.out.println("\nDang luu du lieu truoc khi dang xuat...");
                    // [OOP] Delegation: Ủy quyền đồng bộ dữ liệu từ RAM xuống ổ cứng trước khi đóng
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    accountService.saveToFile();
                    supplierService.saveToFile(); // [BỔ SUNG]
                    System.out.println("Da dang xuat thanh cong!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }
}