import BUS.AccountService;
import java.util.Scanner;

public class TEST {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountService accountService = new AccountService();

        System.out.println("=== HỆ THỐNG ĐĂNG NHẬP ===");
        System.out.print("Tên đăng nhập: ");
        String user = sc.nextLine();
        System.out.print("Mật khẩu: ");
        String pass = sc.nextLine();

        // Gọi hàm kiểm tra đăng nhập từ tầng BUS
        String role = accountService.loginAndGetRole(user, pass);

        if (role != null) {
            // Điều hướng Menu dựa trên Role
            // Sử dụng toLowerCase() để so sánh không phân biệt hoa thường
            if (role.toLowerCase().contains("khach hang")) {
                showCustomerMenu();
            } else if (role.toLowerCase().contains("nhan vien") || role.toLowerCase().contains("quan ly")) {
                showEmployeeMenu();
            } else {
                System.out.println("Tài khoản của bạn không thể truy cập menu!");
            }
        } else {
            System.out.println("Đăng nhập thất bại. Vui lòng thử lại!");
        }
        sc.close();
    }

    // --- MENU DÀNH CHO KHÁCH HÀNG ---
    public static void showCustomerMenu() {
        System.out.println("\n--- MENU KHÁCH HÀNG ---");
        System.out.println("1. Xem danh sách sản phẩm");
        System.out.println("2. Đặt hàng");
        System.out.println("3. Xem lịch sử mua hàng");
        System.out.println("0. Đăng xuất");
        // Gọi Scanner để chọn menu ở đây...
    }

    // --- MENU DÀNH CHO NHÂN VIÊN / QUẢN LÝ ---
    public static void showEmployeeMenu() {
        System.out.println("\n--- MENU QUẢN TRỊ ---");
        System.out.println("1. Quản lý Tài khoản");
        System.out.println("2. Quản lý Nhà cung cấp");
        System.out.println("0. Đăng xuất");
        // Gọi Scanner để chọn menu ở đây...
    }
}
