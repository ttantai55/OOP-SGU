package GUI;

import BUS.AccountService;
import BUS.Validation;
import DTO.Account;
import java.util.Scanner;

public class AccountMenu {
    
    private AccountService accountService;
    private Scanner sc;

    public AccountMenu(AccountService accountService) {
        this.accountService = accountService;
        this.sc = new Scanner(System.in);
    }

    public void showMenu() {
        int choice = -1;
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
                    addNewAccount();
                    pause();
                    break;
                case 3:
                    toggleStatus();
                    pause();
                    break;
                case 4:
                    deleteAccount();
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

    private void addNewAccount() {
        System.out.println("\n--- THEM TAI KHOAN MOI ---");
        String accountId = Validation.getNonEmptyString("Nhap Ma Tai Khoan (VD: ACC01): ");
        String username = Validation.getNonEmptyString("Nhap Ten dang nhap: ");
        String password = Validation.getNonEmptyString("Nhap Mat khau: ");

        System.out.println("\nChon Doi tuong so huu tai khoan (Role):");
        System.out.println("1. Quan ly");
        System.out.println("2. Nhan vien");
        System.out.println("3. Khach hang");
        
        String roleChoice;
        String role = "";
        String ownerPrompt = "";
        
        while (true) {
            roleChoice = Validation.getNonEmptyString("Chon (1, 2 hoac 3): ");
            if (roleChoice.equals("1")) {
                role = "Quan ly";
                ownerPrompt = "Nhap Ma Quan ly (VD: QL001): ";
                break;
            } else if (roleChoice.equals("2")) {
                role = "Nhan vien";
                ownerPrompt = "Nhap Ma Nhan vien (VD: NV002): ";
                break;
            } else if (roleChoice.equals("3")) {
                role = "Khach hang";
                ownerPrompt = "Nhap Ma Khach hang (VD: KH001): ";
                break;
            } else {
                System.out.println("[Loi] Lua chon khong hop le!");
            }
        }

        String ownerId = Validation.getNonEmptyString(ownerPrompt);

        Account newAcc = new Account(accountId, username, password, role, true, ownerId);

        if (accountService.addAccount(newAcc)) {
            System.out.println("[Thong bao] Da them tai khoan thanh cong (Hien dang luu tren RAM).");
        }
    }

    private void toggleStatus() {
        System.out.println("\n--- KHOA / MO KHOA TAI KHOAN ---");
        String accountId = Validation.getNonEmptyString("Nhap Ma Tai Khoan can thay doi (VD: ACC01): ");
        System.out.println("Chon trang thai moi:");
        System.out.println("1. Hoat dong (Mo khoa)");
        System.out.println("2. Bi khoa");
        String statusChoice = Validation.getNonEmptyString("Chon (1 hoac 2): ");
        boolean isActive = statusChoice.equals("1");

        accountService.toggleAccountStatus(accountId, isActive);
    }

    private void deleteAccount() {
        System.out.println("\n--- XOA TAI KHOAN ---");
        String accountId = Validation.getNonEmptyString("Nhap Ma Tai Khoan can xoa: ");
        System.out.print("Ban co chac chan muon xoa? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            accountService.deleteAccount(accountId);
        } else {
            System.out.println("[Thong bao] Da huy thao tac xoa.");
        }
    }

    private void pause() {
        System.out.print("\nNhan Enter de tiep tuc...");
        sc.nextLine();
    }
}