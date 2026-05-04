package BUS;

import DAO.AccountDAO;
import DTO.Account;
import java.util.Scanner;

// [OOP] Class: Lop nghiep vu (Business Logic Layer) xu ly logic tai khoan
public class AccountService {

    // [OOP] Association (Ket hop) & Delegation (Uy quyen):
    // Service khong tu ghi file ma giao viec do cho tang DAO
    private AccountDAO accountDAO;
    static Scanner sc = new Scanner(System.in);

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    // --- [BỔ SUNG] CÁC HÀM ĐỒNG BỘ DỮ LIỆU ---

    // [OOP] Delegation: Uy quyen cho DAO doc du lieu
    public void loadFromFile() {
        accountDAO.readFile("src/data/accounts.txt");
    }

    // [OOP] Delegation: Uy quyen cho DAO ghi du lieu xuong o cung
    public void saveToFile() {
        accountDAO.writeFile("src/data/accounts.txt");
    }

    // --- CÁC HÀM NGHIỆP VỤ ---

    /**
     * Ham xu ly dang nhap.
     * @return Role (Quyen) cua nguoi dung neu thanh cong. Tra ve null neu that bai.
     */
    public String loginAndGetRole(String username, String password) {
        Account acc = accountDAO.findByUsername(username);

        if (acc == null) {
            System.out.println("[Loi] Tai khoan khong ton tai!");
            return null;
        }

        if (!acc.getPassword().equals(password)) {
            System.out.println("[Loi] Sai mat khau!");
            return null;
        }

        if (!acc.isActive()) {
            System.out.println("[Loi] Tai khoan cua ban dang bi khoa. Vui long lien he Admin!");
            return null;
        }

        System.out.println("[Thong bao] Dang nhap thanh cong! Xin chao, " + username);
        return acc.getRole(); // Tra ve quyen: vi du "Khach hang", "Nhan vien", "Quan ly"
    }

    // Them tai khoan moi (Kiem tra trung username truoc khi them)
    public boolean checkAccount(Account acc) {
        if (accountDAO.findByUsername(acc.getUsername()) != null) {
            System.out.println("[Loi] Ten dang nhap da ton tai!");
            return false;
        }
        accountDAO.add(acc);
        return true;
    }

    // Xoa tai khoan
    public void deleteAccount(String accountId) {
        accountDAO.remove(accountId);
    }

    // Khoa/Mo khoa tai khoan
    public void toggleAccountStatus(String accountId, boolean status) {
        Account acc = accountDAO.findById(accountId);
        if (acc != null) {
            acc.setActive(status);
            accountDAO.update(acc);
            System.out.println("[Thong bao] Da cap nhat trang thai tai khoan trong bo nho!");
        } else {
            System.out.println("[Loi] Khong tim thay tai khoan de cap nhat trang thai!");
        }
    }

    // Lay danh sach hien thi
    public void showAllAccounts() {
        accountDAO.displayAll();
    }

    public void addNewAccount() {
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

        if (checkAccount(newAcc)) {
            System.out.println("[Thong bao] Da them tai khoan thanh cong (Hien dang luu tren RAM).");
        }
        saveToFile();
    }

    public void toggleStatus() {
        System.out.println("\n--- KHOA / MO KHOA TAI KHOAN ---");
        String accountId = Validation.getNonEmptyString("Nhap Ma Tai Khoan can thay doi (VD: ACC01): ");
        System.out.println("Chon trang thai moi:");
        System.out.println("1. Hoat dong (Mo khoa)");
        System.out.println("2. Bi khoa");
        String statusChoice = Validation.getNonEmptyString("Chon (1 hoac 2): ");
        boolean isActive = statusChoice.equals("1");

        toggleAccountStatus(accountId, isActive);
        saveToFile();
    }

    public void deleteAccount() {
        System.out.println("\n--- XOA TAI KHOAN ---");
        String accountId = Validation.getNonEmptyString("Nhap Ma Tai Khoan can xoa: ");
        System.out.print("Ban co chac chan muon xoa? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            deleteAccount(accountId);
        } else {
            System.out.println("[Thong bao] Da huy thao tac xoa.");
        }
        saveToFile();
    }
}