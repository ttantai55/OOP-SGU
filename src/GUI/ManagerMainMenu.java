package GUI;

import BUS.AccountService;
import BUS.CustomerService;
import BUS.EmployeeService;
import java.util.Scanner;

// Giao dien chinh danh cho Quan ly
public class ManagerMainMenu {
    static Scanner sc = new Scanner(System.in);

    public void showMenu() {
        EmployeeService employeeService = new EmployeeService();
        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();

        // Tu dong load du lieu khi khoi dong chuong trinh
        System.out.println("Dang tai du lieu tu file...");
        employeeService.loadFromFile();
        customerService.loadFromFile();
        accountService.loadFromFile();

        int choice;
        do {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("  QUAN TRI HE THONG (MANAGER MENU)");
            System.out.println("=".repeat(55));
            System.out.println("  1. Quan ly Nhan Vien");
            System.out.println("  2. Quan ly Khach Hang");
            System.out.println("  3. Quan ly Tai Khoan");
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(55));
            System.out.print("Vui long chon chuc nang (0-3): ");

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
                case 0:
                    System.out.println("\nDang luu du lieu truoc khi dang xuat...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    accountService.saveToFile();
                    System.out.println("Da dang xuat thanh cong!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }
}
