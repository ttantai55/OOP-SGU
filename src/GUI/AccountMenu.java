package GUI;

import BUS.AccountService;
import java.util.Scanner;

public class AccountMenu {

    private final AccountService accountService;
    private final Scanner sc;

    public AccountMenu(AccountService accountService) {
        this.accountService = accountService;
        this.sc = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              QUAN LY TAI KHOAN HE THONG");
            System.out.println("=".repeat(60));
            System.out.println("  1. Hien thi danh sach tai khoan");
            System.out.println("  2. Them tai khoan moi (Quan ly / Nhan vien / Khach hang)");
            System.out.println("  3. Khoa / Mo khoa tai khoan");
            System.out.println("  4. Xoa tai khoan");
            System.out.println("  0. Quay lai Menu Chinh");
            System.out.println("=".repeat(60));
            System.out.print("Vui long chon chuc nang (0-4): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- DANH SACH TAI KHOAN ---");
                    System.out.printf("%-10s | %-15s | %-10s | %-12s | %-10s | %-10s\n",
                            "ID", "Username", "Password", "Role", "Status", "Owner ID");
                    System.out.println("-".repeat(75));
                    accountService.showAllAccounts();
                    pause();
                    break;
                case 2:
                    accountService.addNewAccount();
                    pause();
                    break;
                case 3:
                    accountService.toggleStatus();
                    pause();
                    break;
                case 4:
                    accountService.deleteAccount();
                    pause();
                    break;
                case 0:
                    System.out.println("\n[Thong bao] Dang quay lai Menu Chinh...");
                    break;
                default:
                    System.out.println("[Loi] Lua chon khong hop le!");
                    pause();
            }
        } while (choice != 0);
    }

    public void pause() {
        System.out.print("\nNhan Enter de tiep tuc...");
        sc.nextLine();
    }

}