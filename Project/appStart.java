// [OOP] Class: Lớp khởi chạy toàn bộ hệ thống (Main Driver)
import GUI.LoginUI;

public class appStart {
    public static void main(String[] args) {
        // Khởi tạo giao diện Đăng nhập
        LoginUI app = new LoginUI();
        
        // Vòng lặp vô hạn giúp hệ thống luôn "sống" sau khi Đăng xuất
        while (true) {
            System.out.println("\n[He thong] Dang khoi dong giao dien Dang Nhap...");
            // Gọi màn hình đăng nhập
            app.start(); 
            // Khi app.start() kết thúc (Nghĩa là Khách hoặc Admin vừa bấm phím 0 để Đăng xuất)
            // Vòng lặp while(true) sẽ tự động quay lại dòng 8 và gọi lại màn hình Đăng nhập mới!
        }
    }
}