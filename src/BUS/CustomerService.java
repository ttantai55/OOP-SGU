package BUS;

import DAO.CustomerDAO;
import DTO.Customer;
import java.util.Scanner;

// CustomerService.java - xu ly nghiep vu khach hang
public class CustomerService {
    private final CustomerDAO customerDAO;
    static Scanner sc = new Scanner(System.in);

    private static final String FILE_CUSTOMERS = "src/data/Customer.txt";

    public CustomerService() {
        customerDAO = new CustomerDAO();
        customerDAO.readFile(FILE_CUSTOMERS);
    }

    // ==================== LOAD / SAVE ====================

    public void loadFromFile() {
        customerDAO.readFile(FILE_CUSTOMERS);
        System.out.println("Da tai du lieu tu file: " + FILE_CUSTOMERS);
    }

    public void saveToFile() {
        customerDAO.writeFile(FILE_CUSTOMERS);
        System.out.println("Da luu du lieu vao file: " + FILE_CUSTOMERS);
    }

    // ==================== THEM KHACH HANG ====================

    public void addCustomer() {
        Customer c = new Customer();
        c.input();
        customerDAO.add(c);
        System.out.println("Them khach hang thanh cong!");
        saveToFile();
    }

    // ==================== XOA KHACH HANG ====================

    public void removeCustomer(String id) {
        if (customerDAO.findById(id) == null) {
            System.out.println("Khong tim thay khach hang co ma: " + id);
            return;
        }
        customerDAO.remove(id);
        System.out.println("Da xoa khach hang co ma: " + id);
        saveToFile();
    }

    // ==================== CAP NHAT KHACH HANG ====================

    public void updateCustomer(String id) {
        Customer c = customerDAO.findById(id);
        if (c == null) {
            System.out.println("Khong tim thay khach hang co ma: " + id);
            return;
        }
        c.input();
        customerDAO.update(c);
        System.out.println("Cap nhat khach hang thanh cong!");
        saveToFile();
    }

    // ==================== TIM KIEM ====================

    public Customer findById(String id) {
        return customerDAO.findById(id);
    }

    public Customer[] findByName(String name) {
        return customerDAO.findByName(name);
    }

    // ==================== HIEN THI ====================

    public void displayAll() {
        if (customerDAO.getCount() == 0) {
            System.out.println("Danh sach khach hang rong.");
            return;
        }
        System.out.println("=".repeat(150));

        System.out.printf("%-5s | %-30s | %-13s | %-25s | %-45s | %-10s | %-10s | %-10s | %-12s%n",
                "---", "Ho Ten", "So DT", "Email", "Dia Chi", "Ma KH", "Diem TL", "Loai", "Ngay DK");
        System.out.println("=".repeat(150));
        Customer[] all = customerDAO.getAll();
        for (int i = 0; i < all.length; i++) {
            System.out.printf("%-5d | %s%n", i + 1, all[i].toString());
        }
        System.out.println("=".repeat(150));
        System.out.println("Tong so: " + customerDAO.getCount() + " khach hang.");
    }

    // ==================== SAP XEP ====================

    public void sortByName() {
        customerDAO.sortByName();
        System.out.println("Da sap xep khach hang theo ten.");
    }

    public void sortByPoints() {
        customerDAO.sortByPoints();
        System.out.println("Da sap xep khach hang theo diem tich luy giam dan.");
    }

    // ==================== THONG KE ====================

    public void statsByType() {
        customerDAO.statsbyType();
    }

    // ==================== MENU CHINH ====================

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("  QUAN LY KHACH HANG");
            System.out.println("=".repeat(40));
            System.out.println("  1. Hien thi thong tin tat ca khach hang");
            System.out.println("  2. Them khach hang");
            System.out.println("  3. Xoa khach hang");
            System.out.println("  4. Cap nhat khach hang");
            System.out.println("  5. Tim kiem theo ma");
            System.out.println("  6. Tim kiem theo ten");
            System.out.println("  7. Sap xep theo ten");
            System.out.println("  8. Sap xep theo diem tich luy");
            System.out.println("  9. Thong ke theo loai");
            System.out.println(" 10. Tai du lieu tu file");
            System.out.println("  0. Thoat");
            System.out.println("=".repeat(40));
            System.out.print("Chon: ");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    displayAll();
                    break;
                case 2:
                    addCustomer();
                    break;
                case 3:
                    System.out.print("Nhap ma khach hang can xoa: ");
                    removeCustomer(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ma khach hang can cap nhat: ");
                    updateCustomer(sc.nextLine());
                    break;
                case 5:
                    System.out.print("Nhap ma khach hang: ");
                    Customer found = findById(sc.nextLine());
                    if (found != null) found.displayInfo();
                    else System.out.println("Khong tim thay.");
                    break;
                case 6:
                    System.out.print("Nhap ten can tim: ");
                    Customer[] results = findByName(sc.nextLine());
                    if (results.length == 0) System.out.println("Khong tim thay.");
                    else for (Customer c : results) c.displayInfo();
                    break;
                case 7:
                    sortByName();
                    break;
                case 8:
                    sortByPoints();
                    break;
                case 9:
                    statsByType();
                    break;
                case 10:
                    loadFromFile();
                    break;
                case 0:
                    System.out.println("Thoat quan ly khach hang.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        } while (choice != 0);
    }
}
