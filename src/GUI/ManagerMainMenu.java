package GUI;

import BUS.AccountService;
import BUS.CustomerService;
import BUS.EmployeeService;
import BUS.ProductListBUS;
import java.util.Scanner;

// Giao dien chinh danh cho Quan ly
public class ManagerMainMenu {
    static Scanner sc = new Scanner(System.in);

    public void showMenu() {
        EmployeeService employeeService = new EmployeeService();
        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();
        ProductListBUS productBUS = new ProductListBUS();

        // Tu dong load du lieu khi khoi dong chuong trinh
        System.out.println("Dang tai du lieu tu file...");
        employeeService.loadFromFile();
        customerService.loadFromFile();
        accountService.loadFromFile();
        productBUS.loadFile();

        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  QUAN TRI HE THONG (MANAGER MENU)");
            System.out.println("=".repeat(55));
            System.out.println("  1. Quan ly Nhan Vien");
            System.out.println("  2. Quan ly Khach Hang");
            System.out.println("  3. Quan ly Tai Khoan");
            System.out.println("  4. Quan ly Hoa Don");
            System.out.println("  5. Quan ly San Pham");
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-5): ");

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
                    AccountMenu accountMenu = new AccountMenu(accountService);
                    accountMenu.showMenu();
                    break;
                case 4:
                    BillMenu billMenu = new BillMenu();
                    billMenu.showMenu();
                    break;
                case 5:
                    showProductMenu(productBUS);
                    break;
                case 0:
                    System.out.println("\nDang luu du lieu truoc khi dang xuat...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    accountService.saveToFile();
                    productBUS.saveFile();
                    System.out.println("Da dang xuat thanh cong!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }

    // ===== QUAN LY SAN PHAM (Sub-menu) =====
    private void showProductMenu(ProductListBUS productBUS) {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  QUAN LY SAN PHAM");
            System.out.println("=".repeat(55));
            System.out.println("  1. Xem tat ca san pham");
            System.out.println("  2. Xem san pham theo Danh Muc (Laptop/Phu Kien)");
            System.out.println("  3. Tim kiem san pham");
            System.out.println("  4. Them san pham moi");
            System.out.println("  5. Cap nhat thong tin san pham");
            System.out.println("  6. Xoa san pham");
            System.out.println("  0. Quay lai menu chinh");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-6): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    productBUS.displayAll();
                    pause();
                    break;
                case 2:
                    productBUS.displayByCategory();
                    pause();
                    break;
                case 3:
                    productBUS.searchProduct();
                    pause();
                    break;
                case 4:
                    productBUS.addProducts();
                    pause();
                    break;
                case 5:
                    productBUS.updateProduct();
                    pause();
                    break;
                case 6:
                    productBUS.removeProduct();
                    pause();
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }

    private void pause() {
        System.out.print("\nNhan Enter de tiep tuc...");
        sc.nextLine();
    }
}