package BUS;

import DAO.AccountDAO;
import DTO.Account;
import java.util.Scanner;

// [OOP] Class: Lop nghiep vu (Business Logic Layer) xu ly logic tai khoan
public class AccountService {
    
    // [OOP] Association (Ket hop) & Delegation (Uy quyen): 
    // Service khong tu ghi file ma giao viec do cho tang DAO
    private final AccountDAO accountDAO;
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
    public boolean addAccount(Account acc) {
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
    //Thao tac doi mat khau

    public void changePassword(String username) {
      
        Account acc = accountDAO.findByUsername(username);

        if(acc == null) {
            System.out.println("Khong tim thay tai khoan tren he thong!");
        }

        System.out.println("Moi nhap mat khau hien tai:");
        String oldPass = sc.nextLine();

        if (!acc.getPassword().equals(oldPass)) {
            System.out.println("Mat khau hien tai khong chinh xac");
            return;// co the bat khach hang nhap lai
        }

        System.out.print("Moi nhap mat khau moi: ");
        String newPass = sc.nextLine();
        
        System.out.print("Xac nhan lai mat khau moi: ");
        String confirmPass = sc.nextLine();
        
        //Kiem tra mk moi va xac nhan mk moi
        if (!newPass.equals(confirmPass)) {
            System.out.println("-> Loi: Mat khau xac nhan khong khop!");
            return;
        }

        acc.setPassword(newPass);
        
        //savefile
        
        System.out.println("Doi mat khau thanh cong!");

    }
}