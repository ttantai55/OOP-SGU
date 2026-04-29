import BUS.AccountService;
import BUS.CustomerService;

import java.util.Scanner;

public class LoginUI {
    private AccountService accountService;
    private Scanner sc;

    public LoginUI() {
        this.accountService = new AccountService();
        this.sc = new Scanner(System.in);
    }

    public void start() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 5; // Giới hạn 5 lần nhập sai

        while (attempts < MAX_ATTEMPTS) {
            System.out.println("\n=========================================");
            System.out.println("      HỆ THỐNG QUẢN LÝ CỬA HÀNG LAPTOP     ");
            System.out.println("=========================================");
            System.out.print("👤 Tên đăng nhập : ");
            String username = sc.nextLine().trim();
            System.out.print("🔑 Mật khẩu      : ");
            String password = sc.nextLine().trim();

            System.out.println("⏳ Đang kiểm tra dữ liệu...");
            
            // Gọi hàm xử lý đăng nhập từ AccountService
            String role = accountService.loginAndGetRole(username, password);

            if (role != null) {
                // Đăng nhập thành công
                System.out.println("\n🎉 ĐĂNG NHẬP THÀNH CÔNG!");
                System.out.println("👋 Xin chào, " + username + " | Chức vụ: " + role.toUpperCase());
                
                // Điều hướng Menu dựa trên quyền (Role)
                routeToMenu(role);
                return; // Thoát chương trình đăng nhập để vào Menu chính
            } else {
                // Đăng nhập thất bại (AccountService đã in thông báo lỗi cụ thể)
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("⚠️ Bạn còn " + (MAX_ATTEMPTS - attempts) + " lần thử.");
                }
            }

            // Nếu nhập sai quá giới hạn
            if (attempts >= MAX_ATTEMPTS) {
                System.out.println("\n🚨 HỆ THỐNG BẢO MẬT: Nhập sai quá " + MAX_ATTEMPTS + " lần!");
                System.out.println("Chương trình sẽ tự động đóng để bảo vệ dữ liệu.");
                System.exit(0);
            }
        }
    }

    // Hàm điều hướng Menu dựa trên chức vụ
    private void routeToMenu(String role) {
        String userRole = role.toLowerCase();
        
        if (userRole.contains("khach hang")) {
            System.out.println("Đang mở Menu dành cho Khách Hàng...");
            CustomerService customerService = new CustomerService();
            customerService.loadFromFile(); 
            customerService.showMenu();    
        } else if (userRole.contains("nhan vien") || userRole.contains("quan ly")) {
            System.out.println("Đang mở Menu Quản Trị Hệ Thống...");
            GUI.MainMenu adminMenu = new GUI.MainMenu();
            adminMenu.showMenu(); // Gọi menu của Ý lên!
        } else {
            System.out.println("Quyền hạn không xác định, vui lòng liên hệ trực tiếp với cửa hàng để được xử lý!");
        }
    }
}