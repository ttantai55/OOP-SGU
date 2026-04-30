package GUI;

import BUS.CustomerService;
import BUS.EmployeeService;
import BUS.SupplierService; // Import lop xu ly Nha Cung Cap cua thay
import java.util.Scanner;

// [OOP] Class: Giao dien Menu Chinh cua he thong danh cho Admin/Quan ly
public class MainMenu {
    
    @SuppressWarnings("resource")
    public void showMenu() { 
        Scanner sc = new Scanner(System.in);
        
        // [OOP] Object Creation: Khoi tao cac doi tuong Service
        EmployeeService employeeService = new EmployeeService();
        CustomerService customerService = new CustomerService();
        SupplierService supplierService = new SupplierService();// Them Service cho NCC

        System.out.println("[Thong bao] Dang tai du lieu tu file...");
        employeeService.loadFromFile();
        customerService.loadFromFile();
        // Neu supplierService co ham doc file, thay co the mo comment dong ben duoi
        // supplierService.loadFromFile(); 

        int choice;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("  CHUONG TRINH QUAN LY CUA HANG (MAIN MENU)");
            System.out.println("=".repeat(50));
            System.out.println("  1. Quan ly Nhan Vien");
            System.out.println("  2. Quan ly Khach Hang");
            System.out.println("  3. Quan ly Nha Cung Cap"); // [MOI] Menu Nha cung cap
            System.out.println("  4. Luu toan bo du lieu ra file");
            System.out.println("  9. Thoat chuong trinh (Tat toan bo he thong)");
            System.out.println("  0. Dang xuat (Quay lai man hinh dang nhap)");
            System.out.println("=".repeat(50));
            System.out.print("Vui long chon chuc nang (0-4, 9): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1; // Chu dong bat loi neu nhap chuoi thay vi so
            }

            switch (choice) {
                case 1: 
                    employeeService.showMenu(); 
                    break;
                case 2: 
                    customerService.showMenu(); 
                    break;
                case 3:
                    // [OOP] Delegation: Uy quyen cho SupplierListBUS xu ly giao dien NCC
                    supplierService.showMenu(); 
                    break;
                case 4: 
                    System.out.println("\n[Thong bao] Dang luu du lieu...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    // supplierService.saveToFile(); // Mo comment neu co ham luu file
                    System.out.println("[Thong bao] Luu du lieu thanh cong!");
                    break;
                case 9:
                    System.out.println("\n[Thong bao] Dang luu du lieu truoc khi thoat...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    // supplierService.saveToFile();
                    System.out.println("[Thong bao] Da luu. Chuong trinh tat an toan. Tam biet!");
                    System.exit(0); 
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Dang luu du lieu truoc khi dang xuat...");
                    employeeService.saveToFile();
                    customerService.saveToFile();
                    // supplierService.saveToFile();
                    System.out.println("[Thong bao] Dang xuat thanh cong. Dang tro ve...");
                    break;
                default: 
                    System.out.println("[Loi] Lua chon khong hop le. Vui long chon lai!");
            }
        } while (choice != 0); 
    }
}