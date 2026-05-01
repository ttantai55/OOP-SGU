package GUI;

import BUS.AccountService;
import BUS.Validation;
import DAO.AccountDAO;
import DAO.CustomerDAO;
import DTO.Account;
import DTO.Address;
import DTO.Customer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

// [OOP] Class: Giao dien xu ly Dang nhap, Dang ky va dieu huong he thong
public class LoginUI {
    
    // [OOP] Association: Lien ket voi AccountService de xu ly logic nghiep vu
    private AccountService accountService;
    private Scanner sc;

    public LoginUI() {
        this.accountService = new AccountService();
        this.sc = new Scanner(System.in);
    }

    // [OOP] Method: Diem vao (Entry point) cua giao dien khoi dong
    public void start() {
        int choice = -1;
        do {
            System.out.println("\n" + "=".repeat(41));
            System.out.println("     HE THONG QUAN LY CUA HANG LAPTOP    ");
            System.out.println("=".repeat(41));
            System.out.println("  1. Dang nhap");
            System.out.println("  2. Dang ky tai khoan Khach hang");
            System.out.println("  0. Thoat chuong trinh");
            System.out.println("=".repeat(41));
            System.out.print("Vui long chon chuc nang (0-2): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1; // Bat loi neu nhap chuoi thay vi so
            }

            switch (choice) {
                case 1:
                    handleLogin(); // Goi tuc tien trinh dang nhap
                    break;
                case 2:
                    registerCustomerAccount(); // Goi tien trinh dang ky
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Cam on ban da su dung he thong. Tam biet!");
                    System.exit(0); // [OOP] Tuong tac voi JVM de tat phan mem
                    break;
                default:
                    System.out.println("\n[Loi] Lua chon khong hop le. Vui long thu lai!");
                    System.out.print("Nhan Enter de tiep tuc...");
                    sc.nextLine();
            }
        // Vong lap giup he thong luon song, cho phep dang nhap/dang ky lien tuc
        } while (choice != 0); 
    }

    // [OOP] Encapsulation: Tach logic dang nhap thanh ham private
    private void handleLogin() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 5; // Gioi han 5 lan nhap sai

        System.out.println("\n--- DANG NHAP HE THONG ---");
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Ten dang nhap : ");
            String username = sc.nextLine().trim();
            System.out.print("Mat khau      : ");
            String password = sc.nextLine().trim();

            System.out.println("[Thong bao] Dang kiem tra du lieu...");
            
            // Goi ham xu ly dang nhap tu AccountService
            String role = accountService.loginAndGetRole(username, password);

