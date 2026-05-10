package GUI;

public class Main {
    public static void main(String[] args) {
        // Khoi dong he thong tu man hinh dang nhap
        LoginUI loginUI = new LoginUI();
        loginUI.start();
        while (true) {
            System.out.println("\n[He thong] Dang khoi dong giao dien Dang Nhap...");
            // Gọi màn hình đăng nhập
            loginUI.start();
            // Khi app.start() kết thúc (Nghĩa là Khách hoặc Admin vừa bấm phím 0 để Đăng xuất)
            // Vòng lặp while(true) sẽ tự động quay lại dòng 8 và gọi lại màn hình Đăng nhập mới!
        }
    }
}
