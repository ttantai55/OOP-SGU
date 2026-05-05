package BUS;

import DAO.EmployeeDAO;
import DTO.Employee;
import DTO.Manager;
import DTO.SalesEmployee;
import DTO.DeliveryEmployee;
import DTO.TechnicianEmployee;

import java.util.Scanner;

// EmployeeService.java - xu ly nghiep vu nhan vien
public class EmployeeService {
    private EmployeeDAO employeeDAO;
    static Scanner sc = new Scanner(System.in);

    // Duong dan file
    private static final String FILE_EMPLOYEES = "src/data/Employee.txt";

    public EmployeeService() {
        employeeDAO = new EmployeeDAO();
        //loadFromfile(); fix
    }


    // ==================== LOAD / SAVE ====================

    public void loadFromFile() {
        employeeDAO.readFile(FILE_EMPLOYEES);
        System.out.println("Da tai du lieu tu file: " + FILE_EMPLOYEES);
    }

    public void saveToFile() {
        employeeDAO.writeFile(FILE_EMPLOYEES);
        System.out.println("Da luu du lieu vao file: " + FILE_EMPLOYEES);
    }

    // ==================== THEM NHAN VIEN ====================

    public void addManager() {
        Manager m = new Manager();
        m.input();
        employeeDAO.add(m);
        System.out.println("Them quan ly thanh cong!");
        saveToFile();
    }

    public void addSalesEmployee() {
        SalesEmployee s = new SalesEmployee();
        s.input();
        employeeDAO.add(s);
        System.out.println("Them nhan vien ban hang thanh cong!");
        saveToFile();
    }

    public void addDeliveryEmployee() {
        DeliveryEmployee d = new DeliveryEmployee();
        d.input();
        employeeDAO.add(d);
        System.out.println("Them nhan vien giao hang thanh cong!");
        saveToFile();
    }

    public void addTechnicianEmployee() {
        TechnicianEmployee t = new TechnicianEmployee();
        t.input();
        employeeDAO.add(t);
        System.out.println("Them nhan vien ky thuat thanh cong!");
        saveToFile();
    }

    // ==================== XOA NHAN VIEN ====================

    public void removeEmployee(String id) {
        Employee e = employeeDAO.findById(id);
        if (e == null) {
            System.out.println("Khong tim thay nhan vien co ma: " + id);
            return;
        }
        employeeDAO.remove(id);
        System.out.println("Da xoa nhan vien co ma: " + id);
        saveToFile();
    }

    // ==================== CAP NHAT NHAN VIEN ====================

    public void updateEmployee(String id) {
        Employee e = employeeDAO.findById(id);
        if (e == null) {
            System.out.println("Khong tim thay nhan vien co ma: " + id);
            return;
        }
        e.input();
        employeeDAO.update(e);
        System.out.println("Cap nhat nhan vien thanh cong!");
        saveToFile();
    }

    // ==================== TIM KIEM ====================

    public Employee findById(String id) {
        return employeeDAO.findById(id);
    }

    public Employee[] findByName(String name) {
        return employeeDAO.findByName(name);
    }

    // ==================== HIEN THI ====================

    public void displayAllEmployees() {
        employeeDAO.displayAll();
    }

    // Common part of the header (shared columns)
    private static final String COMMON_HEADER = String.format(
            "%-5s | %-25s | %-12s | %-25s | %-50s | %-10s | %-14s | %-15s | %15s | %-12s",
            "STT", "Ho Ten", "So DT", "Email", "Dia Chi", "Ma NV", "CCCD", "Chuc Vu", "Luong CB", "Ngay VL");

    public void displayManagers() {
        int width = 270;
        System.out.println("=".repeat(width));
        System.out.printf("%s | %-15s | %12s | %15s%n",
                COMMON_HEADER, "Phong Ban", "Phu Cap", "Tong Luong");
        System.out.println("=".repeat(width));
        int stt = 1;
        for (Employee e : employeeDAO.getAll()) {
            if (e instanceof Manager) {
                System.out.printf("%-5d | ", stt++);
                e.displayInfo();
            }
        }
        System.out.println("=".repeat(width));
        System.out.println("Tong so: " + (stt - 1) + " quan ly.");
    }

    public void displaySalesEmployees() {
        int width = 270;
        System.out.println("=".repeat(width));
        System.out.printf("%s | %12s | %15s | %15s%n",
                COMMON_HEADER, "Tien Thuong", "Hoa Hong", "Tong Luong");
        System.out.println("=".repeat(width));
        int stt = 1;
        for (Employee e : employeeDAO.getAll()) {
            if (e instanceof SalesEmployee) {
                System.out.printf("%-5d | ", stt++);
                e.displayInfo();
            }
        }
        System.out.println("=".repeat(width));
        System.out.println("Tong so: " + (stt - 1) + " nhan vien ban hang.");
    }

    public void displayDeliveryEmployees() {
        int width = 300;
        System.out.println("=".repeat(width));
        System.out.printf("%s | %-15s | %-20s | %-10s | %12s | %15s%n",
                COMMON_HEADER, "Bien So Xe", "Khu Vuc GH", "So Don", "Phi/Don", "Tong Luong");
        System.out.println("=".repeat(width));
        int stt = 1;
        for (Employee e : employeeDAO.getAll()) {
            if (e instanceof DeliveryEmployee) {
                System.out.printf("%-5d | ", stt++);
                e.displayInfo();
            }
        }
        System.out.println("=".repeat(width));
        System.out.println("Tong so: " + (stt - 1) + " nhan vien giao hang.");
    }

