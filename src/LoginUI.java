import BUS.AccountService;
import java.util.Scanner;

// [OOP] Class: Giao dien xu ly Dang nhap va dieu huong he thong
public class LoginUI {
    
    // [OOP] Association: Lien ket voi AccountService de xu ly logic nghiep vu
    private AccountService accountService;
    private Scanner sc;

    public LoginUI() {
        this.accountService = new AccountService();
        this.sc = new Scanner(System.in);
    }

    public void start() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 5; // Gioi han 5 lan nhap sai

        while (attempts < MAX_ATTEMPTS) {
            System.out.println("\n=========================================");
            System.out.println("      HE THONG QUAN LY CUA HANG LAPTOP     ");
            System.out.println("=========================================");
            System.out.print("Ten dang nhap : ");
            String username = sc.nextLine().trim();
            System.out.print("Mat khau      : ");
            String password = sc.nextLine().trim();

            System.out.println("[Thong bao] Dang kiem tra du lieu...");
            
            // Goi ham xu ly dang nhap tu AccountService
            String role = accountService.loginAndGetRole(username, password);

            if (role != null) {
                // Dang nhap thanh cong
                System.out.println("\n[Thong bao] DANG NHAP THANH CONG!");
                System.out.println("Xin chao, " + username + " | Chuc vu: " + role.toUpperCase());
                
                // Dieu huong Menu dua tren quyen (Role)
                routeToMenu(role);
                return; // Thoat chuong trinh dang nhap de vao Menu chinh
            } else {
                // Dang nhap that bai (AccountService da in thong bao loi cu the)
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("[Canh bao] Ban con " + (MAX_ATTEMPTS - attempts) + " lan thu.");
                }
            }

            // Neu nhap sai qua gioi han
            if (attempts >= MAX_ATTEMPTS) {
                System.out.println("\n[Loi] HE THONG BAO MAT: Nhap sai qua " + MAX_ATTEMPTS + " lan!");
                System.out.println("Chuong trinh se tu dong dong de bao ve du lieu.");
                System.exit(0);
            }
        }
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
            adminMenu.showMenu(); // Goi menu cua Y len!
        } else {
            System.out.println("[Loi] Quyen han khong xac dinh, vui long lien he truc tiep voi cua hang de duoc xu ly!");
        }
    }
}