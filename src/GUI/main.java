package GUI;
import BUS.CustomerService;
import BUS.EmployeeService;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeService();
        CustomerService customerService = new CustomerService();

        // Tự động load dữ liệu khi khởi động chương trình
        System.out.println("Dang tai du lieu tu file...");
        employeeService.loadFromFile();
        customerService.loadFromFile();

        int choice;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("  CHUONG TRINH QUAN LY CUA HANG (MAIN MENU)");
            System.out.println("=".repeat(50));
            System.out.println("  1. Quan ly Nhan Vien");
            System.out.println("  2. Quan ly Khach Hang");
            System.out.println("  3. Luu toan bo du lieu ra file");
            System.out.println("  0. Thoat chuong trinh");
            System.out.println("=".repeat(50));
            System.out.print("Vui long chon chuc nang (0-3): ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    // Gọi menu quản lý nhân viên từ EmployeeService
                    employeeService.showMenu();
                    break;
                case 2:
                    // Gọi menu quản lý khách hàng từ CustomerService
                    customerService.showMenu();
                    break;
                case 3:
                    System.out.println("\nLuu du lieu...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    break;
                case 0:
                    System.out.println("\nDang luu du lieu truoc khi thoat...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    System.out.println("Cam on ban da su dung chuong trinh. Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0);
    }
}