    public void displayTechnicianEmployees() {
        int width = 270;
        System.out.println("=".repeat(width));
        System.out.printf("%s | %-10s | %12s | %15s%n",
                COMMON_HEADER, "So Sua Chua", "Chi Tieu", "Tong Luong");
        System.out.println("=".repeat(width));
        int stt = 1;
        for (Employee e : employeeDAO.getAll()) {
            if (e instanceof TechnicianEmployee) {
                System.out.printf("%-5d | ", stt++);
                e.displayInfo();
            }
        }
        System.out.println("=".repeat(width));
        System.out.println("Tong so: " + (stt - 1) + " nhan vien ky thuat.");
    }

    // ==================== SAP XEP ====================

    public void sortAllByName() {
        employeeDAO.sortByName();
        System.out.println("Da sap xep tat ca nhan vien theo ten.");
    }

    public void sortAllBySalary() {
        employeeDAO.sortBySalary();
        System.out.println("Da sap xep tat ca nhan vien theo luong giam dan.");
    }

    // ==================== THONG KE ====================

    public void statsByRole() {
        Employee[] all = employeeDAO.getAll();
        int manager = 0, sales = 0, delivery = 0, technician = 0;
        for (int i = 0; i < all.length; i++) {
            if (all[i] instanceof Manager)              manager++;
            else if (all[i] instanceof SalesEmployee)   sales++;
            else if (all[i] instanceof DeliveryEmployee) delivery++;
            else if (all[i] instanceof TechnicianEmployee) technician++;
        }
        System.out.println("=".repeat(40));
        System.out.println("  THONG KE NHAN VIEN THEO VAI TRO");
        System.out.println("=".repeat(40));
        System.out.printf("  Quan ly          : %d%n", manager);
        System.out.printf("  NV Ban hang       : %d%n", sales);
        System.out.printf("  NV Giao hang      : %d%n", delivery);
        System.out.printf("  NV Ky thuat       : %d%n", technician);
        System.out.println("=".repeat(40));
        System.out.printf("  Tong cong         : %d%n", employeeDAO.getCount());
        System.out.println("=".repeat(40));
    }

    public float totalSalary() {
        Employee[] all = employeeDAO.getAll();
        float total = 0;
        for (int i = 0; i < all.length; i++) {
            total += all[i].calculateSalary();
        }
        return total;
    }

    // ==================== MENU CHINH ====================

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("  QUAN LY NHAN VIEN");
            System.out.println("=".repeat(40));
            System.out.println("  1. Hien thi thong tin nhan vien");
            System.out.println("  2. Them nhan vien");
            System.out.println("  3. Xoa nhan vien");
            System.out.println("  4. Cap nhat nhan vien");
            System.out.println("  5. Tim kiem theo ma");
            System.out.println("  6. Tim kiem theo ten");
            System.out.println("  7. Sap xep theo ten");
            System.out.println("  8. Sap xep theo luong");
            System.out.println("  9. Thong ke theo vai tro");
            System.out.println(" 10. Tong quy luong");
            System.out.println(" 11. Tai du lieu tu file");
            System.out.println("  0. Thoat");
            System.out.println("=".repeat(40));
            System.out.print("Chon: ");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    showDisplayMenu();
                    break;
                case 2:
                    showAddMenu();
                    break;
                case 3:
                    System.out.print("Nhap ma nhan vien can xoa: ");
                    removeEmployee(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ma nhan vien can cap nhat: ");
                    updateEmployee(sc.nextLine());
                    break;
                case 5:
                    System.out.print("Nhap ma nhan vien: ");
                    Employee found = findById(sc.nextLine());
                    if (found != null) found.displayInfo();
                    else System.out.println("Khong tim thay.");
                    break;
                case 6:
                    System.out.print("Nhap ten can tim: ");
                    Employee[] results = findByName(sc.nextLine());
                    if (results.length == 0) System.out.println("Khong tim thay.");
                    else for (Employee e : results) e.displayInfo();
                    break;
                case 7:
                    sortAllByName();
                    break;
                case 8:
                    sortAllBySalary();
                    break;
                case 9:
                    statsByRole();
                    break;
                case 10:
                    System.out.printf("Tong quy luong: %.2f%n", totalSalary());
                    break;
                case 11:
                    loadFromFile();
                    break;
                case 0:
                    System.out.println("Thoat quan ly nhan vien.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        } while (choice != 0);
    }

    private void showDisplayMenu() {
        System.out.println("\n--- HIEN THI DANH SACH NHAN VIEN ---");
        System.out.println("  1. Tat ca nhan vien");
        System.out.println("  2. Danh sach Quan ly");
        System.out.println("  3. Danh sach NV Ban hang");
        System.out.println("  4. Danh sach NV Giao hang");
        System.out.println("  5. Danh sach NV Ky thuat");
        System.out.println("  0. Quay lai");
        System.out.print("Chon: ");
        int type = Integer.parseInt(sc.nextLine());
        switch (type) {
            case 1: displayAllEmployees(); break;
            case 2: displayManagers(); break;
            case 3: displaySalesEmployees(); break;
            case 4: displayDeliveryEmployees(); break;
            case 5: displayTechnicianEmployees(); break;
            case 0: break;
            default: System.out.println("Lua chon khong hop le.");
        }
    }

    private void showAddMenu() {
        System.out.println("Chon loai nhan vien:");
        System.out.println("  1. Quan ly");
        System.out.println("  2. NV Ban hang");
        System.out.println("  3. NV Giao hang");
        System.out.println("  4. NV Ky thuat");
        System.out.print("Chon: ");
        int type = Integer.parseInt(sc.nextLine());
        switch (type) {
            case 1: addManager(); break;
            case 2: addSalesEmployee(); break;
            case 3: addDeliveryEmployee(); break;
            case 4: addTechnicianEmployee(); break;
            default: System.out.println("Lua chon khong hop le.");
        }
    }
}