            if (role != null) {
                System.out.println("\n[Thong bao] DANG NHAP THANH CONG!");
                System.out.println("Xin chao, " + username + " | Chuc vu: " + role.toUpperCase());
                
                // Dieu huong Menu dua tren quyen (Role)
                routeToMenu(role);
                
                // [Quan trong]: Khi thoat Menu ben trong (Dang xuat), dung return de quay tro lai Menu ngoai cung (start)
                return; 
            } else {
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("[Canh bao] Ban con " + (MAX_ATTEMPTS - attempts) + " lan thu.\n");
                }
            }

            // Neu nhap sai qua gioi han
            if (attempts >= MAX_ATTEMPTS) {
                System.out.println("\n[Loi] HE THONG BAO MAT: Nhap sai qua " + MAX_ATTEMPTS + " lan!");
                System.out.println("Huy thao tac dang nhap de bao ve du lieu.");
                System.out.print("Nhan Enter de quay lai Menu...");
                sc.nextLine();
                return; // Tra ve Menu chinh thay vi tat luon chuong trinh
            }
        }
    }

    private void registerCustomerAccount() {
        AccountDAO accountDAO = new AccountDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        
        accountDAO.readFile("src/data/accounts.txt");
        customerDAO.readFile("src/data/customers.txt");

        System.out.println("\n" + "=".repeat(50));
        System.out.println("   DANG KY TAI KHOAN KHACH HANG MOI");
        System.out.println("=".repeat(50));

        // --- BUOC 1: THU THAP THONG TIN TAI KHOAN ---
        String username;
        while (true) {
            username = Validation.getNonEmptyString("1. Nhap ten dang nhap: ");
            if (accountDAO.findByUsername(username) != null) {
                System.out.println("[Loi] Ten dang nhap da ton tai! Vui long chon ten khac.");
            } else {
                break;
            }
        }
        String password = Validation.getNonEmptyString("2. Nhap mat khau: ");

        // --- BUOC 2: THU THAP THONG TIN KHACH HANG ---
        System.out.println("\n--- THONG TIN CA NHAN ---");
        String customerId = "KH" + String.format("%03d", customerDAO.getCount() + 1);
        
        String fullName = Validation.getNonEmptyString("3. Ho va Ten: ");
        String phone = Validation.getValidPhone("4. So dien thoai: ");
        String email = Validation.getValidEmail("5. Email: ");

        Account newAcc = new Account(customerId, username, password, "Khach hang", true, null);
        
        Customer newCus = new Customer();
        newCus.setCustomerId(customerId);
        newCus.setFullName(fullName);
        newCus.setPhoneNumber(phone);
        newCus.setEmail(email);
        newCus.setLoyaltyPoints(0);
        newCus.setCustomerType("Moi");
        newCus.setRegisteredDate(new Date());

        // Tu dong khoi tao dia chi rong de tranh NullPointerException khi luu file
        Address emptyAddress = new Address();
        emptyAddress.setHouseNumber("Chua cap nhat");
        emptyAddress.setStreet("Chua cap nhat");
        emptyAddress.setWard("Chua cap nhat");
        emptyAddress.setDistrict("Chua cap nhat");
        emptyAddress.setCity("Chua cap nhat");
        newCus.setAddress(emptyAddress);

        // --- BUOC 3: TAO VA LUU OTP RA FILE ---
        String generatedOTP = String.format("%06d", new Random().nextInt(999999));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/OTP.txt"))) {
            bw.write("Ma OTP dang ky cua khach hang '" + fullName + "' la: " + generatedOTP);
            bw.newLine();
            bw.write("Vui long cung cap ma nay cho khach hang de hoan tat dang ky.");
        } catch (IOException e) {
            System.out.println("[Loi] He thong khong the tao ma OTP ngay luc nay.");
            return;
        }

        // --- BUOC 4: XAC THUC OTP ---
        System.out.println("\n[He thong] Da gui yeu cau dang ky!");
        System.out.println(">>> VUI LONG GAP NHAN VIEN QUAY DE LAY MA OTP XAC NHAN <<<");
        
        int retryCount = 0;
        boolean isVerified = false;
        
        while (retryCount < 3) {
            System.out.print("\nNhap ma OTP (6 so): ");
            String inputOTP = sc.nextLine().trim();
            
            if (inputOTP.equals(generatedOTP)) {
                isVerified = true;
                break;
            } else {
                retryCount++;
                System.out.println("[Loi] Ma OTP khong chinh xac! Ban con " + (3 - retryCount) + " lan thu.");
            }
        }

        // --- BUOC 5: XU LY KET QUA ---
        if (isVerified) {
            // [OOP] Delegation: Uy quyen luu tru cho DAO
            accountDAO.add(newAcc);
            accountDAO.writeFile("src/data/accounts.txt");
            
            customerDAO.add(newCus);
            customerDAO.writeFile("src/data/customers.txt");
            
            System.out.println("\n[Thong bao] CHUC MUNG! Dang ky tai khoan thanh cong.");
            System.out.println("Ban co the dang nhap ngay bay gio.");
        } else {
            System.out.println("\n[Loi] Dang ky that bai do nhap sai OTP qua nhieu lan. Vui long thao tac lai tu dau!");
        }
        
        System.out.print("Nhan Enter de quay lai Menu...");
        sc.nextLine();
    }

    // Ham dieu huong Menu dua tren chuc vu
    private void routeToMenu(String role) {
        String userRole = role.toLowerCase();
        
        if (userRole.contains("khach hang")) {
            System.out.println("[Thong bao] Dang mo Menu danh cho Khach Hang...");
            GUI.CustomerMainMenu customerMenu = new GUI.CustomerMainMenu();
            customerMenu.showMenu();
        } else if (userRole.contains("nhan vien") || userRole.contains("quan ly")) {
            System.out.println("[Thong bao] Dang mo Menu Quan Tri He Thong...");
            GUI.MainMenu adminMenu = new GUI.MainMenu();
            adminMenu.showMenu();
        } else {
            System.out.println("[Loi] Quyen han khong xac dinh, vui long lien he truc tiep voi cua hang de duoc xu ly!");
        }
    }
}