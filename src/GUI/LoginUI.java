package GUI;

import BUS.AccountService;
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

public class LoginUI {
    
    private AccountService accountService;
    private Scanner sc;

    public LoginUI() {
        this.accountService = new AccountService();
        this.sc = new Scanner(System.in);
        // Ra lệnh nạp dữ liệu 1 lần duy nhất khi khởi động ứng dụng
        this.accountService.loadFromFile();
    }

    public void start() {
        int choice = -1;
        do {
            accountService.loadFromFile();
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
                choice = -1; 
            }

            switch (choice) {
                case 1:
                    handleLogin(); 
                    break;
                case 2:
                    registerCustomerAccount(); 
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Cam on ban da su dung he thong. Tam biet!");
                    System.exit(0); 
                    break;
                default:
                    System.out.println("\n[Loi] Lua chon khong hop le. Vui long thu lai!");
                    System.out.print("Nhan Enter de tiep tuc...");
                    sc.nextLine();
            }
        } while (choice != 0); 
    }

    private void handleLogin() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 5; 

        System.out.println("\n--- DANG NHAP HE THONG ---");
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Ten dang nhap : ");
            String username = sc.nextLine().trim();
            System.out.print("Mat khau      : ");
            String password = sc.nextLine().trim();

            System.out.println("[Thong bao] Dang kiem tra du lieu...");
            
            String role = accountService.loginAndGetRole(username, password);

            if (role != null) {
                System.out.println("\n[Thong bao] DANG NHAP THANH CONG!");
                System.out.println("Xin chao, " + username + " | Chuc vu: " + role.toUpperCase());
                
                routeToMenu(role);
                return; 
            } else {
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("[Canh bao] Ban con " + (MAX_ATTEMPTS - attempts) + " lan thu.\n");
                }
            }

            if (attempts >= MAX_ATTEMPTS) {
                System.out.println("\n[Loi] HE THONG BAO MAT: Nhap sai qua " + MAX_ATTEMPTS + " lan!");
                System.out.println("Huy thao tac dang nhap de bao ve du lieu.");
                System.out.print("Nhan Enter de quay lai Menu...");
                sc.nextLine();
                return; 
            }
        }
    }

    // [OOP] Reusability: Hàm hỗ trợ nhập liệu chuyên dụng cho quy trình có thể thoát ngang
    private String getInputWithEscape(String prompt, String regex, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            
            // 1. Kiểm tra lệnh thoát ngang (Early Exit)
            if (input.equals("Esc")) {
                return null; // Trả về null như một tín hiệu yêu cầu hủy bỏ
            }
            
            // 2. Kiểm tra chuỗi rỗng
            if (input.isEmpty()) {
                System.out.println("[Loi] Du lieu khong duoc de trong!");
                continue;
            }
            
            // 3. Kiểm tra biểu thức chính quy (nếu có yêu cầu kiểm tra định dạng)
            if (regex != null && !input.matches(regex)) {
                System.out.println("[Loi] " + errorMsg);
                continue;
            }
            
            return input;
        }
    }

    private void registerCustomerAccount() {
        AccountDAO accountDAO = new AccountDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        
        accountDAO.readFile("src/data/accounts.txt");
        customerDAO.readFile("src/data/Customer.txt");

        System.out.println("\n" + "=".repeat(50));
        System.out.println("   DANG KY TAI KHOAN KHACH HANG MOI");
        System.out.println("=".repeat(50));

        // --- BUOC 1: THU THAP THONG TIN TAI KHOAN ---
        String username;
        while (true) {
            username = getInputWithEscape("1. Nhap ten tai khoan (Nhap Esc de thoat ra menu chinh): ", null, null);
            if (username == null) {
                System.out.println("\n[Thong bao] Da huy qua trinh dang ky. Quay lai man hinh chinh...");
                return; // [OOP] Early Exit
            }

            if (accountDAO.findByUsername(username) != null) {
                System.out.println("[Loi] Ten dang nhap da ton tai! Vui long chon ten khac.");
            } else {
                break;
            }
        }
        
        String password = getInputWithEscape("2. Nhap mat khau (Nhap Esc de thoat ra menu chinh): ", null, null);
        if (password == null) {
            System.out.println("\n[Thong bao] Da huy qua trinh dang ky. Quay lai man hinh chinh...");
            return;
        }

        // --- BUOC 2: THU THAP THONG TIN KHACH HANG ---
        System.out.println("\n--- THONG TIN CA NHAN ---");
        String customerId = "KH" + String.format("%03d", customerDAO.getCount() + 1);
        
        String fullName = getInputWithEscape("3. Ho va Ten (Nhap Esc de thoat ra menu chinh): ", null, null);
        if (fullName == null) {
            System.out.println("\n[Thong bao] Da huy qua trinh dang ky. Quay lai man hinh chinh...");
            return;
        }

        String phone = getInputWithEscape("4. So dien thoai (Nhap Esc de thoat ra menu chinh): ", "^(0|\\+84)\\d{9}$", "So dien thoai khong hop le (Phai la so va co 10 chu so)!");
        if (phone == null) {
            System.out.println("\n[Thong bao] Da huy qua trinh dang ky. Quay lai man hinh chinh...");
            return;
        }

        String email = getInputWithEscape("5. Email (Nhap Esc de thoat ra menu chinh): ", "^[A-Za-z0-9+_.-]+@(.+)$", "Email khong hop le (VD: nguyenvan@gmail.com)!");
        if (email == null) {
            System.out.println("\n[Thong bao] Da huy qua trinh dang ky. Quay lai man hinh chinh...");
            return;
        }

        String accountId = "ACC_" + customerId;
        Account newAcc = new Account(accountId, username, password, "Khach hang", true, customerId);
        
        Customer newCus = new Customer();
        newCus.setCustomerId(customerId);
        newCus.setFullName(fullName);
        newCus.setPhoneNumber(phone);
        newCus.setEmail(email);
        newCus.setLoyaltyPoints(0);
        newCus.setCustomerType("Moi");
        newCus.setRegisteredDate(new Date());

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

        System.out.println("\n[He thong] Da gui yeu cau dang ky!");
        System.out.println(">>> VUI LONG GAP NHAN VIEN QUAY DE LAY MA OTP XAC NHAN <<<");
        
        int retryCount = 0;
        boolean isVerified = false;
        
        while (retryCount < 3) {
            System.out.print("\nNhap ma OTP (6 so) hoac nhap 'Esc' de thoat ra menu chinh: ");
            String inputOTP = sc.nextLine().trim();
            
            if (inputOTP.equals("Esc")) {
                System.out.println("\n[Thong bao] Da huy xac thuc. Quay lai man hinh chinh...");
                return; 
            }
            
            if (inputOTP.equals(generatedOTP)) {
                isVerified = true;
                break;
            } else {
                retryCount++;
                System.out.println("[Loi] Ma OTP khong chinh xac! Ban con " + (3 - retryCount) + " lan thu.");
            }
        }

        if (isVerified) {
            accountDAO.add(newAcc);
            customerDAO.add(newCus);
            
            accountDAO.writeFile("src/data/accounts.txt");
            customerDAO.writeFile("src/data/customers.txt");
            
            // Tải lại dữ liệu vào accountService chính để Khách hàng có thể đăng nhập ngay
            this.accountService.loadFromFile();

            System.out.println("\n[Thong bao] CHUC MUNG! Dang ky tai khoan thanh cong.");
            System.out.println("Ban co the dang nhap ngay bay gio.");
        } else {
            System.out.println("\n[Loi] Dang ky that bai do nhap sai OTP qua nhieu lan. Vui long thao tac lai tu dau!");
        }
        
        System.out.print("Nhan Enter de quay lai Menu...");
        sc.nextLine();
    }

    private void routeToMenu(String role) {
        String userRole = role.toLowerCase();
        
        if (userRole.contains("khach hang")) {
            System.out.println("[Thong bao] Dang mo Menu danh cho Khach Hang...");
            GUI.CustomerMainMenu customerMenu = new GUI.CustomerMainMenu(); 
            customerMenu.showMenu();
        } else if (userRole.contains("nhan vien")) {
            System.out.println("[Thong bao] Dang mo Menu danh cho Nhan vien...");
            GUI.SalesEmployeeMainMenu employeeMenu = new GUI.SalesEmployeeMainMenu(); 
            employeeMenu.showMenu();
        } else if (userRole.contains("quan ly")) {
            System.out.println("[Thong bao] Dang mo Menu danh cho Quan ly...");
            GUI.ManagerMainMenu managerMenu = new GUI.ManagerMainMenu(); 
            managerMenu.showMenu();
        } 
        else {
            System.out.println("[Loi] Quyen han khong xac dinh, vui long lien he truc tiep voi cua hang de duoc xu ly!");
        }
    }
}